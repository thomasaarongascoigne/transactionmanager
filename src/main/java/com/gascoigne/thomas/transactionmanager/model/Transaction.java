package com.gascoigne.thomas.transactionmanager.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private Timestamp timestamp;

    private String type;

    private String actor;

    @ElementCollection(fetch=FetchType.EAGER)
    @MapKeyColumn(name="key_string", length = 50, nullable = false)
    @Column(name="value_string", length = 150, nullable = false)
    @CollectionTable(name="transaction_data", joinColumns=@JoinColumn(name="transaction_id"))
    Map<String, String> data = new HashMap<String, String>();

    public Transaction() {}

    public Transaction(Timestamp timestamp, String type, String actor, Map<String, String> data) {
        this.timestamp = timestamp;
        this.type = type;
        this.actor = actor;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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
