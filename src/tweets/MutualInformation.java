package tweets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MutualInformation {
	
	int tweetNumber;
	List<Tweet> tweets;
	String[] classes;
	Map<String,Integer>[] words;
	Set<String> uniques ;
	Map<String,Integer> vocabulary;
	int[] counter ;
	Map<String,Double>[] mi ;
	
	public MutualInformation(int tweetNumber,List<Tweet> tweets,String[] classes){
		
		this.tweetNumber = tweetNumber;
		this.tweets = tweets;
		this.classes = classes.clone();
		initialize();
		
	}
	

	

	
	protected Map<String,Double> computeMi(){
		
		int vcounter=0;
		for(int c = 0;c<classes.length;c++){
			for(int i = 0; i <tweetNumber; i++) {
				if(tweets.get(i).getCity().equals(classes[c])){
					for(int j=0;j<tweets.get(i).getWords().size();j++){
						//words[c].add(tweets.get(i).getWords().get(j)); //all words in a class
						uniques.add(tweets.get(i).getWords().get(j)); //unique words in the document
						if(!vocabulary.containsKey(tweets.get(i).getWords().get(j))){
							vocabulary.put(tweets.get(i).getWords().get(j),++vcounter);
						}
						
											
					    if (words[c].containsKey(tweets.get(i).getWords().get(j))) {
					        // Map already contains the word key. Just increment it's count by 1
					        words[c].put(tweets.get(i).getWords().get(j), words[c].get(tweets.get(i).getWords().get(j)) + 1);
					    } else {
					        // Map doesn't have mapping for word. Add one with count = 1
					        words[c].put(tweets.get(i).getWords().get(j), 1);
					    }
						
					}
					counter[c]++;
			
					
				}
			}	
		}
		//System.out.println(uniques.size());
		System.out.println("Initial vocabulary :"+vocabulary.size() +" words");
		

		
		int a=0;
		int b=0;
		int cc=0;
		int n=tweetNumber;
		
		for(int c = 0;c<classes.length;c++){
			
			
		    for (String s : uniques) {
		    	a=0;
				b=0;
			    cc=0;
		    	if (words[c].containsKey(s)){
		    		//System.out.println(words[c].get(s)+" at class"+c);
		    		a = words[c].get(s);
		    		for(int j = 0;j<classes.length;j++){
		    			if (j!=c){
		    				if(words[j].containsKey(s)){
		    					b=b+words[j].get(s);
		    				}
		    			}
		    		}
		    		cc=counter[c]-a;
		    		mi[c].put(s, Math.log((a*n)/((a+cc)*(a+b))));
		    		//System.out.println(Math.log((a*n)/((a+cc)*(a+b))));
		    		
		    	}else{
		    		mi[c].put(s, Math.log(1));
		    		//System.out.println(Math.log(0));
		    	}
		    }
		    
		}
		Map<String,Double> maxmi = new HashMap<String,Double>();
		//double average=0.0;
		for(int c = 0;c<classes.length;c++){

		    for(Iterator<Map.Entry<String, Double>> it = mi[c].entrySet().iterator(); it.hasNext(); ) {
		        Map.Entry<String, Double> entry = it.next();
		        
		        	//System.out.println(entry.getKey()+" "+entry.getValue()+" "+words[9].get(entry.getKey()));
		        	 if (maxmi.containsKey(entry.getKey()) ){
					        // Map already contains the word key. Just increment it's count by 1
					        maxmi.put(entry.getKey(),Math.max(maxmi.get(entry.getKey()), entry.getValue()));
		        	 }
		        	 else {
					        // Map doesn't have mapping for word. Add one with count = 1
					        maxmi.put(entry.getKey(), entry.getValue());
					}
		    }
		}
		
	    for(Iterator<Map.Entry<String, Double>> it = maxmi.entrySet().iterator(); it.hasNext(); ) {
	        Map.Entry<String, Double> entry = it.next();
	        //avgmi.put(entry.getKey(),entry.getValue()/10);
	        //System.out.println(entry.getKey()+" "+entry.getValue());
	        if(entry.getValue()<2.15){
	        	it.remove();
	        }
			
	        
	    }
	    System.out.println("New vocabulary :"+maxmi.size() +" words");
	    
	    return(maxmi);
	    
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void initialize()
	{
		
		words = new HashMap[classes.length];
		uniques = new HashSet<String>();
		vocabulary =  new HashMap<String,Integer>();
		
		for( int i = 0; i < classes.length; i++) {
		    words[i] = new HashMap<>();
		}	
		
		counter = new int[classes.length];
		for( int i = 0; i < classes.length; i++) {
		    counter[i]=0;
		}	
		
		mi = new HashMap[classes.length];
		
		
		for( int i = 0; i < classes.length; i++) {
		    mi[i] = new HashMap<>();
		}	
		
		
		
	}
	
	
	


}
