package entity;
/*
rank manager by their total recharge
 */
public class memberRank {
	private String rank;//rank No.
	private String id;//member ID
	private String money;//total recharge
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
}
