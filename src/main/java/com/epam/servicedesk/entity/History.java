package com.epam.servicedesk.entity;

import java.time.LocalDateTime;

public class History {
    private long id;
    private Request request;
    private long userId;
    private LocalDateTime dateOfChange;
    private String columnName;
    private String columnValueBefore;
    private String columnValueAfter;

    public Request getRequest() {
        return request;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(LocalDateTime dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValueBefore() {
        return columnValueBefore;
    }

    public void setColumnValueBefore(String columnValueBefore) {
        this.columnValueBefore = columnValueBefore;
    }

    public String getColumnValueAfter() {
        return columnValueAfter;
    }

    public void setColumnValueAfter(String columnValueAfter) {
        this.columnValueAfter = columnValueAfter;
    }
}
