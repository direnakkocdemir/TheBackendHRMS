package com.CCT.HRMS.core.Results;

public class SuccessResult extends Result {
    
    public SuccessResult(String message){
        super(true,message);
    }

    public SuccessResult(){
        super(true);
    }
}
