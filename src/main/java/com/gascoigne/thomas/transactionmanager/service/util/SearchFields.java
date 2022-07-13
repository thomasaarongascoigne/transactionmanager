package com.gascoigne.thomas.transactionmanager.service.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

public class SearchFields implements Serializable {

    private Timestamp start;
    private Timestamp end;
    private String type;
    private String actor;
    private Map<String, String> data;

    public SearchFields(Timestamp start, Timestamp end, String type, String actor, Map<String, String> data) {
        this.start = start;
        this.end = end;
        this.type = type;
        this.actor = actor;
        this.data = data;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
