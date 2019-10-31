package com.epam.servicedesk.entity;

import java.time.LocalDateTime;

public class RequestState {
    private Long requestId;
    private String requestTheme;
    private String statusName;
    private Long requestPriorityId;
    private String groupName;
    private String engineerName;
    private String projectName;
    private String clientName;
    private String authorCreationName;
    private LocalDateTime requestDateOfCreation;
    private String authorOfDecisionName;
    private LocalDateTime requestDateOfDecision;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getRequestTheme() {
        return requestTheme;
    }

    public void setRequestTheme(String requestTheme) {
        this.requestTheme = requestTheme;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getRequestPriorityId() {
        return requestPriorityId;
    }

    public void setRequestPriorityId(Long requestPriorityId) {
        this.requestPriorityId = requestPriorityId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAuthorCreationName() {
        return authorCreationName;
    }

    public void setAuthorCreationName(String authorCreationName) {
        this.authorCreationName = authorCreationName;
    }

    public LocalDateTime getRequestDateOfCreation() {
        return requestDateOfCreation;
    }

    public void setRequestDateOfCreation(LocalDateTime requestDateOfCreation) {
        this.requestDateOfCreation = requestDateOfCreation;
    }

    public String getAuthorOfDecisionName() {
        return authorOfDecisionName;
    }

    public void setAuthorOfDecisionName(String authorOfDecisionName) {
        this.authorOfDecisionName = authorOfDecisionName;
    }

    public LocalDateTime getRequestDateOfDecision() {
        return requestDateOfDecision;
    }

    public void setRequestDateOfDecision(LocalDateTime requestDateOfDecision) {
        this.requestDateOfDecision = requestDateOfDecision;
    }
}
