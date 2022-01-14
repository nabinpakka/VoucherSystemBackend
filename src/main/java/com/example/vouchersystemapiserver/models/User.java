package com.example.vouchersystemapiserver.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class User {

    @Id
    private UUID id;
    private String username;
    private float value;

    //transaction disable procedure
    private boolean transactionDisable;
    private int numberOfIncorrectAttempts;
    private long recentTimeStampForIncorrectAttempt;

    public User() {
    }

    public User(UUID id,String username) {
        this.id=id;
        this.username=username;
        this.value = 0;
        this.transactionDisable = false;
        this.numberOfIncorrectAttempts=0;
        this.recentTimeStampForIncorrectAttempt=0;
    }

    public boolean isTransactionDisable() {
        return transactionDisable;
    }

    public void setTransactionDisable(boolean transactionDisable) {
        this.transactionDisable = transactionDisable;
    }

    public void update(float value){
        this.value+=value;
    }

    public int getNumberOfIncorrectAttempts() {
        return numberOfIncorrectAttempts;
    }

    public void setNumberOfIncorrectAttempts(int numberOfIncorrectAttempts) {
        this.numberOfIncorrectAttempts = numberOfIncorrectAttempts;
    }

    public long getRecentTimeStampForIncorrectAttempt() {
        return recentTimeStampForIncorrectAttempt;
    }

    public void setRecentTimeStampForIncorrectAttempt(long recentTimeStampForIncorrectAttempt) {
        this.recentTimeStampForIncorrectAttempt = recentTimeStampForIncorrectAttempt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
