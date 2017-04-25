/**
 *
 */
package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaSkillLevel;

/**
 * @author danshannon
 *
 */
public class StravaSkillLevelSerializerTest extends EnumSerializerTest<StravaSkillLevel> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaSkillLevel> getClassUnderTest() {
		return StravaSkillLevel.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaSkillLevel getUnknownValue() {
		return StravaSkillLevel.UNKNOWN;
	}

}
