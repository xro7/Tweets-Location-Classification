package tweets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class NaiveBayes {
	
	private int trainTweets;
	private List<Tweet> tweets;
	String[] classes;
	int[] counter ;
	ArrayList<String>[] words;
	Set<String> uniques;
	Map<String, Integer>[] wordCount;
	Map<String, Double>[] cprob;
	double[] priorprob ;
	double[] postprob;
	double max ;
	int classchosen;
	int corrects;
	
	public NaiveBayes(int trainTweets,List<Tweet> tweets,String[] classes){
		
		this.trainTweets = trainTweets;
		this.tweets = tweets;
		this.classes = classes.clone();
		initialize();
		
		
	}
	
	protected void train(){
		
		for(int c = 0;c<classes.length;c++){
			for(int i = 0; i <trainTweets; i++) {
				if(tweets.get(i).getCity().equals(classes[c])){
					for(int j=0;j<tweets.get(i).getWords().size();j++){
						words[c].add(tweets.get(i).getWords().get(j)); //all words in a class
						uniques.add(tweets.get(i).getWords().get(j)); //unique words in the document
											
					    if (wordCount[c].containsKey(tweets.get(i).getWords().get(j))) {
					        // Map already contains the word key. Just increment it's count by 1
					        wordCount[c].put(tweets.get(i).getWords().get(j), wordCount[c].get(tweets.get(i).getWords().get(j)) + 1);
					    } else {
					        // Map doesn't have mapping for word. Add one with count = 1
					        wordCount[c].put(tweets.get(i).getWords().get(j), 1);
					    }
						
					}
					counter[c]++;
					
				}
			}	
		}
		System.out.println("Vocabulary length "+uniques.size());
		
		
		for(int c = 0;c<classes.length;c++){		
			priorprob[c] = (double)counter[c]/trainTweets;
			System.out.println("prior probability P("+classes[c]+"):"+priorprob[c]);
			System.out.println(classes[c]+ " words "+words[c].size());
		}
		
		
		for(int i=0;i<classes.length;i++){
			for (Entry<String, Integer> entry: wordCount[i].entrySet()) {
	
			    cprob[i].put(entry.getKey(), ((double)entry.getValue()+1)/(words[i].size()+uniques.size()));
				
				
			}  
		}
		
	}
	
	protected double test(){
		
		//initialize posterior probabilities
		for(int i=0;i<classes.length;i++){
			postprob[i] = priorprob[i];
		}
		int counter=0;
		for(int i =trainTweets;i<tweets.size();i++){
			
			for(int j=0;j<tweets.get(i).getWords().size();j++){
				
				for(int c=0;c<classes.length;c++){
					if (cprob[c].containsKey(tweets.get(i).getWords().get(j))){
						postprob[c] = postprob[c] * cprob[c].get(tweets.get(i).getWords().get(j));
					}else{
						postprob[c] = postprob[c] * ((double)1 / (words[c].size()+uniques.size()+1) );//unknown word smoothing
					}
				}
			}
			max = 0;	
			for(int c=0;c<classes.length;c++){
				if (postprob[c]>max){
					max = postprob[c];
					classchosen = c;
				}
			}
			if(classes[classchosen].equals(tweets.get(i).getCity())){
				//System.out.println("Correct");
				corrects++;
			}else{
				//System.out.println("Wrong");
			}		
			//initialize posterior probabilities
			for(int j=0;j<classes.length;j++){
				postprob[j] = priorprob[j];
			}
			counter++;
		}
		
		System.out.println("test tweets: "+counter);
		
		
	
		//double correctper = ((double)corrects/(tweets.size()-trainTweets))*100;
		return(((double)corrects/(tweets.size()-trainTweets))*100);
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	private void initialize(){
		
		counter = new int[10];	
		
		words = (ArrayList<String>[])new ArrayList[classes.length];
		
		for( int i = 0; i < classes.length; i++) {
		    words[i] = new ArrayList<String>();
		}
		
		uniques = new HashSet<String>();	
		wordCount = new HashMap[10];
		
		for( int i = 0; i < classes.length; i++) {
		    wordCount[i] = new HashMap<>();
		}	
	
		cprob = new HashMap[10];
		for( int i = 0; i < classes.length; i++) {
		    cprob[i] = new HashMap<>();
		}
		
		priorprob = new double[classes.length];
		
		postprob = new double[classes.length];
		
		max = 0.0;
		classchosen = -1;
		corrects=0;

	}

}
