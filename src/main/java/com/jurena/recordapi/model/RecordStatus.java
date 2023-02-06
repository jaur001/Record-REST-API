package com.jurena.recordapi.model;

public enum RecordStatus {
    SUCCESS("success"),
    ERROR("error");

    private final String status;

    RecordStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
