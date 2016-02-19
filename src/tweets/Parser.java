package tweets;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Parser {
	
  
  private Path fFilePath;
  List<Tweet> tweets = new ArrayList<Tweet>();
  
  public Parser(String aFileName){
    this.fFilePath = Paths.get(aFileName);
  }
  
  

  public final void processLineByLine() throws IOException {

    try (Scanner scanner =  new Scanner(fFilePath, "UTF-8")){
    	int i=0;
    
    	while (scanner.hasNextLine() && i<5){
    			
    	  processLine(scanner.nextLine() );
    	  
    	  i++;
    	}		 
    	    
    }
  }
 

  protected void processLine(String aLine)  throws IOException {
  
	  
	List<String> data = new ArrayList<String>();
    try (Scanner s = new Scanner(aLine)){;
    	s.useDelimiter("\\t");
	    while (s.hasNext()){
	    	data.add(s.next());
	    	
	    	//System.out.println(s.next());
	    }  
	    
	    List<String> words = processText(data.get(3));
	    
	    
	    if (data.size()==5){
	    	tweets.add(new Tweet(data.get(0),data.get(1),data.get(2),data.get(3),data.get(4),words));
	    }else if (data.size()==4){
	    	tweets.add(new Tweet(data.get(0),data.get(1),data.get(2),data.get(3),words));
	    }else if (data.size()<4){
	    	//System.out.println("not enough data");
		}
    }
 
  }
  
  protected List<String> processText(String text)  throws IOException {
	   
		  
		List<String> words = new ArrayList<String>();
	    try (Scanner s = new Scanner(text)){;
	    	s.useDelimiter("\\s+");
		    while (s.hasNext()){
		    	words.add(s.next());
		    	
		    	//System.out.println(s.next());
		    }  
/*		    for (int i=0;i<words.size();i++){
		    	System.out.println(words.get(i));
		    }*/
		    
		    return(words);
		    
		   
	    }
	 
  }
  
  public List<Tweet> getTweets(){
	  return tweets;
  }
  
}
