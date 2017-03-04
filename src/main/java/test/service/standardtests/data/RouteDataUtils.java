package test.service.standardtests.data;

import test.utils.TestUtils;

/**
 * <p>
 * Test data utilities for routes
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class RouteDataUtils {

	/**
	 * Identifier of a valid route that belongs to the authenticated user
	 */
	public final static Integer	ROUTE_VALID_ID		= TestUtils.integerProperty("test.routeId");		//$NON-NLS-1$ ;
	/**
	 * Invalid route identifier
	 */
	public final static Integer	ROUTE_INVALID_ID	= TestUtils.integerProperty("test.routeInvalidId");	//$NON-NLS-1$ ;

}
