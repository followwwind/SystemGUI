package entity;

import javafx.beans.property.SimpleStringProperty;
/*
The coach personal information
 */
public class employee {
    private SimpleStringProperty EmployeeId;
    private SimpleStringProperty EmployeePassword;
    private SimpleStringProperty EmployeeEmail;
    private SimpleStringProperty EmployeeBirth;
    private SimpleStringProperty EmployeePhoneNo;
    private SimpleStringProperty EmployeeSex;
    private SimpleStringProperty EmployeeName;
    public employee(String EmployeeId,String EmployeePassword, String EmployeeEmail, String EmployeeBirth, String EmployeePhoneNo, String EmployeeSex, String EmployeeName){
        this.EmployeeId=new SimpleStringProperty(EmployeeId);
        this.EmployeePassword=new SimpleStringProperty(EmployeePassword);
        this.EmployeeEmail=new SimpleStringProperty(EmployeeEmail);
        this.EmployeeBirth=new SimpleStringProperty(EmployeeBirth);
        this.EmployeePhoneNo=new SimpleStringProperty(EmployeePhoneNo);
        this.EmployeeSex=new SimpleStringProperty(EmployeeSex);
        this.EmployeeName=new SimpleStringProperty(EmployeeName);
    }
    public employee(){}
    public void setEmployeeId(String EmployeeId){
        this.EmployeeId.set(EmployeeId);
    }
    public String getEmployeeId(){
        return EmployeeId.get();
    }
    public void setEmployeePassword(String EmployeePassword){
        this.EmployeePassword.set(EmployeePassword);
    }
    public String getEmployeePassword(){
        return EmployeePassword.get();
    }
    public void setEmployeeEmail(String EmployeeEmail){
        this.EmployeeEmail.set(EmployeeEmail);
    }
    public String getEmployeeEmail(){
        return EmployeeEmail.get();
    }
    public void setEmployeeBirth(String EmployeeBirth){
        this.EmployeeBirth.set(EmployeeBirth);
    }
    public String getEmployeeBirth(){
        return EmployeeBirth.get();
    }
    public void setEmployeePhoneNo(String EmployeePhoneNo){
        this.EmployeePhoneNo.set(EmployeePhoneNo);
    }
    public String getEmployeePhoneNo(){
        return EmployeePhoneNo.get();
    }
    public void setEmployeeSex(String sex){
        this.EmployeeSex.set(sex);
    }
    public String getEmployeeSex(){
        return EmployeeSex.get();
    }
    public void setEmployeeName(String name){
        this.EmployeeName.set(name);
    }
    public String getEmployeeName(){
        return EmployeeName.get();
    }
    public String toString(){
        String use=EmployeeId.get()+","+EmployeePassword.get()+","+EmployeeEmail.get()+","+EmployeeBirth.get()+","+EmployeePhoneNo.get()+","+EmployeeSex.get()+","+EmployeeName.get();
        return use;
    }
}
