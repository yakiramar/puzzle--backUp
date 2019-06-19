package matala;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Board {
	String plate[][];
	String path="";
	int count=0;
	int level;
	boolean out=false;
	public Board() {
		
	}
	public Board(String[][] board) {
		plate=new String[board.length][board[0].length];
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[0].length;j++) {
				plate[i][j]=board[i][j];
			}
		}
	 plate=board;
	 
	}
	public Board(Board b) {
		this.plate=new String[b.plate.length][b.plate[0].length];
		for(int i=0;i<b.plate.length;i++) {
			for(int j=0;j<b.plate[0].length;j++) {
				this.plate[i][j]=b.plate[i][j];
					
				}
		}
		this.path=b.path;
		this.count=b.count;
		this.level=b.level;
	}
	@Override 
	public boolean equals(Object obj) {
		if (!(obj instanceof Board))
			return false;	
		if (obj == this)
			return true;
		for(int i=0;i<((Board)obj).plate.length;i++) {
			for(int j=0;j<((Board)obj).plate[0].length;j++)
				if(!((Board)obj).plate[i][j].equals(this.plate[i][j])) {
					return false;
				}
		}
		
		return true;
		
	}
    @Override
	public int hashCode(){
		return plate.length;//for simplicity reason
	}
	//to copy another board
	public void copy(Board b) {
		this.plate=new String[b.plate.length][b.plate[0].length];
		for(int i=0;i<b.plate.length;i++) {
			for(int j=0;j<b.plate[0].length;j++) {
				this.plate[i][j]=b.plate[i][j];
					
				}
		}
		this.path=b.path;
		this.count=b.count;
		this.level=b.level;
		
	}
	
	//this function get n*m and return hoe the board need to fill
	public static Board FindGoal(int n, int m) {
	String b[][]=new String[n][m];
	  int v=1;
	  for(int i=0;i<n;i++) {
		for(int j=0;j<m;j++) {
			if(v<=(n*m)-2) {b[i][j]=Integer.toString(v);}
			else b[i][j]="_";
			v++;
		}
	  }
	  Board g=new Board(b);
	  return g;
	}
	//Goal get a Board b and return if b is our goal
	public boolean Goal(Board b) {
		Board g=new Board();
		g=FindGoal(b.plate.length, b.plate[0].length);
		for(int i=0;i<b.plate.length;i++) {
			for(int j=0;j<b.plate[0].length;j++)
				if(!b.plate[i][j].equals(g.plate[i][j])) {
					return false;
				}
		}
		
		return true;
	}
	public static void clearTheFile() throws IOException {
        FileWriter fwOb = new FileWriter("output.txt", false); 
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }
	public static void writeToFile(Board b , int num2, double totalTime, boolean withTime, boolean noPath) throws IOException {
		try
		{  
	//	String dir = System.getProperty("user.dir");
		    String filename="output.txt";
//		    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		    File file=new File(filename);
		    boolean flag=false;
		    if(!file.exists()||(flag)) {
		    	file.createNewFile();
		    	FileWriter fw = new FileWriter(file.getAbsoluteFile());
		    	BufferedWriter bw= new BufferedWriter(fw);
		    	
		    
		    clearTheFile();
		    if(!noPath) {
		    	bw.write("no path");
		    }
		    else {
		    fw.write(b.path.substring(0,b.path.length()-1));//appends the string to the file
		  
		    }
		    fw.write("\n");
		    fw.write("Num: "+num2);
		    fw.write("\n");
		    fw.write("Cost: "+b.count);
		    if(withTime) {
		    fw.write("\n");
		    fw.write(totalTime+" seconds");
		    }
		    
		    
		    
		    fw.close();
		}//else-the file exsist
		    else {
		    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		    clearTheFile();
		    if(!noPath) {
		    	fw.write("no path");
		    }
		    else {
		    fw.write(b.path.substring(0,b.path.length()-1));//appends the string to the file
		  
		    }
		    fw.write("\n");
		    fw.write("Num: "+num2);
		    fw.write("\n");
		    fw.write("Cost: "+b.count);
		    if(withTime) {
		    fw.write("\n");
		    fw.write(totalTime+" seconds");
		    }
		    
		    
		    
		    fw.close();
		    }
		
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	        
	        
	}
	//print the board
	public static void print(Board board) {
		for(int i=0;i<board.plate.length;i++) {
			 System.out.println();
			 for(int j=0;j<board.plate[0].length;j++) {
				 System.out.print(board.plate[i][j]+" ");
			 }
		 }
		System.out.println();
	}
	
	//operators:Arranged by order ratio on developing vertices
	
	//2 blocks to the left
	public boolean towLeft() {
		if(this.plate.length==1) {return false;}
		for(int i=0;i<this.plate.length-1;i++) {
			for(int j=0;j<this.plate[0].length-1;j++) {
				if((this.plate[i][j].equals("_"))&&(this.plate[i+1][j].equals("_"))) {
					System.out.println("1 operation");
					System.out.println("towLeft");
					this.plate[i][j]=this.plate[i][j+1];
					this.plate[i+1][j]=this.plate[i+1][j+1];
					this.plate[i][j+1]="_";
					this.plate[i+1][j+1]="_";
					this.path=this.path+this.plate[i][j]+"&"+this.plate[i+1][j]+"L-";
					count+=6;
					return true;
				}
			}
		}
		
	    return false;	
	}
	//2 blocks up
	public boolean towUp() {
		if(this.plate.length==1) {return false;}
		for(int i=0;i<this.plate.length-1;i++) {
			for(int j=0;j<this.plate[0].length-1;j++) {
				if((this.plate[i][j].equals("_"))&&(this.plate[i][j+1].equals("_"))) {
					System.out.println("2 operation");
					this.plate[i][j]=this.plate[i+1][j];
					this.plate[i][j+1]=this.plate[i+1][j+1];
					this.plate[i+1][j]="_";
					this.plate[i+1][j+1]="_";
					this.path=this.path+this.plate[i][j]+"&"+this.plate[i][j+1]+"U-";
					count+=7;
					return true;
				}
			}
		}
		return false;
		
	}
	//2 blocks to the right
	public boolean towRight() {
		if(this.plate.length==1) {return false;}
		for(int i=0;i<this.plate.length-1;i++) {
			for(int j=1;j<this.plate[0].length;j++) {
				if((this.plate[i][j].equals("_"))&&(this.plate[i+1][j].equals("_"))) {
					System.out.println("3 operation");
					this.plate[i][j]=this.plate[i][j-1];
					this.plate[i+1][j]=this.plate[i+1][j-1];
					this.plate[i][j-1]="_";
					this.plate[i+1][j-1]="_";
					this.path=this.path+this.plate[i][j]+"&"+this.plate[i+1][j]+"R-";
					count+=6;
					return true;
				}
			}
		}
		return false;
		
	}
	//2 blocks down
	public boolean towDown() {
		for(int i=1;i<this.plate.length;i++) {
			for(int j=0;j<this.plate[0].length-1;j++) {
				if((this.plate[i][j].equals("_"))&&(this.plate[i][j+1].equals("_"))) {
					System.out.println("4 operation");
					this.plate[i][j]=this.plate[i-1][j];
					this.plate[i][j+1]=this.plate[i-1][j+1];
					this.plate[i-1][j]="_";
					this.plate[i-1][j+1]="_";
					this.path=this.path+this.plate[i][j]+"&"+this.plate[i][j+1]+"D-";
					count+=7;
					return true;
				}
			}
		}
		return false;
		
	}
	//1 block to the left
	public boolean oneLeft(int fos) {//fos-first or second "_" to know witch of them we need to deal with
		String s="5 operation";
		for(int i=0;i<this.plate.length;i++) {
			for(int j=0;j<this.plate[0].length;j++) {
				if(this.plate[i][j].equals("_")) {
					if(fos==1) {
						if((j!=this.plate[0].length-1)&&(!this.plate[i][j+1].equals("_"))) {
					        System.out.println(s);
				        	this.plate[i][j]=this.plate[i][j+1];
					        this.plate[i][j+1]="_";
				         	this.path=this.path+this.plate[i][j]+"L-";
					        count+=5;
					        return true;	
						 }
						else {
							return false;
						}
					 }//close if(fos==1)
				
				else {
					if((this.plate[i][j].equals("_"))&&(fos==2)) {//case that we want the second "_"
						fos=1;
						s="9 operation";
					 }
				}
			  }//close first if
				
			}
		}
		return false;
	}
	//1 block up
	public boolean oneUp(int fos) {//fos-first or second "_" to know witch of them we need to deal with
		String s="6 operation";
		for(int i=0;i<this.plate.length;i++) {
			for(int j=0;j<this.plate[0].length;j++) {
				if(this.plate[i][j].equals("_")) {
					if(fos==1) {
						if((i!=this.plate.length-1)&&(!this.plate[i+1][j].equals("_"))) {
							System.out.println(s);
							this.plate[i][j]=this.plate[i+1][j];
							this.plate[i+1][j]="_";
							this.path=this.path+this.plate[i][j]+"U-";
							count+=5;
							return true;	
						 }
						else {
							return false;
						}
					 }//close if(fos==1)
				
				else {
					if((this.plate[i][j].equals("_"))&&(fos==2)) {//case that we want the second "_"
						fos=1;
				     	s="10 operation";
					 }
				}
			  }//close first if
				
			}
		}
		return false;
	}	
	//one block to the right
	public boolean oneRight(int fos) {//fos-first or second "_" to know witch of them we need to deal with
		String s="7 operation";
		for(int i=0;i<this.plate.length;i++) {
			for(int j=0;j<this.plate[0].length;j++) {
				if(this.plate[i][j].equals("_")) {
					if(fos==1) {
						if((j!=0)&&(!this.plate[i][j-1].equals("_"))) {
							System.out.println(s);
							this.plate[i][j]=this.plate[i][j-1];
							this.plate[i][j-1]="_";
							this.path=this.path+this.plate[i][j]+"R-";
							count+=5;
							return true;
						 }
						else {
							return false;
						}
					 }//close if(fos==1)
				
				else {
					if((this.plate[i][j].equals("_"))&&(fos==2)) {//case that we want the second "_"
						fos=1;
						s="11 operation";
					 }
				}
			  }//close first if
				
			}
		}
		return false;
	}	
		
	//1 block down
	public boolean oneDown(int fos) {//fos-first or second "_" to know witch of them we need to deal with
		String s="8 operation";
		for(int i=0;i<this.plate.length;i++) {
			for(int j=0;j<this.plate[0].length;j++) {
				if(this.plate[i][j].equals("_")) {
					if(fos==1) {
						if((i!=0)&&(!this.plate[i-1][j].equals("_"))) {
							System.out.println(s);
							this.plate[i][j]=this.plate[i-1][j];
							this.plate[i-1][j]="_";
							this.path=this.path+this.plate[i][j]+"D-";
							count+=5;
							return true;
						 }
						else {
							return false;
						}
					 }//close if(fos==1)
				
				else {
					if((this.plate[i][j].equals("_"))&&(fos==2)) {//case that we want the second "_"
						fos=1;
						s="12 operation";
					 }
				}
			  }//close first if
				
			}
		}
		return false;
	}	
		
}
