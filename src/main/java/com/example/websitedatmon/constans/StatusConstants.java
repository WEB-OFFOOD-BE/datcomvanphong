package com.example.websitedatmon.constans;

public enum StatusConstants {
    PROGRESSING(1),
    COOKED(2),
    RECEIVED(3),
    APPROVED(4),
    UNAPPROVED(5),
    TIME_OUT(6),
    DELETED(7)
    ;

    private final Integer active;

    StatusConstants(Integer active) {
        this.active = active;
    }

    public Integer getValue() {
        return active;
    }

    public String getName(Integer val) {
        return (this.name());
    }

}
