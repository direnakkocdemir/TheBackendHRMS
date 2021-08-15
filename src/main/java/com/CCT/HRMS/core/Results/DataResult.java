package com.CCT.HRMS.core.Results;

/**
 * 
 * @author diren
 *
 * @param <T> parameter which is defined for methods
 */

public abstract class DataResult<T> extends Result{
	
	// Properties
    private T data;

    // Constructors
    public DataResult(T data,boolean success, String message) {
        super(success, message);
        this.data=data;
    }
    public DataResult(T data, boolean success){
        super(success);
        this.data=data;
    }

    public DataResult(boolean success,String message){
        super(success,message);
    }

    public DataResult(boolean success){
        super(success);
    }
    
    // Getter method
    public T getData(){
        return this.data;
    }
}
