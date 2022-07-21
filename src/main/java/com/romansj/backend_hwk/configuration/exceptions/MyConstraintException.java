package com.romansj.backend_hwk.configuration.exceptions;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to abstract away {@link javax.validation.ConstraintViolationException} to allow adding own errors to list
 */
public class MyConstraintException extends ValidationException {
    List<MyViolation> myViolationList = new ArrayList<>();

    public MyConstraintException(List<MyViolation> myViolations) {
        this.myViolationList = myViolations;
    }


    public List<MyViolation> getConstraintViolations() {
        return myViolationList;
    }


}
