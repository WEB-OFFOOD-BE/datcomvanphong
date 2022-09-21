package com.example.websitedatmon.constans;

public enum ActiveConstants {
    TRUE(1), // Đang còn
    FALSE(0), // Đã xoá

    ;

    private final Integer active;

    ActiveConstants(Integer active) {
        this.active = active;
    }

    public Integer getValue() {
        return active;
    }

    public String getName(Integer val) {
        return (this.name());
    }
}
