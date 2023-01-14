package com.payeshgaran.workflow.exceptions;

import java.io.Serial;

public class LogicErrorException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 676916457036693128L;

    public LogicErrorException(String message) {
        super(message);
    }
}
