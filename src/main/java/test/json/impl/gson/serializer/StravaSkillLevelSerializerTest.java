/**
 *
 */
package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaSkillLevel;

/**
 * @author danshannon
 *
 */
public class StravaSkillLevelSerializerTest extends EnumSerializerTest<StravaSkillLevel> {

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaSkillLevel getUnknownValue() {
		return StravaSkillLevel.UNKNOWN;
	}

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaSkillLevel> getClassUnderTest() {
		return StravaSkillLevel.class;
	}

}
