package com.CCT.HRMS.core.Results;

public class ErrorResult extends Result{
    
    public ErrorResult(String message){
        super(false,message);
    }

    public ErrorResult(){
        super(false);
    }
}
