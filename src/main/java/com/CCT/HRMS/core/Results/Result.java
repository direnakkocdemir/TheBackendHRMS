package com.CCT.HRMS.core.Results;

public abstract class Result {

    private boolean success;
    private String message;

    public Result(boolean success,String message){
        this(success);
        this.message=message;
    }

    public Result(boolean success){
        this.success=success;
    }

    public Result(){

    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
