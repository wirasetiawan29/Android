package id.or.bandungitclub.tweetme.model;

import java.io.Serializable;

public class Timeline implements Serializable {

	private String tweetId;
	private String userId;
	private String tweetMsg;
	private String tweetDate;
	private String username;
	private String fName;
	private String lName;
	
	public void setTweetId(String id){
		this.tweetId = id;
	}
	
	public String getTweetId(){
		return tweetId;
	}
	
	public void setUserId(String id){
		this.userId = id;
	}
	
	public String getUserId(){
		return userId;
	}
	
	public void setTweetMsg(String msg){
		this.tweetMsg = msg;
	}
	
	public String getTweetMsg(){
		return tweetMsg;
	}
	
	public void setTweetDate(String date){
		this.tweetDate = date;
	}
	
	public String getTweetDate(){
		return tweetDate;
	}
	
	public void setUsername(String uname){
		this.username = uname;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setFirstName(String name){
		this.fName = name;
	}
	
	public String getFirstName(){
		return fName;
	}
	
	public void setLastName(String name){
		this.lName = name;
	}
	
	public String getLastName(){
		return lName;
	}
	
}
