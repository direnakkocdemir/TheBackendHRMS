package com.CCT.HRMS.core.Results;

public abstract class DataResult<T> extends Result{

    private T data;

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
    
    public T getData(){
        return this.data;
    }
}
