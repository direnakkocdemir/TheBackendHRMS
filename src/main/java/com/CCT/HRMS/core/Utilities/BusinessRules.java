package com.CCT.HRMS.core.Utilities;

import com.CCT.HRMS.core.Results.*;

public class BusinessRules {
    
    public static Result run(Result... results){

        for (Result result : results) {
            if(!result.isSuccess()){
                return result;
            }
        }
        return new SuccessResult();
    }
}
