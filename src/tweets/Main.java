package tweets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
			System.out.println("------");
			
			//randomize tweets' list
			Collections.shuffle(tweets);

			String[] classes ={"Athens","Barcelona","Berlin","Cairo","Chicago","Hong Kong","Johannesburg","Sao Paolo","Sydney","Thessaloniki"};
			  
/*		    System.out.println("----Naive Bayes----");
  
		    int tweetstrain = 2500;
		  
		    NaiveBayes nb ;
		    
		    double sum = 0.0;
		    double acc = 0.0;
		    int[][] cm = new int[10][10];
			for(int i=0;i<10;i++){
				for(int j=0;j<10;j++){
					cm[i][j]=0;
				}
			}

		    int n=20;
		    for(int i=0;i<n;i++){
		    	System.out.println("Naive Bayes classifier with  " +(tweetstrain*i+10)+" tweets as training set and "+(tweets.size()-(tweetstrain*i+10))+" tweets to test ");
		    	nb = new NaiveBayes(tweetstrain*i+10, tweets, classes);
			    nb.train();
			    acc = nb.test();
			    sum += acc;
			    System.out.format("Accuracy: %3.2f%%%n",acc);
			    cm = addArrays(cm, nb.getConfusionMatrix(),10);
		    }
		   System.out.println("------------------"); 
		   System.out.format("Overall Accuracy: %3.2f%%%n",sum/n);
		   System.out.println("Aggregated Confusion matrix");  
		  
		    for(int i=0;i<10;i++){
		    	
		    	 for(int j=0;j<10;j++){
		    		 
		    		 System.out.format(" %5d ",cm[i][j]);
		    		 
		    	 }
		    	 System.out.println();
		    }*/
		    
		    
			
			
			
			
			
			int trainingset=2500;
			

		    

			//System.out.println(maxmi.size());
		    NaiveBayes nb ;

		    double acc = 0.0,acc2=0.0;
		    double sum=0.0,sum2=0.0;
		    int n=20;
		    for(int i=0;i<n;i++){
		    	
				MutualInformation mi = new MutualInformation(trainingset*i+10, tweets, classes);
				Map<String,Double> maxmi   =mi.computeMi();
		   
		    	nb = new NaiveBayes(trainingset*i+10, tweets,maxmi, classes);
			    nb.train();
			    acc = nb.test();
			    sum += acc;
			    System.out.format("Accuracy with smaller vocabulary: %3.2f%%%n ",acc);
			    
		    	nb = new NaiveBayes(trainingset*i+10, tweets, classes);
			    nb.train();
			    acc2 = nb.test();
			    sum2 += acc2;
			    System.out.format("Accuracy: %3.2f%%%n",acc2);
			    
		    }
		   System.out.println("------------------"); 
		   System.out.format("Overall Accuracy with smaller vocabulary: %3.2f%%%n",sum/n);
		   System.out.format("Overall Accuracy: %3.2f%%%n",sum2/n);
		    
		    



				
			
			
			
			
			
		    	
		    	
			
		

		

	}
	
	private static int [][] addArrays(int[][] a,int[][] b,int n){
		
		int[][] array = new int[n][n];
		
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				array[i][j]=a[i][j] + b[i][j];
			}
		}
		return(array);
		
	}

}
