package test.apicheck;

import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javastrava.api.v3.model.StravaAchievement;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityPhotos;
import javastrava.api.v3.model.StravaActivityZone;
import javastrava.api.v3.model.StravaActivityZoneDistributionBucket;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaAthleteSegmentStats;
import javastrava.api.v3.model.StravaBestRunningEffort;
import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.StravaClubAnnouncement;
import javastrava.api.v3.model.StravaClubEvent;
import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.model.StravaLap;
import javastrava.api.v3.model.StravaMap;
import javastrava.api.v3.model.StravaMapPoint;
import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.model.StravaRoute;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaSegmentExplorerResponse;
import javastrava.api.v3.model.StravaSegmentExplorerResponseSegment;
import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.model.StravaSimilarActivities;
import javastrava.api.v3.model.StravaSplit;
import javastrava.api.v3.model.StravaStatistics;
import javastrava.api.v3.model.StravaStatisticsEntry;
import javastrava.json.exception.JsonSerialisationException;
import javastrava.json.impl.gson.JsonUtilImpl;
import retrofit.client.Response;

/**
 * @author Dan Shannon
 *
 */
public class ResponseValidator {
	private static JsonUtilImpl util = new JsonUtilImpl();

	private static void compareJsonObjects(final JsonElement output, final JsonElement inputElement, final Set<String> errors, final String prefix) {
		for (final Entry<String, JsonElement> entry : inputElement.getAsJsonObject().entrySet()) {
			final String name = entry.getKey();
			final JsonElement element = entry.getValue();
			if (!(element.isJsonNull()) && (output.getAsJsonObject().get(name) == null)) {
				errors.add("No element named '" + prefix + "." + name + "' returned by round-tripping "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		}
	}

	/**
	 * @param errors
	 *            Set containing error messages
	 * @return Concatenated string representation of the strings in the set
	 */
	private static String errors(final Set<String> errors) {
		String errorString = ""; //$NON-NLS-1$
		if (!errors.isEmpty()) {
			for (final String error : errors) {
				errorString = errorString + System.getProperty("line.separator") + error + "; "; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		if (errorString.length() == 0) {
			return null;
		}
		return errorString;
	}

	private static <T> JsonElement roundTrip(final JsonElement input, final Class<T> class1) {
		return util.getGson().toJsonTree(util.getGson().fromJson(input, class1));
	}

	/**
	 * @param response
	 *            response to be validated
	 * @param class1
	 *            class of the element (or of the array contents if the input is a JsonArray)
	 * @param prefix
	 *            String describing the parent element
	 * @throws JsonSerialisationException
	 *             if there's an error serialising or deserialising
	 * @throws IOException
	 *             if there's an IO error
	 */
	public static <T> void validate(final Response response, final Class<T> class1, final String prefix) throws JsonSerialisationException, IOException {
		final String input = IOUtils.toString(response.getBody().in(), Charset.defaultCharset());
		final JsonParser parser = new JsonParser();
		final JsonElement inputElement = parser.parse(input);
		final Set<String> errors = new LinkedHashSet<String>();
		if ((inputElement == null) || inputElement.isJsonNull() || inputElement.isJsonPrimitive()) {
			return;
		}

		validateElement(inputElement, errors, class1, prefix);
		final String message = errors(errors);
		assertNull(message);

	}

	/**
	 * Validate the activity as returned
	 *
	 * @param element
	 *            JSON element containing the activity
	 * @param errors
	 *            Set containing detected errors
	 * @param prefix
	 *            String describing the parent element
	 */
	private static void validateActivity(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaActivity.class), element, errors, prefix);
		final JsonElement athlete = element.getAsJsonObject().get("athlete"); //$NON-NLS-1$
		if (athlete != null) {
			validateElement(athlete, errors, StravaAthlete.class, prefix + ".athlete"); //$NON-NLS-1$
		}
		final JsonElement bestEfforts = element.getAsJsonObject().get("best_efforts"); //$NON-NLS-1$
		if (bestEfforts != null) {
			validateElement(bestEfforts, errors, StravaBestRunningEffort.class, prefix + ".best_efforts"); //$NON-NLS-1$
		}
		final JsonElement endLatLng = element.getAsJsonObject().get("end_latlng"); //$NON-NLS-1$
		if (endLatLng != null) {
			validateElement(endLatLng, errors, StravaMapPoint.class, prefix + ".end_latlng"); //$NON-NLS-1$
		}
		final JsonElement gear = element.getAsJsonObject().get("gear"); //$NON-NLS-1$
		if (gear != null) {
			validateElement(gear, errors, StravaGear.class, prefix + ".gear"); //$NON-NLS-1$
		}
		final JsonElement map = element.getAsJsonObject().get("map"); //$NON-NLS-1$
		if (map != null) {
			validateElement(map, errors, StravaMap.class, prefix + ".map"); //$NON-NLS-1$
		}
		final JsonElement photos = element.getAsJsonObject().get("photos"); //$NON-NLS-1$
		if (photos != null) {
			validateElement(photos, errors, StravaActivityPhotos.class, prefix + ".photos"); //$NON-NLS-1$
		}
		final JsonElement segmentEfforts = element.getAsJsonObject().get("segment_efforts"); //$NON-NLS-1$
		if (segmentEfforts != null) {
			validateElement(segmentEfforts, errors, StravaSegmentEffort.class, prefix + ".segment_efforts"); //$NON-NLS-1$
		}
		final JsonElement splitsMetric = element.getAsJsonObject().get("splits_metric"); //$NON-NLS-1$
		if (splitsMetric != null) {
			validateElement(splitsMetric, errors, StravaSplit.class, prefix + ".splits_metric"); //$NON-NLS-1$
		}
		final JsonElement splitsStandard = element.getAsJsonObject().get("splits_standard"); //$NON-NLS-1$
		if (splitsStandard != null) {
			validateElement(splitsStandard, errors, StravaSplit.class, prefix + ".splits_metric"); //$NON-NLS-1$
		}
		final JsonElement startLatLng = element.getAsJsonObject().get("start_latlng"); //$NON-NLS-1$
		if (startLatLng != null) {
			validateElement(startLatLng, errors, StravaMapPoint.class, prefix + ".start_latlng"); //$NON-NLS-1$
		}
		final JsonElement laps = element.getAsJsonObject().get("laps"); //$NON-NLS-1$
		if (laps != null) {
			validateElement(laps, errors, StravaLap.class, prefix + ".laps"); //$NON-NLS-1$
		}
		final JsonElement similarActivities = element.getAsJsonObject().get("similar_activities"); //$NON-NLS-1$
		if (similarActivities != null) {
			validateElement(similarActivities, errors, StravaSimilarActivities.class, prefix + ".similar_activities"); //$NON-NLS-1$
		}

	}

	/**
	 * @param element
	 *            Element to be validated
	 * @param errors
	 *            Set of validation errors, will be added to if there are further validation errors
	 * @param prefix
	 *            String describing the parent element
	 */
	private static void validateActivityPhotos(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaActivityPhotos.class), element, errors, prefix);
		final JsonElement primary = element.getAsJsonObject().get("primary"); //$NON-NLS-1$
		if (primary != null) {
			validateElement(primary, errors, StravaPhoto.class, prefix + ".primary"); //$NON-NLS-1$
		}
	}

