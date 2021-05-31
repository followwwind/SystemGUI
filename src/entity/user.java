package entity;

import javafx.beans.property.SimpleStringProperty;
/*
Member's personal information
 */
public class user {
    private SimpleStringProperty userId;
    private SimpleStringProperty userPassword;
    private SimpleStringProperty userEmail;
    private SimpleStringProperty userBirth;
    private SimpleStringProperty userPhoneNo;
    private SimpleStringProperty Sex;
    private SimpleStringProperty userName;
    private SimpleStringProperty invitation;
    public user(String userId,String userPassword, String userEmail, String userBirth, String userPhoneNo, String Sex, String userName, String invitation){
        this.userId=new SimpleStringProperty(userId);
        this.userPassword=new SimpleStringProperty(userPassword);
        this.userEmail=new SimpleStringProperty(userEmail);
        this.userBirth=new SimpleStringProperty(userBirth);
        this.userPhoneNo=new SimpleStringProperty(userPhoneNo);
        this.Sex=new SimpleStringProperty(Sex);
        this.userName=new SimpleStringProperty(userName);
        this.invitation=new SimpleStringProperty(invitation);
    }
    public user(){}

    public void setUserId(String userId){
        this.userId.set(userId);
    }
    public String getUserId(){
        return userId.get();
    }
    public void setUserPassword(String userPassword){
        this.userPassword.set(userPassword);
    }
    public String getUserPassword(){
        return userPassword.get();
    }
    public void setUserEmail(String userEmail){
        this.userEmail.set(userEmail);
    }
    public String getUserEmail(){
        return userEmail.get();
    }
    public void setUserBirth(String userBirth){
        this.userBirth.set(userBirth);
    }
    public String getUserBirth(){
        return userBirth.get();
    }
    public void setUserPhoneNo(String userPhoneNo){
        this.userPhoneNo.set(userPhoneNo);
    }
    public String getUserPhoneNo(){
        return userPhoneNo.get();
    }
    public void setSex(String sex){
        this.Sex.set(sex);
    }
    public String getSex(){
        return Sex.get();
    }
    public void setUserName(String userName){this.userName.set(userName);}
    public String getUserName(){return userName.get();}
    public void setInvitation(String invitation){this.invitation.set(invitation);}
    public String getInvitation(){return this.invitation.get();}
    public String toString(){
        String use=userId.get()+","+userPassword.get()+","+userEmail.get()+","+userBirth.get()+","+userPhoneNo.get()+","+Sex.get()+","+userName.get()+","+invitation.get();
        return use;
    }
}
