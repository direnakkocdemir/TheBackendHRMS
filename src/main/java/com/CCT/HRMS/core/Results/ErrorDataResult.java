package com.CCT.HRMS.core.Results;

/**
 * This class is defined for unsuccessfully results which include data If result
 * is not successful result returns false, data with/without message
 * 
 * @author Diren
 *
 * @param <T> parameter which is defined for methods
 */

public class ErrorDataResult<T> extends DataResult<T> {
	
	// Constructors
    public ErrorDataResult(T data, String message) {
        super(data, false, message);
    }

    public ErrorDataResult(T data) {
        super(data, false);
    }

    public ErrorDataResult(String message) {
        super(false, message);
    }

    public ErrorDataResult(){
        super(false);
    }
}
