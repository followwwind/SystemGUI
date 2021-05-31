package entity;
/*
Member recharge information
 */
public class recharge {
    private String chargeId; //recharge id
    private String userRecharge; //user's id
    private String isPay;//balance of the user
    private String recharge;//total amount of money the user had recharged
    private String timeOfCharge; //The time to recharge
    private String VIPState;//state of VIP
    public void setChargeId(String userId){
        this.chargeId=userId;
    }
    public String getChargeId(){
        return chargeId;
    }
    public String getIsPay(){
        return isPay;
    }
    public void setIsPay(String isPay){
        this.isPay=isPay;
    }
    public void setRecharge(String recharge){this.recharge=recharge;}
    public String getRecharge(){return this.recharge;}
    public String getTimeOfCharge(){return this.timeOfCharge;}
    public void setTimeOfCharge(String timeOfCharge){this.timeOfCharge=timeOfCharge;}
    public String getUserRecharge(){return this.getUserRecharge();}
    public void setUserRecharge(String userRecharge){this.userRecharge=userRecharge;}
    public void setVIPState(String VIPState){this.VIPState=VIPState;}
    public String getVIPState(){return this.VIPState;}
}
