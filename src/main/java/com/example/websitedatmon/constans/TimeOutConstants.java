package com.example.websitedatmon.constans;

public enum TimeOutConstants {
    POST(1), // cho nhà bếp đăng
    ORDER(2), // cho nhân viên đặt

    ;

    private final Integer active;

    TimeOutConstants(Integer active) {
        this.active = active;
    }

    public Integer getValue() {
        return active;
    }

    public String getName(Integer val) {
        return (this.name());
    }
}
