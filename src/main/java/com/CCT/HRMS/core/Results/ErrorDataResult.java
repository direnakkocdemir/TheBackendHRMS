package com.CCT.HRMS.core.Results;

public class ErrorDataResult<T> extends DataResult<T> {

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
