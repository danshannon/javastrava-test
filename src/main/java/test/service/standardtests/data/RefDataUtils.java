package test.service.standardtests.data;

import java.util.Random;

import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.model.reference.StravaAthleteType;
import javastrava.api.v3.model.reference.StravaClimbCategory;
import javastrava.api.v3.model.reference.StravaClubMembershipStatus;
import javastrava.api.v3.model.reference.StravaClubType;
import javastrava.api.v3.model.reference.StravaEventFrequency;
import javastrava.api.v3.model.reference.StravaFollowerState;
import javastrava.api.v3.model.reference.StravaFrameType;
import javastrava.api.v3.model.reference.StravaGearType;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaMeasurementMethod;
import javastrava.api.v3.model.reference.StravaPhotoSource;
import javastrava.api.v3.model.reference.StravaPhotoType;
import javastrava.api.v3.model.reference.StravaRouteSubType;
import javastrava.api.v3.model.reference.StravaRouteType;
import javastrava.api.v3.model.reference.StravaSegmentActivityType;
import javastrava.api.v3.model.reference.StravaSkillLevel;
import javastrava.api.v3.model.reference.StravaSportType;
import javastrava.api.v3.model.reference.StravaTerrainType;
import javastrava.api.v3.model.reference.StravaWeekOfMonth;
import javastrava.api.v3.model.reference.StravaWorkoutType;

/**
 * Test data utilities for generating randomised reference data instances
 *
 * @author Dan Shannon
 *
 */
public class RefDataUtils {
	private static Random random = new Random();

	/**
	 * @return A random activity type
	 */
	public static StravaActivityType randomActivityType() {
		StravaActivityType type = StravaActivityType.UNKNOWN;
		while (type == StravaActivityType.UNKNOWN) {
			final int pick = random.nextInt(StravaActivityType.values().length);
			type = StravaActivityType.values()[pick];
		}
		return type;
	}

	/**
	 * @return A random athlete type
	 */
	public static StravaAthleteType randomAthleteType() {
		StravaAthleteType athleteType = StravaAthleteType.UNKNOWN;
		while (athleteType == StravaAthleteType.UNKNOWN) {
			final int pick = random.nextInt(StravaAthleteType.values().length);
			athleteType = StravaAthleteType.values()[pick];
		}
		return athleteType;
	}

	/**
	 * @return A random climb category
	 */
	public static StravaClimbCategory randomClimbCategory() {
		StravaClimbCategory category = StravaClimbCategory.UNKNOWN;
		while (category == StravaClimbCategory.UNKNOWN) {
			final int pick = random.nextInt(StravaClimbCategory.values().length);
			category = StravaClimbCategory.values()[pick];
		}
		return category;
	}

	/**
	 * @return A random club membership status
	 */
	public static StravaClubMembershipStatus randomClubMembershipStatus() {
		StravaClubMembershipStatus status = StravaClubMembershipStatus.UNKNOWN;
		while (status == StravaClubMembershipStatus.UNKNOWN) {
			final int pick = random.nextInt(StravaClubMembershipStatus.values().length);
			status = StravaClubMembershipStatus.values()[pick];
		}
		return status;
	}

	/**
	 * @return A random club type
	 */
	public static StravaClubType randomClubType() {
		StravaClubType clubType = StravaClubType.UNKNOWN;
		while (clubType == StravaClubType.UNKNOWN) {
			final int pick = random.nextInt(StravaClubType.values().length);
			clubType = StravaClubType.values()[pick];
		}
		return clubType;
	}

	/**
	 * @return A random event frequency
	 */
	public static StravaEventFrequency randomEventFrequency() {
		StravaEventFrequency frequency = StravaEventFrequency.UNKNOWN;
		while (frequency == StravaEventFrequency.UNKNOWN) {
			final int pick = random.nextInt(StravaEventFrequency.values().length);
			frequency = StravaEventFrequency.values()[pick];
		}
		return frequency;
	}

	/**
	 * @return A random follower state
	 */
	public static StravaFollowerState randomFollowerState() {
		StravaFollowerState state = StravaFollowerState.UNKNOWN;
		while (state == StravaFollowerState.UNKNOWN) {
			final int pick = random.nextInt(StravaFollowerState.values().length);
			state = StravaFollowerState.values()[pick];
		}
		return state;
	}

	/**
	 * @return A random frame type
	 */
	public static StravaFrameType randomFrameType() {
		StravaFrameType frameType = StravaFrameType.UNKNOWN;
		while (frameType == StravaFrameType.UNKNOWN) {
			final int pick = random.nextInt(StravaFrameType.values().length);
			frameType = StravaFrameType.values()[pick];
		}
		return frameType;
	}

	/**
	 * @return A random gear type
	 */
	public static StravaGearType randomGearType() {
		StravaGearType gearType = StravaGearType.UNKNOWN;
		while (gearType == StravaGearType.UNKNOWN) {
			final int pick = random.nextInt(StravaGearType.values().length);
			gearType = StravaGearType.values()[pick];
		}
		return gearType;
	}

