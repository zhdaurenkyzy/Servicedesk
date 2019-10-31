package com.epam.servicedesk.entity;

import com.epam.servicedesk.enums.Priority;

import java.time.LocalDateTime;

public class Request {
    private long id;
    private String theme;
    private String description;
    private long statusId;
    private long levelId;
    private long modeId;
    private Priority priority;
    private long groupId;
    private long engineerId;
    private long projectId;
    private long clientId;
    private long authorOfCreationId;
    private LocalDateTime dateOfCreation;
    private String decision;
    private long authorOfDecisionId;
    private LocalDateTime dateOfDecision;
    private long reportId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public long getLevelId() {
        return levelId;
    }

    public void setLevelId(long levelId) {
        this.levelId = levelId;
    }

    public long getModeId() {
        return modeId;
    }

    public void setModeId(long modeId) {
        this.modeId = modeId;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(long engineerId) {
        this.engineerId = engineerId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getAuthorOfCreationId() {
        return authorOfCreationId;
    }

    public void setAuthorOfCreationId(long authorOfCreationId) {
        this.authorOfCreationId = authorOfCreationId;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public long getAuthorOfDecisionId() {
        return authorOfDecisionId;
    }

    public void setAuthorOfDecisionId(long authorOfDecisionId) {
        this.authorOfDecisionId = authorOfDecisionId;
    }

    public LocalDateTime getDateOfDecision() {
        return dateOfDecision;
    }

    public void setDateOfDecision(LocalDateTime dateOfDecision) {
        this.dateOfDecision = dateOfDecision;
    }

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }
}
