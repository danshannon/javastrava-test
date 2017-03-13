package test.service.standardtests.data;

import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaMap;
import javastrava.api.v3.model.StravaMapPoint;
import javastrava.api.v3.model.reference.StravaResourceState;

public class MapDataUtils {
	private static Random random = new Random();

	private static TextProducer text = Fairy.create().textProducer();

	@SuppressWarnings("boxing")
	public static StravaMapPoint testMapPoint(StravaResourceState resourceState) {
		final StravaMapPoint point = new StravaMapPoint();

		point.setLatitude((random.nextFloat() * 180) - 90);
		point.setLongitude((random.nextFloat() * 360) - 180);

		return point;
	}

	public static StravaMap testMap(StravaResourceState resourceState) {
		final StravaMap map = new StravaMap();

		map.setId(text.randomString(20));
		map.setPolyline(text.randomString(100));
		map.setResourceState(resourceState);
		map.setSummaryPolyline(text.randomString(100));

		return map;
	}

}
