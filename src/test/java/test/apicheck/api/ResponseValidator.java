package test.apicheck.api;

import static org.junit.Assert.assertNull;

import java.io.IOException;
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
import javastrava.api.v3.model.StravaGear;
import javastrava.api.v3.model.StravaLap;
import javastrava.api.v3.model.StravaMap;
import javastrava.api.v3.model.StravaMapPoint;
import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.StravaSegmentExplorerResponse;
import javastrava.api.v3.model.StravaSegmentExplorerResponseSegment;
import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
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

	private static void compareJsonObjects(final JsonElement output, final JsonElement jsonElement,
			final Set<String> errors, final String prefix) {
		for (final Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
			final String name = entry.getKey();
			final JsonElement element = entry.getValue();
			if (!(element.isJsonNull()) && (output.getAsJsonObject().get(name) == null)) {
				errors.add("No element named '" + prefix + "." + name + "'");
			}
		}
	}

	/**
	 * @param errors
	 * @return
	 */
	private static String errors(final Set<String> errors) {
		String errorString = "";
		if (!errors.isEmpty()) {
			for (final String error : errors) {
				errorString = errorString + error + "; ";
			}
		}
		if (errorString.length() == 0) {
			return null;
		}
		System.out.println(errorString);
		return errorString;
	}

	private static <T> JsonElement roundTrip(final JsonElement input, final Class<T> class1) {
		return util.getGson().toJsonTree(util.getGson().fromJson(input, class1));
	}

	/**
	 * @param response
	 *            response to be validated
	 * @param class1
	 *            class of the element (or of the array contents if the input is
	 *            a JsonArray)
	 * @throws JsonSerialisationException
	 * @throws IOException
	 */
	public static <T> void validate(final Response response, final Class<T> class1, final String prefix)
			throws JsonSerialisationException, IOException {
		final String input = IOUtils.toString(response.getBody().in());
		final JsonParser parser = new JsonParser();
		final JsonElement inputElement = parser.parse(input);
		final Set<String> errors = new LinkedHashSet<String>();
		if ((inputElement == null) || inputElement.isJsonNull() || inputElement.isJsonPrimitive()) {
			return;
		}

		validateElement(inputElement, errors, class1, prefix);
		final String message = errors(errors);
		assertNull(message, message);

	}

	/**
	 * @param activity
	 * @param activity2
	 * @param errors
	 * @param string
	 */
	private static void validateActivity(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaActivity.class), element, errors, prefix);
		final JsonElement athlete = element.getAsJsonObject().get("athlete");
		if (athlete != null) {
			validateElement(athlete, errors, StravaAthlete.class, prefix + ".athlete");
		}
		final JsonElement bestEfforts = element.getAsJsonObject().get("best_efforts");
		if (bestEfforts != null) {
			validateElement(bestEfforts, errors, StravaBestRunningEffort.class, prefix + ".best_efforts");
		}
		final JsonElement endLatLng = element.getAsJsonObject().get("end_latlng");
		if (endLatLng != null) {
			validateElement(endLatLng, errors, StravaMapPoint.class, prefix + ".end_latlng");
		}
		final JsonElement gear = element.getAsJsonObject().get("gear");
		if (gear != null) {
			validateElement(gear, errors, StravaGear.class, prefix + ".gear");
		}
		final JsonElement map = element.getAsJsonObject().get("map");
		if (map != null) {
			validateElement(map, errors, StravaMap.class, prefix + ".map");
		}
		final JsonElement photos = element.getAsJsonObject().get("photos");
		if (photos != null) {
			validateElement(photos, errors, StravaActivityPhotos.class, prefix + ".photos");
		}
		final JsonElement segmentEfforts = element.getAsJsonObject().get("segment_efforts");
		if (segmentEfforts != null) {
			validateElement(segmentEfforts, errors, StravaSegmentEffort.class, prefix + ".segment_efforts");
		}
		final JsonElement splitsMetric = element.getAsJsonObject().get("splits_metric");
		if (splitsMetric != null) {
			validateElement(splitsMetric, errors, StravaSplit.class, prefix + ".splits_metric");
		}
		final JsonElement splitsStandard = element.getAsJsonObject().get("splits_standard");
		if (splitsStandard != null) {
			validateElement(splitsStandard, errors, StravaSplit.class, prefix + ".splits_metric");
		}
		final JsonElement startLatLng = element.getAsJsonObject().get("start_latlng");
		if (startLatLng != null) {
			validateElement(startLatLng, errors, StravaMapPoint.class, prefix + ".start_latlng");
		}

	}

	/**
	 * @param element
	 * @param errors
	 * @param prefix
	 */
	private static void validateActivityPhotos(final JsonElement element, final Set<String> errors,
			final String prefix) {
		compareJsonObjects(roundTrip(element, StravaActivityPhotos.class), element, errors, prefix);
		final JsonElement primary = element.getAsJsonObject().get("primary");
		if (primary != null) {
			validateElement(primary, errors, StravaPhoto.class, prefix + ".primary");
		}
	}

	/**
	 * @param inputElement
	 * @param errors
	 * @param prefix
	 */
	private static void validateActivityZone(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaActivityZone.class), element, errors, prefix);
		final JsonElement buckets = element.getAsJsonObject().get("distribution_buckets");
		if (buckets != null) {
			validateElement(buckets, errors, StravaActivityZoneDistributionBucket.class,
					prefix + ".distributoin_buckets");
		}

	}

	/**
	 * @param athlete
	 * @param athlete2
	 * @param errors
	 * @param string
	 */
	private static void validateAthlete(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaAthlete.class), element, errors, prefix);
		final JsonElement bikes = element.getAsJsonObject().get("bikes");
		if (bikes != null) {
			validateElement(bikes, errors, StravaGear.class, prefix + ".bikes");
		}
		final JsonElement clubs = element.getAsJsonObject().get("clubs");
		if (clubs != null) {
			validateElement(clubs, errors, StravaClub.class, prefix + ".clubs");
		}
		final JsonElement shoes = element.getAsJsonObject().get("shoes");
		if (shoes != null) {
			validateElement(shoes, errors, StravaGear.class, prefix + ".shoes");
		}

	}

	/**
	 * @param inputElement
	 * @param errors
	 * @param prefix
	 */
	private static void validateBestEffort(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaBestRunningEffort.class), element, errors, prefix);
		final JsonElement activity = element.getAsJsonObject().get("activity");
		if (activity != null) {
			validateElement(activity, errors, StravaActivity.class, prefix + ".activity");
		}
		final JsonElement athlete = element.getAsJsonObject().get("athlete");
		if (athlete != null) {
			validateElement(athlete, errors, StravaAthlete.class, prefix + ".athlete");
		}
		final JsonElement segment = element.getAsJsonObject().get("segment");
		if (segment != null) {
			validateElement(segment, errors, StravaSegment.class, prefix + ".segment");
		}

	}

	/**
	 * @param inputElement
	 * @param errors
	 * @param prefix
	 */
	private static void validateClubAnnouncements(final JsonElement element, final Set<String> errors,
			final String prefix) {
		compareJsonObjects(roundTrip(element, StravaClubAnnouncement.class), element, errors, prefix);
		final JsonElement athlete = element.getAsJsonObject().get("athlete");
		if (athlete != null) {
			validateElement(athlete, errors, StravaAthlete.class, prefix + ".athlete");
		}
	}

	private static <T> void validateElement(final JsonElement inputElement, final Set<String> errors,
			final Class<T> class1, final String prefix) {
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

	/**
	 * @param element
	 * @param errors
	 * @param prefix
	 */
	private static void validateLap(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaLap.class), element, errors, prefix);
		final JsonElement athlete = element.getAsJsonObject().get("athlete");
		if (athlete != null) {
			validateElement(athlete, errors, StravaAthlete.class, prefix + ".athlete");
		}
		final JsonElement activity = element.getAsJsonObject().get("acivity");
		if (activity != null) {
			validateElement(activity, errors, StravaActivity.class, prefix + ".activity");
		}

	}

	private static void validateSegment(final JsonElement element, final Set<String> errors, final String prefix) {
		compareJsonObjects(roundTrip(element, StravaSegment.class), element, errors, prefix);
		final JsonElement prElement = element.getAsJsonObject().get("athlete_pr_effort");
		if (prElement != null) {
			validateElement(prElement, errors, StravaSegmentEffort.class, prefix + ".athlete_pr_effort");
		}
		final JsonElement mapElement = element.getAsJsonObject().get("map");
		if (mapElement != null) {
			validateElement(mapElement, errors, StravaMap.class, prefix + ".map");
		}

	}

	private static void validateSegmentEffort(final JsonElement element, final Set<String> errors,
			final String prefix) {
		compareJsonObjects(roundTrip(element, StravaSegmentEffort.class), element, errors, prefix);
		final JsonElement achievements = element.getAsJsonObject().get("achievements");
		if (achievements != null) {
			validateElement(achievements, errors, StravaAchievement.class, prefix + ".achievements");
		}
		final JsonElement activity = element.getAsJsonObject().get("activity");
		if (activity != null) {
			validateElement(activity, errors, StravaActivity.class, prefix + ".activity");
		}
		final JsonElement stats = element.getAsJsonObject().get("athlete_segment_stats");
		if (stats != null) {
			validateElement(stats, errors, StravaAthleteSegmentStats.class, prefix + ".athlete_segment_stats");
		}
		final JsonElement segment = element.getAsJsonObject().get("segment");
		if (segment != null) {
			validateElement(segment, errors, StravaSegment.class, prefix + ".segment");
		}
	}

	/**
	 * @param inputElement
	 * @param errors
	 * @param prefix
	 */
	private static void validateSegmentExplorerResponse(final JsonElement element, final Set<String> errors,
			final String prefix) {
		compareJsonObjects(roundTrip(element, StravaSegmentExplorerResponse.class), element, errors, prefix);
		final JsonElement segments = element.getAsJsonObject().get("segments");
		if (segments != null) {
			validateElement(segments, errors, StravaSegmentExplorerResponseSegment.class, prefix + ".segments");
		}

	}

	/**
	 * @param inputElement
	 * @param errors
	 * @param prefix
	 */
	private static void validateSegmentExplorerResponseSegment(final JsonElement element, final Set<String> errors,
			final String prefix) {
		compareJsonObjects(roundTrip(element, StravaSegmentExplorerResponseSegment.class), element, errors, prefix);
		final JsonElement endLatLng = element.getAsJsonObject().get("end_latlng");
		if (endLatLng != null) {
			validateElement(endLatLng, errors, StravaMapPoint.class, prefix + ".end_latlng");
		}

		final JsonElement startLatLng = element.getAsJsonObject().get("start_latlng");
		if (startLatLng != null) {
			validateElement(startLatLng, errors, StravaMapPoint.class, prefix + ".start_latlng");
		}
	}

	/**
	 * @param inputElement
	 * @param errors
	 * @param prefix
	 */
	private static void validateSegmentLeaderboard(final JsonElement element, final Set<String> errors,
			final String prefix) {
		compareJsonObjects(roundTrip(element, StravaSegmentLeaderboard.class), element, errors, prefix);
		final JsonElement entries = element.getAsJsonObject().get("entries");
		if (entries != null) {
			validateElement(entries, errors, StravaSegmentLeaderboardEntry.class, prefix + ".entries");
		}

	}

	/**
	 * @param inputElement
	 * @param errors
	 * @param prefix
	 */
	private static void validateStravaStatistics(final JsonElement element, final Set<String> errors,
			final String prefix) {
		compareJsonObjects(roundTrip(element, StravaStatistics.class), element, errors, prefix);
		final String[] totalsList = { "all_ride_totals", "all_run_totals", "recent_ride_totals", "recent_run_totals",
				"ytd_ride_totals", "ytd_run_totals" };
		for (final String totals : totalsList) {
			final JsonElement stats = element.getAsJsonObject().get("allRideTotals");
			if (stats != null) {
				validateElement(stats, errors, StravaStatisticsEntry.class, prefix + "." + totals);
			}
		}
	}

}
