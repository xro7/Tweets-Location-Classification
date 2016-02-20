package tweets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
	
		
		Parser parser = new Parser("data/tweets-10cities-1.txt");
		try {
			parser.processLineByLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			List<Tweet> tweets  = new ArrayList<Tweet>(parser.getTweets());
			
			System.out.println(tweets.size() +" tweets were parsed");
			System.out.println("----");
			//System.out.println(tweets.get(32).getText());
			Collections.shuffle(tweets);
			//System.out.println(tweets.get(32).getText());
			
/*		       for(int i = 0; i <tweets.size(); i=i+3000) {
		            System.out.println(tweets.get(i).getCity());
		            System.out.println(tweets.get(i).getLatidude());
		            System.out.println(tweets.get(i).getLongitude());
		            System.out.println(tweets.get(i).getText());
		            System.out.println(tweets.get(i).getUser_location());
		            for(int j = 0; j <tweets.get(i).getWords().size(); j++) {
		            	 System.out.println(tweets.get(i).getWords().get(j));
		            }
		            System.out.println("----");
		            
		            
		        }*/
			
			  
		    System.out.println("----Naive Bayes----");
		    
		    
		    int tweetstrain = 49500;
		  
		    
		    String[] classes ={"Athens","Barcelona","Berlin","Cairo","Chicago","Hong Kong","Johannesburg","Sao Paolo","Sydney","Thessaloniki"};
		    
		    NaiveBayes nb = new NaiveBayes(tweetstrain, tweets, classes);
		    nb.train();
		    System.out.format("Accuracy: %3.2f%n ",nb.test());


			
		

		

	}

}
