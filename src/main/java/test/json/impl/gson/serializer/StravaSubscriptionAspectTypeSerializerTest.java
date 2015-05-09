/**
 *
 */
package test.json.impl.gson.serializer;

import javastrava.api.v3.model.webhook.reference.StravaSubscriptionAspectType;

/**
 * @author danshannon
 *
 */
public class StravaSubscriptionAspectTypeSerializerTest extends EnumSerializerTest<StravaSubscriptionAspectType> {

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaSubscriptionAspectType getUnknownValue() {
		return StravaSubscriptionAspectType.UNKNOWN;
	}

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaSubscriptionAspectType> getClassUnderTest() {
		return StravaSubscriptionAspectType.class;
	}

}
