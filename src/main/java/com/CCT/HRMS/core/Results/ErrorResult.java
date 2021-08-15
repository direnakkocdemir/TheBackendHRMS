package com.CCT.HRMS.core.Results;

/**
 * This class is defined for unsuccessfully results. If result is unsuccessful
 * result returns false with/without message
 * 
 * @author Diren
 *
 */
public class ErrorResult extends Result{
    
	// Constructors
    public ErrorResult(String message){
        super(false,message);
    }

    public ErrorResult(){
        super(false);
    }
}
