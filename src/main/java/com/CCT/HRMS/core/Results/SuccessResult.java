package com.CCT.HRMS.core.Results;

/**
 * This class is defined for successfully results If result is successful result
 * returns true with/without message
 * 
 * @author Diren
 *
 */

public class SuccessResult extends Result {
    
	// Constructors
    public SuccessResult(String message){
        super(true,message);
    }

    public SuccessResult(){
        super(true);
    }
}
