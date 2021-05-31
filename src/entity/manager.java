package entity;
/*
manager personal information
 */
public class manager {
    String id;
    String password;
    String email;
    String birth;
    String phone;
    String sex;
    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setEmail(String em){
        this.email=em;
    }
    public String getEmail(){
        return email;
    }
    public void setBirth(String birth){
        this.birth=birth;
    }
    public String getBirth(){
        return this.birth;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setSex(String sex){
        this.sex=sex;
    }
    public String getSex(){
        return this.sex;
    }
}