	/**
	 * @return A random gender
	 */
	public static StravaGender randomGender() {
		StravaGender gender = StravaGender.UNKNOWN;
		while (gender == StravaGender.UNKNOWN) {
			final int pick = random.nextInt(StravaGender.values().length);
			gender = StravaGender.values()[pick];
		}
		return gender;
	}

	/**
	 * @return A random measurement method
	 */
	public static StravaMeasurementMethod randomMeasurementMethod() {
		StravaMeasurementMethod method = StravaMeasurementMethod.UNKNOWN;
		while (method == StravaMeasurementMethod.UNKNOWN) {
			final int pick = random.nextInt(StravaMeasurementMethod.values().length);
			method = StravaMeasurementMethod.values()[pick];
		}
		return method;
	}

	/**
	 * @return A random photo source
	 */
	public static StravaPhotoSource randomPhotoSource() {
		StravaPhotoSource source = StravaPhotoSource.UNKNOWN;
		while (source == StravaPhotoSource.UNKNOWN) {
			final int pick = random.nextInt(StravaPhotoSource.values().length);
			source = StravaPhotoSource.values()[pick];
		}
		return source;
	}

	/**
	 * @return A random photo type
	 */
	public static StravaPhotoType randomPhotoType() {
		StravaPhotoType type = StravaPhotoType.UNKNOWN;
		while (type == StravaPhotoType.UNKNOWN) {
			final int pick = random.nextInt(StravaPhotoType.values().length);
			type = StravaPhotoType.values()[pick];
		}
		return type;
	}

	/**
	 * @return A random route sub-type
	 */
	public static StravaRouteSubType randomRouteSubType() {
		StravaRouteSubType routeSubType = StravaRouteSubType.UNKNOWN;
		while (routeSubType == StravaRouteSubType.UNKNOWN) {
			final int pick = random.nextInt(StravaRouteSubType.values().length);
			routeSubType = StravaRouteSubType.values()[pick];
		}
		return routeSubType;
	}

	/**
	 * @return A random route type
	 */
	public static StravaRouteType randomRouteType() {
		StravaRouteType routeType = StravaRouteType.UNKNOWN;
		while (routeType == StravaRouteType.UNKNOWN) {
			final int pick = random.nextInt(StravaRouteType.values().length);
			routeType = StravaRouteType.values()[pick];
		}
		return routeType;
	}

	/**
	 * @return A random segment activity type
	 */
	public static StravaSegmentActivityType randomSegmentActivityType() {
		StravaSegmentActivityType type = StravaSegmentActivityType.UNKNOWN;
		while (type == StravaSegmentActivityType.UNKNOWN) {
			final int pick = random.nextInt(StravaSegmentActivityType.values().length);
			type = StravaSegmentActivityType.values()[pick];
		}
		return type;
	}

	/**
	 * @return A random skill level
	 */
	public static StravaSkillLevel randomSkillLevel() {
		StravaSkillLevel skillLevel = StravaSkillLevel.UNKNOWN;
		while (skillLevel == StravaSkillLevel.UNKNOWN) {
			final int pick = random.nextInt(StravaSkillLevel.values().length);
			skillLevel = StravaSkillLevel.values()[pick];
		}
		return skillLevel;
	}

	/**
	 * @return A random sport type
	 */
	public static StravaSportType randomSportType() {
		StravaSportType sportType = StravaSportType.UNKNOWN;
		while (sportType == StravaSportType.UNKNOWN) {
			final int pick = random.nextInt(StravaSportType.values().length);
			sportType = StravaSportType.values()[pick];
		}
		return sportType;
	}

	/**
	 * @return A random terrain type
	 */
	public static StravaTerrainType randomTerrainType() {
		StravaTerrainType terrainType = StravaTerrainType.UNKNOWN;
		while (terrainType == StravaTerrainType.UNKNOWN) {
			final int pick = random.nextInt(StravaTerrainType.values().length);
			terrainType = StravaTerrainType.values()[pick];
		}
		return terrainType;
	}

	/**
	 * @return A random week of the month
	 */
	public static StravaWeekOfMonth randomWeekOfMonth() {
		StravaWeekOfMonth week = StravaWeekOfMonth.UNKNOWN;
		while (week == StravaWeekOfMonth.UNKNOWN) {
			final int pick = random.nextInt(StravaWeekOfMonth.values().length);
			week = StravaWeekOfMonth.values()[pick];
		}
		return week;
	}

	/**
	 * @return A random workout type
	 */
	public static StravaWorkoutType randomWorkoutType() {
		StravaWorkoutType type = StravaWorkoutType.UNKNOWN;
		while (type == StravaWorkoutType.UNKNOWN) {
			final int pick = random.nextInt(StravaWorkoutType.values().length);
			type = StravaWorkoutType.values()[pick];
		}
		return type;
	}

}
