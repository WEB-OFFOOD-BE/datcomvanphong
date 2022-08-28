package com.example.websitedatmon.constans;

public enum StatusConstants {
    PROGRESSING(1), // chờ xử lý
    COOKED(2), // đã hoàn thành
    RECEIVED(3), // Đã nhận
    APPROVED(4), // xác nhận yêu cầu
    UNAPPROVED(5), // Không xác nhận yêu cầu
    TIME_OUT(6), // Hết giờ
    DELETED(7) // Đã xoá
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
