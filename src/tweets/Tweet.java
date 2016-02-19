package tweets;

import java.util.ArrayList;
import java.util.List;

public class Tweet {
	
	private String city;
	private String latidude;
	private String longitude;
	private String text;
	private String user_location;
	private List<String> words;
	
	public Tweet(String city,String latitude,String longitude,String text,String user_location,List<String> words){
		this.city = city;
		this.latidude = latitude;
		this.longitude = longitude;
		this.text = text;
		this.user_location = user_location;
		this.setWords(new ArrayList<String>(words));
	}
	
	public Tweet(String city,String latitude,String longitude,String text,List<String> words){
		this.city = city;
		this.latidude = latitude;
		this.longitude = longitude;
		this.text = text;
		this.user_location = "-";
		this.setWords(new ArrayList<String>(words));
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLatidude() {
		return latidude;
	}
	public void setLatidude(String latidude) {
		this.latidude = latidude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUser_location() {
		return user_location;
	}
	public void setUser_location(String user_location) {
		this.user_location = user_location;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

}
