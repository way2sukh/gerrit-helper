package org.helper.gerrit.model;

/**
 * @author Sukhpal Singh
 *
 */
public class GerritProperties {
    public String serverUrl;
    public String userName;
    public String password;
    public String changeId;
    public String reviewerEmails;
    public String namedServerUrl;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    public String getReviewerEmails() {
        return reviewerEmails;
    }

    public void setReviewerEmails(String reviewerEmails) {
        this.reviewerEmails = reviewerEmails;
    }

    public String getNamedServerUrl() {
        return namedServerUrl;
    }

    public void setNamedServerUrl(String namedServerUrl) {
        this.namedServerUrl = namedServerUrl;
    }
}
