package tweets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LogisticRegression {
	
	
	private int trainTweets;
	private List<Tweet> tweets;
	private Map<String,Integer> vocabulary;
	private String[] classes;
	double max ;
	int classchosen;
	int corrects;
	int[][] confusionMatrix;
	boolean useImportantWords = false;
	HashMap<String,Double> important;
	//int[][] X;
	Map<Integer,Integer>[] x;
	double[][] theta;
	private double rate;
	private int ITERATIONS = 1500;
	
	
	public LogisticRegression(int trainTweets,List<Tweet> tweets,Map<String,Integer> vocabulary,String[] classes){
		this.trainTweets = trainTweets;
		this.tweets = tweets;
		this.vocabulary =vocabulary;
		this.classes = classes;
		this.rate = 0.1;
		initialize();
	}
	
	
	
	protected void train(){
	
		Random random = new Random();
		x = buildX();
		//System.out.println(x[3].size());
		int[] label=new int[classes.length];
		int xj=0;
		for (int n=0; n<1; n++) {
			
	        double biasGradient = 0.0;
	        double[][] gradients = new double[theta.length][classes.length];
			
			for (int c=0; c<classes.length; c++) {
				for (int i=0; i<trainTweets; i++) {	    	
			        
			        double predicted = classify(x[i],c);
			        label[c] = (c == getClassid(tweets.get(i).getCity())) ? 1:0;
			        
			        double error = predicted - label[c];
			        biasGradient += (error);
			        
			     
			        for (int j=0; j<theta.length; j++) {
			        	if(j==0){
			        		xj=1;
			        	}
			        	for(int z=0; z<x[i].size(); z++){	        	
				        		if (j==x[i].get(z)){
				        			xj=1;
			        		}
			        	}
	
			        	gradients[i][c] += (error) * xj;
			            xj=0;
			        }

			    }
		        for (int i = 0; i < theta.length; i++) {
		            gradients[i][c] = gradients[i][c] / trainTweets;
		        }

		        //biasWeight += rate * biasGradient / examples.size();
		        for (int i = 0; i < theta.length; i++) {
		            theta[i][c] += rate * gradients[i][c] / trainTweets;
		        }
				
		    }

		    //System.out.println("iteration: " + n /*+ " " + Arrays.toString(theta)*/ + " mle: " + lik);

		}
    	
		System.out.println("trained");
		
		
	}
	

	
	@SuppressWarnings("unchecked")
	private Map<Integer,Integer>[] buildX(){
		
		Map<Integer,Integer>[] array;
		array = new HashMap[trainTweets];
		for( int i = 0; i < trainTweets; i++) {
			array[i] = new HashMap<>();
		}
		
		for(int i=0;i<trainTweets;i++){
			for(int j=0;j<tweets.get(i).getWords().size();j++){
				array[i].put(j,vocabulary.get(tweets.get(i).getWords().get(j)));//(aa,vocabulary position)
				//System.out.println("tweet"+i+" "+tweets.get(i).getWords().get(j));
			}	
		}
		return array;
	}
	
	@SuppressWarnings("unchecked")
	private void initialize(){
		
		theta= new double[vocabulary.size()+1][classes.length];
		for( int i = 0; i < vocabulary.size(); i++) {
			for( int j = 0; j < classes.length; j++) {
			theta[i][j] =0;
			}
		}
		
	
		x = new HashMap[trainTweets];
		for( int i = 0; i < trainTweets; i++) {
		    x[i] = new HashMap<>();
		}
	}
	
    private double sigmoid(double z) {
        return 1 / (1 + Math.exp(-z));
    }
    
    private double classify(Map<Integer,Integer> x,int c) {
        double logit = .0;
        //////// dotnforget the 1
/*        for (int i=0; i<theta.length;i++)  {
            logit += theta[i] * x[i];
        }*/
        for (int i=0; i<x.size();i++)  {
        	//System.out.println(x.get(i));
        	
        	logit +=  theta[x.get(i)][c]*1;
        }
        logit+= theta[0][c];
        //System.out.println(logit);
        //System.out.println(sigmoid(logit));
        return sigmoid(logit);
    }
    
    private int getClassid(String c){
    	int id =-1;
    	for (int i=0;i<classes.length;i++){
    		if(c.equals(classes[i])){
    			id=i;
    		}
    	}
    	return id;
    }

}
