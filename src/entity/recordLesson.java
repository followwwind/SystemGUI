package entity;

import javafx.beans.property.SimpleStringProperty;
/*
record course information
 */
public class recordLesson {
	
	private SimpleStringProperty reLessName;
    private SimpleStringProperty reLessId;
    private SimpleStringProperty description;
    private SimpleStringProperty type;
    private SimpleStringProperty duration;          //the length of a lesson
    private SimpleStringProperty url;//url for the recorded courses
    private SimpleStringProperty VIP;//the corresponding member rank
    public recordLesson() {
    	
    }
	public recordLesson(String id, String name,String description, String type, String duration,String url, String VIP) {
		this.reLessName=new SimpleStringProperty(name);
		this.reLessId=new SimpleStringProperty(id);
		this.description=new SimpleStringProperty(description);
		this.type=new SimpleStringProperty(type);
		this.duration=new SimpleStringProperty(duration);
		this.url=new SimpleStringProperty(url);
		this.VIP=new SimpleStringProperty(VIP);
	}

	public String getReLessName() {
		return reLessName.get();
	}
	public void setReLessName(String reLessName) {
		this.reLessName.set(reLessName);
	}
	public String getReLessId() {
		return reLessId.get();
	}
	public void setReLessId(String reLessId) {
		this.reLessId.set(reLessId);
	}
	public String getDescription() {
		return description.get();
	}
	public void setDescription(String description) {
		this.description.set(description);
	}
	public String getType() {
		return type.get();
	}
	public void setType(String type) {
		this.type.set(type);
	}
	public String getDuration() {
		return duration.get();
	}
	public void setDuration(String duration) {
		this.duration.set(duration);
	}
	public void setUrl(String url){this.url.set(url);}
	public String getUrl(){return this.url.get();}
	public String getVIP(){return this.VIP.get();}
	public void setVIP(String VIP){this.VIP.set(VIP);}
	@Override
	public String toString() {
    	String re=reLessId.get()+","+reLessName.get()+","+description.get()+","+type.get()+","+duration.get()+","+url.get()+","+VIP.get();
		return re;
	}
}
