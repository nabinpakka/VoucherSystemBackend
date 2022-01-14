package com.example.vouchersystemapiserver.models;

import javax.persistence.*;

@Entity
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String code;
    private int value;
    private long startingTimeStamp;
    private long endingTimeStamp;
    private boolean isUsed;

    public Voucher() {
    }

    public Voucher(String code, int value) {
        this.code = code;
        this.value = value;
        this.startingTimeStamp=System.currentTimeMillis();
        this.endingTimeStamp=this.startingTimeStamp+60*24*60*60*1000;
        this.isUsed=false;
    }

    public Voucher(String code, int value, long startingTimeStamp,long durationInDays) {
        this.code = code;
        this.value = value;
        this.startingTimeStamp = startingTimeStamp;
        this.endingTimeStamp=this.startingTimeStamp+durationInDays*24*60*60*1000;
        this.isUsed=false;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getStartingTimeStamp() {
        return startingTimeStamp;
    }

    public void setStartingTimeStamp(long startingTimeStamp) {
        this.startingTimeStamp = startingTimeStamp;
    }

    public long getEndingTimeStamp() {
        return endingTimeStamp;
    }

    public void setEndingTimeStamp(long endingTimeStamp) {
        this.endingTimeStamp = endingTimeStamp;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
