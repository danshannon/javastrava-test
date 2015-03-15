package test.apicheck.api;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javastrava.api.v3.model.StravaMap;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.util.exception.JsonSerialisationException;
import javastrava.util.impl.gson.JsonUtilImpl;

import org.apache.commons.io.IOUtils;

import retrofit.client.Response;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author dshannon
 *
 */
public class ResponseValidator {
	private static JsonUtilImpl util = new JsonUtilImpl();
	/**
	 * @param errors
	 * @return
	 */
	private static boolean containsErrors(final List<String> errors) {
		boolean fail = false;
		if (!errors.isEmpty()) {
			for (final String error : errors) {
				System.out.println(error);
			}
			fail = true;
		}
		return fail;
	}

	/**
	 * @param response response to be validated
	 * @param class1 class of the element (or of the array contents if the input is a JsonArray)
	 * @throws JsonSerialisationException
	 * @throws IOException
	 */
	public static <T> void validate(final Response response, final Class<T> class1) throws JsonSerialisationException, IOException {
		final String input = IOUtils.toString(response.getBody().in());
		final JsonParser parser = new JsonParser();
		final JsonElement inputElement = parser.parse(input);
		final List<String> errors = new ArrayList<String>();
		if (inputElement == null || inputElement.isJsonNull() || inputElement.isJsonPrimitive()) {
			return;
		}


		if (inputElement.isJsonObject()) {
			final T object = util.deserialise(input, class1);
			final String outputString = util.serialise(object);
			final JsonElement output = parser.parse(outputString);
			for (final Entry<String, JsonElement> entry : inputElement.getAsJsonObject().entrySet()) {
				final String name = entry.getKey();
				final JsonElement element = entry.getValue();
				if (!(element.isJsonNull()) && output.getAsJsonObject().get(name)  == null) {
					errors.add("No element named '" + name + "'");
				}
			}
		}

		if (inputElement.isJsonArray()) {
			for (final JsonElement element : inputElement.getAsJsonArray()) {
				final T object = util.getGson().fromJson(element, class1);
				final JsonElement output = util.getGson().toJsonTree(object);
				for (final Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
					final String name = entry.getKey();
					final JsonElement subelement = entry.getValue();
					if (!(subelement.isJsonNull()) && output.getAsJsonObject().get(name)  == null) {
						errors.add("No element named '" + name + "'");
					}
				}
			}
		}

		// If it's a segment, validate the bits
		if (class1 == StravaSegment.class) {
			final StravaSegment segment = util.deserialise(input, StravaSegment.class);
			validateSegment(segment, inputElement, errors, "segment.");
		}

		// If it's a segment effort, validate the bits
		if (class1 == StravaSegmentEffort.class) {
			final StravaSegmentEffort effort = util.deserialise(input, StravaSegmentEffort.class);
			validateSegmentEffort(effort, inputElement, errors, "segmentEffort.");
		}

		assertFalse(containsErrors(errors));
	}

	private static void validateSegment(final StravaSegment segment, final JsonElement element, final List<String> errors, final String prefix) {
		compareJsonElements(util.getGson().toJsonTree(segment),element,errors,prefix);
		if (segment.getAthletePrEffort() != null) {
			final JsonElement prElement = element.getAsJsonObject().get("athlete_pr_effort");
			validateSegmentEffort(segment.getAthletePrEffort(), prElement, errors, prefix + "athlete_pr_effort");
		}
		if (segment.getMap() != null) {
			final JsonElement mapElement = element.getAsJsonObject().get("map");
			validateMap(segment.getMap(), mapElement, errors, prefix + ".map");
		}

	}

	private static void validateSegmentEffort(final StravaSegmentEffort effort, final JsonElement element, final List<String> errors, final String prefix) {
		compareJsonElements(util.getGson().toJsonTree(effort), element, errors, prefix);
		if (effort.getAchievements() != null) {
			final JsonElement achievements = element.getAsJsonObject().get("achievements");
			validateAchievements(effort.getAchievements(), achievements, errors, prefix + "achievements");
		}
		if (effort.getActivity() != null) {
			// TODO
		}
		if (effort.getAthleteSegmentStats() != null) {
			// TODO
		}
		if (effort.getSegment() != null ) {
			// TODO
		}
	}

	private static void validateMap(final StravaMap map, final JsonElement mapElement, final List<String> errors, final String prefix) {
		compareJsonElements(util.getGson().toJsonTree(map), mapElement, errors, prefix);

	}

	private static void compareJsonElements(final JsonElement output, final JsonElement jsonElement, final List<String> errors, final String prefix) {
		for (final Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
			final String name = entry.getKey();
			final JsonElement element = entry.getValue();
			if (!(element.isJsonNull()) && output.getAsJsonObject().get(name)  == null) {
				errors.add("No element named '" + prefix + "." + name + "'");
			}
		}
	}

}