	/**
	 * @param element
	 *            Element to be validated
	 * @param errors
	 *            Set of validation errors, will be added to if there are further validation errors
	 * @param prefix
	 *            String describing the parent element
	 */
	private static void validateActivityZone(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaActivityZone.class), element, errors, prefix);
		final JsonElement buckets = element.getAsJsonObject().get("distribution_buckets"); //$NON-NLS-1$
		if (buckets != null) {
			validateElement(buckets, errors, StravaActivityZoneDistributionBucket.class, prefix + ".distribution_buckets"); //$NON-NLS-1$
		}

	}

	/**
	 * @param element
	 *            Element to be validated
	 * @param errors
	 *            Set of validation errors, will be added to if there are further validation errors
	 * @param prefix
	 *            String describing the parent element
	 */
	private static void validateAthlete(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaAthlete.class), element, errors, prefix);
		final JsonElement bikes = element.getAsJsonObject().get("bikes"); //$NON-NLS-1$
		if (bikes != null) {
			validateElement(bikes, errors, StravaGear.class, prefix + ".bikes"); //$NON-NLS-1$
		}
		final JsonElement clubs = element.getAsJsonObject().get("clubs"); //$NON-NLS-1$
		if (clubs != null) {
			validateElement(clubs, errors, StravaClub.class, prefix + ".clubs"); //$NON-NLS-1$
		}
		final JsonElement shoes = element.getAsJsonObject().get("shoes"); //$NON-NLS-1$
		if (shoes != null) {
			validateElement(shoes, errors, StravaGear.class, prefix + ".shoes"); //$NON-NLS-1$
		}

	}

	/**
	 * @param element
	 *            Element to be validated
	 * @param errors
	 *            Set of validation errors, will be added to if there are further validation errors
	 * @param prefix
	 *            String describing the parent element
	 */
	private static void validateBestEffort(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaBestRunningEffort.class), element, errors, prefix);
		final JsonElement activity = element.getAsJsonObject().get("activity"); //$NON-NLS-1$
		if (activity != null) {
			validateElement(activity, errors, StravaActivity.class, prefix + ".activity"); //$NON-NLS-1$
		}
		final JsonElement athlete = element.getAsJsonObject().get("athlete"); //$NON-NLS-1$
		if (athlete != null) {
			validateElement(athlete, errors, StravaAthlete.class, prefix + ".athlete"); //$NON-NLS-1$
		}
		final JsonElement segment = element.getAsJsonObject().get("segment"); //$NON-NLS-1$
		if (segment != null) {
			validateElement(segment, errors, StravaSegment.class, prefix + ".segment"); //$NON-NLS-1$
		}

	}

	/**
	 * @param element
	 *            Element to be validated
	 * @param errors
	 *            Set of validation errors, will be added to if there are further validation errors
	 * @param prefix
	 *            String describing the parent element
	 */
	private static void validateClubAnnouncements(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaClubAnnouncement.class), element, errors, prefix);
		final JsonElement athlete = element.getAsJsonObject().get("athlete"); //$NON-NLS-1$
		if (athlete != null) {
			validateElement(athlete, errors, StravaAthlete.class, prefix + ".athlete"); //$NON-NLS-1$
		}
	}

	private static <T> void validateElement(final JsonElement inputElement, final Set<String> errors, final Class<T> class1, final String prefix) {
		// Null safety
		if (inputElement == null) {
			return;
		}

		if (inputElement.isJsonObject()) {
			final JsonElement outputElement = roundTrip(inputElement, class1);

			// If it's an activity, validate the bits
			if (class1 == StravaActivity.class) {
				validateActivity(inputElement, errors, prefix);
			}

			else if (class1 == StravaActivityPhotos.class) {
				validateActivityPhotos(inputElement, errors, prefix);
			}

			else if (class1 == StravaActivityZone.class) {
				validateActivityZone(inputElement, errors, prefix);
			}

			else if (class1 == StravaAthlete.class) {
				validateAthlete(inputElement, errors, prefix);
			}

			else if (class1 == StravaBestRunningEffort.class) {
				validateBestEffort(inputElement, errors, prefix);
			}

			else if (class1 == StravaLap.class) {
				validateLap(inputElement, errors, prefix);
			}

			// If it's a segment, validate the bits
			else if (class1 == StravaSegment.class) {
				validateSegment(inputElement, errors, prefix);
			}

			// If it's a segment effort, validate the bits
			else if (class1 == StravaSegmentEffort.class) {
				validateSegmentEffort(inputElement, errors, prefix);

			}

			else if (class1 == StravaSegmentExplorerResponse.class) {
				validateSegmentExplorerResponse(inputElement, errors, prefix);
			}

			else if (class1 == StravaSegmentExplorerResponseSegment.class) {
				validateSegmentExplorerResponseSegment(inputElement, errors, prefix);
			}

			else if (class1 == StravaSegmentLeaderboard.class) {
				validateSegmentLeaderboard(inputElement, errors, prefix);
			}

			else if (class1 == StravaStatistics.class) {
				validateStravaStatistics(inputElement, errors, prefix);
			}

			else if (class1 == StravaClubAnnouncement.class) {
				validateClubAnnouncements(inputElement, errors, prefix);
			}

			else if (class1 == StravaClubEvent.class) {
				validateClubGroupEvents(inputElement, errors, prefix);
			}

			else if (class1 == StravaRoute.class) {
				validateRoutes(inputElement, errors, prefix);
			}
			// For everything else which doesn't have composed Strava model
			// objects
			else {
				compareJsonObjects(outputElement, inputElement, errors, prefix);
			}
		}

		if (inputElement.isJsonArray()) {
			for (final JsonElement element : inputElement.getAsJsonArray()) {
				validateElement(element, errors, class1, prefix);
			}
		}

	}

	private static void validateRoutes(JsonElement element, Set<String> errors, String prefix) {
		compareJsonObjects(roundTrip(element, StravaRoute.class), element, errors, prefix);

		// Validate the athlete if there is one
		final JsonElement athlete = element.getAsJsonObject().get("athlete"); //$NON-NLS-1$
		if (athlete != null) {
			validateElement(athlete, errors, StravaAthlete.class, prefix + ".athlete"); //$NON-NLS-1$
		}

		// Validate the map if there is one
		final JsonElement map = element.getAsJsonObject().get("map"); //$NON-NLS-1$
		if (map != null) {
			validateElement(map, errors, StravaMap.class, prefix + ".map"); //$NON-NLS-1$
		}

		// Validate the segments if they exist
		final JsonElement segments = element.getAsJsonObject().get("segments"); //$NON-NLS-1$
		validateElement(segments, errors, StravaSegment.class, prefix + ".segments"); //$NON-NLS-1$

	}

	private static void validateClubGroupEvents(JsonElement element, Set<String> errors, String prefix) {
		compareJsonObjects(roundTrip(element, StravaClubEvent.class), element, errors, prefix);

		// Validate the club if there is one
		final JsonElement club = element.getAsJsonObject().get("club"); //$NON-NLS-1$
		if (club != null) {
			validateElement(club, errors, StravaClub.class, prefix + ".club"); //$NON-NLS-1$
		}

		// Validate the route if there is one
		final JsonElement route = element.getAsJsonObject().get("route"); //$NON-NLS-1$
		if (route != null) {
			validateElement(route, errors, StravaRoute.class, prefix + ".route"); //$NON-NLS-1$
		}

	}

	/**
	 * @param element
	 *            Element to be validated
	 * @param errors
	 *            Set of validation errors, will be added to if there are further validation errors
	 * @param prefix
	 *            String describing the parent element
	 */
	private static void validateLap(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaLap.class), element, errors, prefix);
		final JsonElement athlete = element.getAsJsonObject().get("athlete"); //$NON-NLS-1$
		if (athlete != null) {
			validateElement(athlete, errors, StravaAthlete.class, prefix + ".athlete"); //$NON-NLS-1$
		}
		final JsonElement activity = element.getAsJsonObject().get("acivity"); //$NON-NLS-1$
		if (activity != null) {
			validateElement(activity, errors, StravaActivity.class, prefix + ".activity"); //$NON-NLS-1$
		}

	}

	private static void validateSegment(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaSegment.class), element, errors, prefix);
		final JsonElement prElement = element.getAsJsonObject().get("athlete_pr_effort"); //$NON-NLS-1$
		if (prElement != null) {
			validateElement(prElement, errors, StravaSegmentEffort.class, prefix + ".athlete_pr_effort"); //$NON-NLS-1$
		}
		final JsonElement mapElement = element.getAsJsonObject().get("map"); //$NON-NLS-1$
		if (mapElement != null) {
			validateElement(mapElement, errors, StravaMap.class, prefix + ".map"); //$NON-NLS-1$
		}

	}

	private static void validateSegmentEffort(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaSegmentEffort.class), element, errors, prefix);
		final JsonElement achievements = element.getAsJsonObject().get("achievements"); //$NON-NLS-1$
		if (achievements != null) {
			validateElement(achievements, errors, StravaAchievement.class, prefix + ".achievements"); //$NON-NLS-1$
		}
		final JsonElement activity = element.getAsJsonObject().get("activity"); //$NON-NLS-1$
		if (activity != null) {
			validateElement(activity, errors, StravaActivity.class, prefix + ".activity"); //$NON-NLS-1$
		}
		final JsonElement stats = element.getAsJsonObject().get("athlete_segment_stats"); //$NON-NLS-1$
		if (stats != null) {
			validateElement(stats, errors, StravaAthleteSegmentStats.class, prefix + ".athlete_segment_stats"); //$NON-NLS-1$
		}
		final JsonElement segment = element.getAsJsonObject().get("segment"); //$NON-NLS-1$
		if (segment != null) {
			validateElement(segment, errors, StravaSegment.class, prefix + ".segment"); //$NON-NLS-1$
		}
	}

	/**
	 * @param element
	 *            Element to be validated
	 * @param errors
	 *            Set of validation errors, will be added to if there are further validation errors
	 * @param prefix
	 *            String describing the parent element
	 */
	private static void validateSegmentExplorerResponse(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaSegmentExplorerResponse.class), element, errors, prefix);
		final JsonElement segments = element.getAsJsonObject().get("segments"); //$NON-NLS-1$
		if (segments != null) {
			validateElement(segments, errors, StravaSegmentExplorerResponseSegment.class, prefix + ".segments"); //$NON-NLS-1$
		}

	}

	/**
	 * @param element
	 *            Element to be validated
	 * @param errors
	 *            Set of validation errors, will be added to if there are further validation errors
	 * @param prefix
	 *            String describing the parent element
	 */
	private static void validateSegmentExplorerResponseSegment(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaSegmentExplorerResponseSegment.class), element, errors, prefix);
		final JsonElement endLatLng = element.getAsJsonObject().get("end_latlng"); //$NON-NLS-1$
		if (endLatLng != null) {
			validateElement(endLatLng, errors, StravaMapPoint.class, prefix + ".end_latlng"); //$NON-NLS-1$
		}

		final JsonElement startLatLng = element.getAsJsonObject().get("start_latlng"); //$NON-NLS-1$
		if (startLatLng != null) {
			validateElement(startLatLng, errors, StravaMapPoint.class, prefix + ".start_latlng"); //$NON-NLS-1$
		}
	}

	/**
	 * @param element
	 *            Element to be validated
	 * @param errors
	 *            Set of validation errors, will be added to if there are further validation errors
	 * @param prefix
	 *            String describing the parent element
	 */
	private static void validateSegmentLeaderboard(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaSegmentLeaderboard.class), element, errors, prefix);
		final JsonElement entries = element.getAsJsonObject().get("entries"); //$NON-NLS-1$
		if (entries != null) {
			validateElement(entries, errors, StravaSegmentLeaderboardEntry.class, prefix + ".entries"); //$NON-NLS-1$
		}

	}

	/**
	 * @param element
	 *            Element to be validated
	 * @param errors
	 *            Set of validation errors, will be added to if there are further validation errors
	 * @param prefix
	 *            String describing the parent element
	 */
	private static void validateStravaStatistics(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaStatistics.class), element, errors, prefix);
		final String[] totalsList = { "all_ride_totals", "all_run_totals", "recent_ride_totals", "recent_run_totals", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"ytd_ride_totals", "ytd_run_totals" }; //$NON-NLS-1$ //$NON-NLS-2$
		for (final String totals : totalsList) {
			final JsonElement stats = element.getAsJsonObject().get("allRideTotals"); //$NON-NLS-1$
			if (stats != null) {
				validateElement(stats, errors, StravaStatisticsEntry.class, prefix + "." + totals); //$NON-NLS-1$
			}
		}
	}

}
