package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaStreamSeriesDownsamplingType;

/**
 * @author Dan Shannon
 *
 */
public class StreamSeriesDownsamplingTypeSerializerTest extends EnumSerializerTest<StravaStreamSeriesDownsamplingType> {

	@Override
	public Class<StravaStreamSeriesDownsamplingType> getClassUnderTest() {
		return StravaStreamSeriesDownsamplingType.class;
	}

	@Override
	protected StravaStreamSeriesDownsamplingType getUnknownValue() {
		return StravaStreamSeriesDownsamplingType.UNKNOWN;
	}
}
