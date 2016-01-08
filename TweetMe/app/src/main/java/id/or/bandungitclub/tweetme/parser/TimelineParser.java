package id.or.bandungitclub.tweetme.parser;

import id.or.bandungitclub.tweetme.model.Timeline;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TimelineParser {
	public Vector<Timeline> parseTimeline(String data){
		Vector<Timeline> vTimeline = new Vector<Timeline>();
		try {				
			JSONObject jObj = new JSONObject(data);
			JSONArray jArr = jObj.getJSONArray("data");
			for (int i = 0; i < jArr.length(); i++) {
				vTimeline.addElement(parse(jArr.getJSONObject(i)));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vTimeline;
	} 
	
	private Timeline parse(JSONObject jObj){
		Timeline tl = new Timeline();
		
		try {
			tl.setTweetId(jObj.getString("TWEET_ID"));
			tl.setUserId(jObj.getString("USER_ID"));
			tl.setTweetMsg(jObj.getString("TWEET_MSG"));
			tl.setTweetDate(jObj.getString("TWEET_DATE"));
			tl.setUsername(jObj.getString("USERNAME"));
			tl.setFirstName(jObj.getString("FIRST_NAME"));
			tl.setLastName(jObj.getString("LAST_NAME"));			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		return tl;
	}
}
