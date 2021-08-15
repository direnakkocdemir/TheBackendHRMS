package com.CCT.HRMS.core.Results;

/**
 * Define the results with their properties
 * 
 * @author Diren
 *
 */
public abstract class Result {
	
	// Properties
    private boolean success;
    private String message;
    
    // Constructors
    public Result(boolean success,String message){
        this(success);
        this.message=message;
    }

    public Result(boolean success){
        this.success=success;
    }

    public Result(){

    }
    
    // Getter methods
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
