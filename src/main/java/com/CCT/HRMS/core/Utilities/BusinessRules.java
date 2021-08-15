package com.CCT.HRMS.core.Utilities;

import com.CCT.HRMS.core.Results.*;

/**
 * All business rules validation
 * 
 * @author diren
 *
 */
public class BusinessRules {

	/**
	 * Checking business validation rules and responding with result
	 * 
	 * @param results which is defined for business layer
	 * @return Success or Error result which are including boolean with/without
	 *         message
	 */
	public static Result run(Result... results) {

		for (Result result : results) {
			// If result is false we should return proper error with it's message
			if (!result.isSuccess()) {
				return result;
			}
		}
		// If there is no violation for rules return success which is true
		return new SuccessResult();
	}
}
