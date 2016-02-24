package tweets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Jama.Matrix;

public class LR {
	
	private int trainingset;
	private List<Tweet> tweets;
	private Map<String,Integer> vocabulary;
	private String[] classes;
	double[][] x;
	double[][] xtest;
	double[][] y;
	double[] ytest;
	double[][] theta;
	private double rate;
	private int ITERATIONS = 1500;
	Matrix th;
	int testset;
	
	
	public LR(int trainingset,List<Tweet> tweets,Map<String,Integer> vocabulary,String[] classes){
		this.trainingset = trainingset;
		this.tweets = tweets;
		this.vocabulary =vocabulary;
		this.classes = classes;
		this.rate = 0.01;
		this.testset=50;
		initialize();
	}
	
	public void train(){
		
		buildXY();
		//printArray(x,trainingset,vocabulary.size()+1);
		System.out.println();
		//printArray(y,trainingset,classes.length);
		System.out.println();
		
		
		
		for (int iter=0;iter<1000;iter++){
		
			Matrix yy = new Matrix(y);
			Matrix xx = new Matrix(x);
			th = new Matrix(theta);
			Matrix red = xx.times(th);
	/*		for(int i=0;i<trainingset;i++){
				for(int j=0;j<10;j++){
					System.out.print(red.get(i, j) +" ");
					
				}
				System.out.println();
			}*/
			double temp[][] = new double[trainingset][classes.length];
			temp = red.getArray();
			for(int i=0;i<trainingset;i++){
				for(int j=0;j<classes.length;j++){
					temp[i][j]= sigmoid(temp[i][j]);
				}
			}
			//System.out.println();
			red = new Matrix(temp);
			
	/*		for(int i=0;i<trainingset;i++){
				for(int j=0;j<10;j++){
					System.out.print(red.get(i, j) +" ");
					
				}
				System.out.println();
			}*/
			
			
			Matrix grad = red.minus(yy);
			grad = xx.transpose().times(grad);
			grad = grad.times(1.0/trainingset);
			double[][] temp2 = new double[vocabulary.size()+1][classes.length];
			temp2 = grad.getArray();
			for(int i=0;i<vocabulary.size()+1;i++){
				for(int j=0;j<classes.length;j++){
					theta[i][j] = theta[i][j] - rate * temp2[i][j];
				}
			}
			th =new Matrix(theta);
			System.out.println(iter);
			
		}


	}
	
	protected void test(){
		
		int corrects=0;
		buildXYtest();
		Matrix xxtest = new Matrix(xtest);
		Matrix res = xxtest.times(th);
		double[][] result = new double[testset][classes.length];
		result = res.getArray();
		double max=0.0;
		int clash=-1;
		for(int i=0;i<testset;i++){
			for(int j=0;j<classes.length;j++){
				if(result[i][j]>max){
					max=result[i][j];
					clash =j;
				}
			}
			if(clash == ytest[i]){
				corrects++;
			}
		}
		System.out.println("c"+corrects);
		System.out.format("Accuracy: %3.2f%% ",(double)corrects/testset*100);
		
	}
	
    private double sigmoid(double z) {
        return 1 / (1 + Math.exp(-z));
    }
	
	private void buildXY(){
		
		for(int i=0;i<trainingset;i++){
			for(int j=0;j<tweets.get(i).getWords().size();j++){
				String word = tweets.get(i).getWords().get(j);
				if(vocabulary.containsKey(word)){
					x[i][vocabulary.get(word)] = 1;
				}
				
			}
		}		
		
		for(int i=0;i<trainingset;i++){
			for(int j=0;j<classes.length;j++){
				String city = tweets.get(i).getCity();
				
				if(j==getClassid(city)){
					y[i][j] = 1;
					//System.out.println(getClassid(city));
				}
				else{
					y[i][j] = 0;
				}
				
			}
		}	
		
	}
	
	private void buildXYtest(){
		int t=0;
		for(int i=trainingset;i<trainingset+testset;i++){
			
			ytest[t] = getClassid(tweets.get(i).getCity());
			for(int j=0;j<tweets.get(i).getWords().size();j++){
				String word = tweets.get(i).getWords().get(j);
				if(vocabulary.containsKey(word)){
					xtest[t][vocabulary.get(word)] = 1;
				}
				
			}
			t++;
		}		
	
	}
	

	private void initialize(){
		
		theta= new double[vocabulary.size()+1][classes.length];
		for( int i = 0; i < vocabulary.size(); i++) {
			for( int j = 0; j < classes.length; j++) {
				theta[i][j] = 0;
			}
		}
		
	
		x = new double[trainingset][vocabulary.size()+1];
		for(int i=0;i<trainingset;i++){
			for(int j=0;j<vocabulary.size()+1;j++){
				if(j == 0){
					x[i][j] = 1;
				}
				else{
					x[i][j] = 0;
				}
			}
		}
		
		xtest = new double[testset][vocabulary.size()+1];
		for(int i=0;i<testset;i++){
			for(int j=0;j<vocabulary.size()+1;j++){
				if(j == 0){
					x[i][j] = 1;
				}
				else{
					x[i][j] = 0;
				}
			}
		}
		
		ytest= new double[testset];
		
		y = new double[trainingset][classes.length];

	
		

	}
	
	private void printArray(double[][] array,int row,int col ){
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				System.out.print(array[i][j] +" ");
			}
		System.out.println();
		}
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
