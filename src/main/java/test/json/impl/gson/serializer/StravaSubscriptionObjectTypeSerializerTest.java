/**
 *
 */
package test.json.impl.gson.serializer;

import javastrava.api.v3.model.webhook.reference.StravaSubscriptionObjectType;

/**
 * @author danshannon
 *
 */
public class StravaSubscriptionObjectTypeSerializerTest extends EnumSerializerTest<StravaSubscriptionObjectType> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaSubscriptionObjectType> getClassUnderTest() {
		return StravaSubscriptionObjectType.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaSubscriptionObjectType getUnknownValue() {
		return StravaSubscriptionObjectType.UNKNOWN;
	}

}
