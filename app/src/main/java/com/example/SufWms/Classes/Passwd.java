package com.example.SufWms.Classes;

public class Passwd {
    private String UserId ;
    private String LoginId ;
    private String IsValid ;
    private String UserEmail ;
    private String UserGroup ;
    private String IsPrimary ;
    //Sender Email Information/settings
    private String EmailUser ;
    private String EmailPassowrd ;
    private String ServerAddress ;
    private String PortNo ;
    private String SSLEnabled ;
    private String EmailSenderName ;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public String getIsValid() {
        return IsValid;
    }

    public void setIsValid(String isValid) {
        IsValid = isValid;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserGroup() {
        return UserGroup;
    }

    public void setUserGroup(String userGroup) {
        UserGroup = userGroup;
    }

    public String getIsPrimary() {
        return IsPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        IsPrimary = isPrimary;
    }

    public String getEmailUser() {
        return EmailUser;
    }

    public void setEmailUser(String emailUser) {
        EmailUser = emailUser;
    }

    public String getEmailPassowrd() {
        return EmailPassowrd;
    }

    public void setEmailPassowrd(String emailPassowrd) {
        EmailPassowrd = emailPassowrd;
    }

    public String getServerAddress() {
        return ServerAddress;
    }

    public void setServerAddress(String serverAddress) {
        ServerAddress = serverAddress;
    }

    public String getPortNo() {
        return PortNo;
    }

    public void setPortNo(String portNo) {
        PortNo = portNo;
    }

    public String getSSLEnabled() {
        return SSLEnabled;
    }

    public void setSSLEnabled(String SSLEnabled) {
        this.SSLEnabled = SSLEnabled;
    }

    public String getEmailSenderName() {
        return EmailSenderName;
    }

    public void setEmailSenderName(String emailSenderName) {
        EmailSenderName = emailSenderName;
    }
}
