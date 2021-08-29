package com.msy.badgeuse.control;

import java.sql.Date;
import java.sql.Time;

public class Pointing {
    private final String identifier;
    private final String date;
    private final String startAt;
    private final String endAt;

    public Pointing(String identifier, String date, String startAt, String endAt) {
        this.identifier = identifier;
        this.date = date;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public Pointing(String identifer, String date, String startAt) {
        this.identifier = identifer;
        this.date = date;
        this.startAt = startAt;
        this.endAt = null;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDate() {
        return date;
    }

    public String getStartAt() {
        return startAt;
    }

    public String getEndAt() {
        return endAt;
    }
}
