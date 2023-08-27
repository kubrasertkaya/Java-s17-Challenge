package com.workintech.course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CourseExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CourseErrorResponse> handleException(CourseException exception){
        CourseErrorResponse respone=new CourseErrorResponse(exception.getStatus().value(),
                exception.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(respone,exception.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<CourseErrorResponse> handleException(Exception exception){
        CourseErrorResponse respone=new CourseErrorResponse(HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(respone,HttpStatus.BAD_REQUEST);
    }
}
