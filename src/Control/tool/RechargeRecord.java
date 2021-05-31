package Control.tool;

import entity.recharge;
import entity.user;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

/**
 * this class will record the member's recharge information and payment
 */
public class RechargeRecord {
	public static String memberFileName = "member.txt";
	private static Properties member;
	static {
		member=new Properties();
		FileInputStream reader=null;
		try {
			reader = new FileInputStream(memberFileName);
			member.load(reader);
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error in file");
			System.exit(0);
		}
		finally {
			try {
				reader.close();
			}
			catch (Exception ex) {}
		}
	}
	/**
	 *  Write data into file
	 */
	private static void listInfo(String list) throws FileNotFoundException {
		OutputStream ps=new FileOutputStream(memberFileName);
		try {
			member.store(ps,list);
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Exception in file operation.");
			System.exit(0);
		}
		finally {
			try {
				ps.close();
			}
			catch (Exception ex) {}
		}
	}

	/**
	 * update the recharge information in the member.txt
	 * @param userRecharge the user's id
	 * @param chargeId the id for recharge
	 * @param isPay the user's balance
	 * @param recharge the user recharged into his account
	 * @param timeOfCharge the time user recharge
	 */
	public static void updateRecharge(String userRecharge, String chargeId, String isPay, String recharge, String timeOfCharge,String VIP) throws FileNotFoundException {
		String list=chargeId+"#"+isPay+"#"+recharge+"#"+timeOfCharge+"#"+VIP;
		member.setProperty(userRecharge,list);
		listInfo(list);
	}

	/**
	 * return "recharge" object from userId, getting user's balance and total recharge, time of recharge
	 * @param userId
	 * @return
	 */
	public static recharge getRechargeInfo(String userId) {
		String chargeInfo=member.getProperty(userId);
		recharge use=new recharge();
		if(chargeInfo!=null) {
			String[] infos=chargeInfo.split("#");
			System.out.println(use.toString());
			use.setUserRecharge(userId);
			use.setChargeId(infos[0]);
			use.setIsPay(infos[1]);
			use.setRecharge(infos[2]);
			use.setTimeOfCharge(infos[3]);
			use.setVIPState(infos[4]);
			return use;
		}
		else
			return null;
	}

	/**
	 * @param userID the user's id
	 * @param money how much the user is going to pay
	 * @return new balance
	 */
	public static float payMoney(String userID, String money) throws FileNotFoundException {
		String charge=member.getProperty(userID);
		recharge use=getRechargeInfo(userID);
		float pay=Float.parseFloat(money);
		if(charge==null){  //the user didn't register a member account
			return -2;
		}
		else{
			float balance=Float.parseFloat(use.getIsPay());
			if(balance<pay){  //the balance is smaller than the payment
				return -1;
			}
			else {
				float balanceChangeTo=balance-pay; //new balance in float
				String chargeId=randomId.getRandomId();
				String newBalance=String.valueOf(balanceChangeTo);
				String payTime=getTime.getNow();
				String VIP=use.getVIPState();
				updateRecharge(userID,chargeId,newBalance,use.getRecharge(),payTime,VIP);
				return balanceChangeTo;
			}
		}
	}

	/**
	 * member's vip state will change if they recharge
	 * @param amount total recharge amount
	 */
	public static String getVIPState(float amount){
		if(0<amount&&amount<100){
			return "1";
		}
		else if(amount<200){
			return "2";
		}
		else{
			return "3";
		}
	}

	/**
	 * user will recharge into his account
	 * @param userID the user's name
	 * @param money the account the user will recharge
	 * @return balanceChangeTo is the account balance
	 */
	public static float rechargeMoney(String userID, String money) throws FileNotFoundException {
		String charge = member.getProperty(userID);
		float recharge = Float.parseFloat(money);
		String chargeId=randomId.getRandomId();
		String payTime = getTime.getNow();
		float balanceChangeTo=recharge;
		float newRecharge;
		if(charge==null||charge.equals("")){
			newRecharge=recharge;
		}
		else{
			recharge use = getRechargeInfo(userID);
			float balance = Float.parseFloat(use.getIsPay());
			balanceChangeTo = balance+recharge; //new balance in float
			float oldRecharge=Float.parseFloat(use.getRecharge());
			newRecharge=oldRecharge+recharge;//total recharge should be updated
		}
		String VIP=getVIPState(newRecharge);
		updateRecharge(userID, chargeId,  String.valueOf(balanceChangeTo), String.valueOf(newRecharge), payTime,VIP);
		return balanceChangeTo;
	}
}
