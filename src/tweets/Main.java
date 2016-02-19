package tweets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
	
		
		Parser parser = new Parser("data/tweets-10cities-1.txt");
		try {
			parser.processLineByLine();
			List<Tweet> tweets  = new ArrayList<Tweet>(parser.getTweets());
			
			System.out.println(tweets.size() +" tweets were parsed");
			System.out.println("----");
			
		       for(int i = 0; i <tweets.size(); i++) {
		            System.out.println(tweets.get(i).getCity());
		            System.out.println(tweets.get(i).getLatidude());
		            System.out.println(tweets.get(i).getLongitude());
		            System.out.println(tweets.get(i).getText());
		            System.out.println(tweets.get(i).getUser_location());
		            for(int j = 0; j <tweets.get(i).getWords().size(); j++) {
		            	 System.out.println(tweets.get(i).getWords().get(j));
		            }
		            System.out.println("----");
		            
		            
		        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
