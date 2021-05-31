package entity;
/*
Manager can check the rank of coaches, including rank, coachID and Number of being ordered live courses
 */
public class coachRank {
	private String rank; //coach rank
	private String id; //coach ID
	private String number; //Number of being ordered live courses
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}
