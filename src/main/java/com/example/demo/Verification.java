package com.example.demo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Verification implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5094556298267166819L;

    private String deviceId;
    private char verified;
    private int attempts;
    private Timestamp changedDateTime;
    private Timestamp createdDateTime;

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public char getVerified() {
        return verified;
    }

    public void setVerified(char verified) {
        this.verified = verified;
    }

    public Timestamp getChangedDateTime() {
        return changedDateTime;
    }

    public void setChangedDateTime(Timestamp changedDateTime) {
        this.changedDateTime = changedDateTime;
    }

    public Timestamp getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Timestamp createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @Override
    public String toString() {
        return "PinVerification [deviceId=" + deviceId + ", verified=" + verified + ", attempts=" + attempts + ", changedDateTime="
                + changedDateTime + ", createdDateTime=" + createdDateTime + "]";
    }
}
