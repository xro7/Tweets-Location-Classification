package tweets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
			System.out.println("------");
			
			//randomize tweets' list
			Collections.shuffle(tweets);

			
			  
		    System.out.println("----Naive Bayes----");
  
		    int tweetstrain = 2500;
		  
		    NaiveBayes nb ;
		    String[] classes ={"Athens","Barcelona","Berlin","Cairo","Chicago","Hong Kong","Johannesburg","Sao Paolo","Sydney","Thessaloniki"};
		    double sum = 0.0;
		    double acc = 0.0;
		    int[][] cm = new int[10][10];

		    int n=20;
		    for(int i=0;i<n;i++){
		    	System.out.println("Naive Bayes classifier with  " +(tweetstrain*i+10)+" tweets as training set and "+(tweets.size()-(tweetstrain*i+10))+" tweets to test ");
		    	nb = new NaiveBayes(tweetstrain*i+10, tweets, classes);
			    nb.train();
			    acc = nb.test();
			    sum += acc;
			    System.out.format("Accuracy: %3.2f%%%n",acc);
			    cm = nb.getConfusionMatrix();
		    }
		   System.out.println("------------------"); 
		   System.out.format("Overall Accuracy: %3.2f%%%n",sum/n);
		   System.out.println("Confusion matrix of the last run");  
		   //int sum2=0;
		    for(int i=0;i<10;i++){
		    	 for(int j=0;j<10;j++){
		    		 System.out.format(" %4d ",cm[i][j]);
		    		 //sum2+=cm[i][j];
		    	 }
		    	 System.out.println();
		    }
		    //System.out.println("sum = "+sum2);
		    



			
		

		

	}

}
