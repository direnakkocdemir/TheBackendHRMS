package com.CCT.HRMS.core.Results;


/**
 * This class is defined for successfully results which include data If result
 * is successful result returns true, data with/without message
 * 
 * @author Diren
 *
 * @param <T> parameter which is defined for methods
 */

public class SuccessDataResult<T> extends DataResult<T> {
	
	// Constructors
    public SuccessDataResult(T data, String message) {
        super(data, true, message);
    }
    public SuccessDataResult(T data) {
        super(data, true);
    }
    public SuccessDataResult(String message){
        super(true,message);
    }
    public SuccessDataResult(){
        super(true);
    }
}
