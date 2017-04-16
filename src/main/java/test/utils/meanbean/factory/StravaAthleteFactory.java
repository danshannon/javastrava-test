package test.utils.meanbean.factory;

import org.meanbean.lang.Factory;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.service.standardtests.data.AthleteDataUtils;

/**
 * Custom factory for mean bean testing
 *
 * @author Dan Shannon
 *
 */
public class StravaAthleteFactory implements Factory<StravaAthlete> {

	@Override
	public StravaAthlete create() {
		return AthleteDataUtils.testAthlete(StravaResourceState.DETAILED);
	}

}
