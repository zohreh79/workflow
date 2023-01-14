package com.payeshgaran.workflow.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

@Getter
@Setter
public class LogicExceptions {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public LogicExceptions(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}
