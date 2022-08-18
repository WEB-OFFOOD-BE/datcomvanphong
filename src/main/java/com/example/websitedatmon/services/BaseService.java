package com.example.websitedatmon.services;

import com.example.websitedatmon.constans.StatusConstants;

public class BaseService {
    public String checkStatus(Integer statusId){
        switch (statusId){
            case 1: return StatusConstants.PROGRESSING.getName(1);
            case 2: return StatusConstants.COOKED.getName(2);
            case 3: return StatusConstants.RECEIVED.getName(3);
            case 4: return StatusConstants.APPROVED.getName(4);
            case 5: return StatusConstants.UNAPPROVED.getName(5);
            case 6: return StatusConstants.TIME_OUT.getName(6);
            case 7: return StatusConstants.DELETED.getName(7);
        }
        return null;
    }
}
