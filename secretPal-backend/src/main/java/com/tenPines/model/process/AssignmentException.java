package com.tenPines.model.process;

import com.tenPines.model.Worker;

import java.util.HashMap;
import java.util.Map;

public class AssignmentException extends RuntimeException {
    private Reason reason;
    private Map<String, Object> details = new HashMap<>();

    public AssignmentException(Reason reason, Worker worker) {
        this.reason = reason;
        this.details.put("worker", worker);
    }

    public AssignmentException(Reason reason) {
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public enum Reason{
        DOES_NOT_WANT_TO_PARTICIPATE, NOT_ENOUGH_QUORUM, CANT_SELF_ASSIGN
    }
}
