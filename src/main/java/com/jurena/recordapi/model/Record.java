package com.jurena.recordapi.model;

import java.util.Objects;

public class Record {

    private String primaryKey;
    private String name;
    private String description;
    private String updatedTimeStamp;

    public Record() {
    }

    public Record(String primaryKey, String name, String description, String updatedTimeStamp) {
        this.primaryKey = primaryKey;
        this.name = name;
        this.description = description;
        this.updatedTimeStamp = updatedTimeStamp;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Record)
            return primaryKey.equals(((Record) obj).primaryKey);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryKey, name, description, updatedTimeStamp);
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdatedTimeStamp() {
        return updatedTimeStamp;
    }

    public void setUpdatedTimeStamp(String updatedTimeStamp) {
        this.updatedTimeStamp = updatedTimeStamp;
    }
}
