package matala;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Algo extends Board{
	int num=0;//num-Number of nodes that extend from the open list
	boolean withTime;
	double startTime = System.nanoTime();
	
	public Algo() {
		
	}              
	public Algo(boolean withTime) {
		this.withTime=withTime;
	}
	public int h(Board b) {
		if(Goal(b)) {return 0;}
		int n=b.plate.length, m=b.plate[0].length;
		int hirst=n*m-2;
		int v=0;
		Board g=FindGoal(n, m);
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				v++;
				if(b.plate[i][j].equals(g.plate[i][j])&&(v<(n*m)-2)) {
					hirst--;
				}
			}
		}
		return hirst;
	}
	//f(n)=g(n)+h(n)| g(n)=cost from the start node to this node.
	//                h(n)=the evaluation of the best(cheap) path to the goal.
	public int f(Board b) {
		return h(b)+b.count;
	}
	//min return the minimal 
	public int min(int x,int y) {
		if(x<y) {return x;}
		else return y;
	}
	Comparator<Board> h_priority = new Comparator<Board>() {
        @Override
        public int compare(Board b1, Board b2) {
            return (b1.count+h(b1)) - (b2.count+h(b2));
        }
    };
    
    Board findIfPresent(Board source, HashSet<Board> set)
	{
	   if (set.contains(source)) {
	      for (Board obj : set) {
	        if (obj.equals(source)) 
	          return obj;
	      } 
	   }

	  return null;
	}
	
	//BFS
	public void BFS(Board start) throws IOException {
        HashSet<Board> openList = new HashSet<Board>(); 
        Queue<Board> openListQ = new LinkedList<>();
        openList.add(start);
        openListQ.add(start);
        HashSet<Board> closeList = new HashSet<Board>();
        Board curBoard=new Board();//create current board
    	Board copyBoard=new Board();//create copy Board //we need to crete a new Board evry time
    	boolean allowed=false, found=false, noPath=false;
    	int countOperation;
    	int v=0;
        while((!openListQ.isEmpty())&&(v<200)&&(found==false)) {
        	curBoard=openListQ.remove();//remove the first node from the queue
        	num++;
        	openList.remove(curBoard);//remove this node from open list
        	closeList.add(curBoard);//put the node in the close list 
        	copyBoard.copy(curBoard);
        	System.out.println("the board now");
        	print(copyBoard);
        	for(int i=1;(i<=12)&&(found==false);i++) {
        		
        		  if(i==1)  allowed=copyBoard.towLeft();
        		  if(i==2)  allowed=copyBoard.towUp();
        		  if(i==3)  allowed=copyBoard.towRight();
        		  if(i==4)  allowed=copyBoard.towDown();
        		  if(i==5)  allowed=copyBoard.oneLeft(1);
        		  if(i==6)  allowed=copyBoard.oneUp(1);
        		  if(i==7)  allowed=copyBoard.oneRight(1);
        		  if(i==8)  allowed=copyBoard.oneDown(1);
        		  if(i==9)  allowed=copyBoard.oneLeft(2);
        		  if(i==10) allowed=copyBoard.oneUp(2);
        		  if(i==11) allowed=copyBoard.oneRight(2);
        		  if(i==12) allowed=copyBoard.oneDown(2);
        		  	  
        		  System.out.println("the cuurent board");
                  print(curBoard);     
             	  System.out.println("the board after operator"+i);
             	  print(copyBoard);
        	    
        	      if((allowed==true)&&(!openList.contains(copyBoard))&&(!closeList.contains(copyBoard))) {
        	    	  System.out.println("checking if operator"+i+" get to the goal");
        	    	  if(Goal(copyBoard)) {
        	    		  noPath=true;
        	    		  double endTime   = System.nanoTime();
        	    		  double totalTime = (endTime - startTime)/1000000000;
        	    		  writeToFile(copyBoard, num, totalTime, withTime, noPath);
        	    		  found=true;
        	    		  }	
        	    	  else {
        	    	     System.out.println("operator "+i+" create new board that not in open and close list");
           	    	     openListQ.add(copyBoard);
   		                 openList.add(copyBoard);
        	    	  }
        		        
        	       }
        	      
        	  
        	 copyBoard=new Board(curBoard);
            }//close for
        	v++;
        }//close while
        if(openListQ.isEmpty()) {
        	double endTime   = System.nanoTime();
		    double totalTime = (endTime - startTime)/1_000_000_000;
		    writeToFile(copyBoard, num, totalTime, withTime, noPath);
		  }
        
	}
	//DFID
	public void DFID(Board start, int deep, int num2) throws IOException {
		    HashSet<Board> allNodes = new HashSet<Board>(); 
	        Stack<Board> stackFronitor = new Stack<Board>();
	        start.level=0;
	        allNodes.add(start);
	        stackFronitor.push(start);
	        Board curBoard=new Board();//create current board
	    	Board copyBoard=new Board();//create copy Board //we need to crete a new Board evry time
	    	boolean allowed=false, found=false, noPath=false;
	    	int v=0;
	        while((!stackFronitor.isEmpty())&&(found==false)) {
	        	curBoard=stackFronitor.pop();//remove the first node from the queue
	        	num2++;
	        	copyBoard.copy(curBoard);
	        	System.out.println("the board now");
	        	print(copyBoard);
	        	for(int i=12;(i>0)&&(found==false);i--) {
	        		
	        		  if(i==1)  allowed=copyBoard.towLeft();
	        		  if(i==2)  allowed=copyBoard.towUp();
	        		  if(i==3)  allowed=copyBoard.towRight();
	        		  if(i==4)  allowed=copyBoard.towDown();
	        		  if(i==5)  allowed=copyBoard.oneLeft(1);
	        		  if(i==6)  allowed=copyBoard.oneUp(1);
	        		  if(i==7)  allowed=copyBoard.oneRight(1);
	        		  if(i==8)  allowed=copyBoard.oneDown(1);
	        		  if(i==9)  allowed=copyBoard.oneLeft(2);
	        		  if(i==10) allowed=copyBoard.oneUp(2);
	        		  if(i==11) allowed=copyBoard.oneRight(2);
	        		  if(i==12) allowed=copyBoard.oneDown(2);
	        		  	  
	        		  System.out.println("the cuurent board");
	                  print(curBoard);     
	             	  System.out.println("the board after operator"+i);
	             	  print(copyBoard);
	        	    
	        	      if((allowed==true)&&(!allNodes.contains(copyBoard))&&(!stackFronitor.contains(copyBoard))) {
	        	    	  System.out.println("checking if operator"+i+" get to the goal");
	        	    	  if(Goal(copyBoard)) {
	        	    		  noPath=true;
	        	    		  double endTime   = System.nanoTime();
	        	    		  double totalTime = (endTime - startTime)/1000000000;
	        	    		  writeToFile(copyBoard, num2, totalTime, withTime, noPath);
	        	    		  found=true;
	        	    		  }	
	        	    	  else {
	        	    	     System.out.println("operator "+i+" create new board that not in open and close list");
	        	    	     copyBoard.level=curBoard.level+1;
	        	    	     if(copyBoard.level<deep) {stackFronitor.push(copyBoard);}
	        	    	     
	        	    	     allNodes.add(copyBoard);
	        	    	  }
	        	    	  num2++;
	        	       }
	        	  
	        	 copyBoard=new Board(curBoard);
	            }//close for	
	        	
	        }//close while
	        if(deep==1) {
	        	v=allNodes.size();
	        	deep++;
	        	
	        	DFID(start,deep, num2);
	        	}
//	        deep++;
	        else {  if((v!=allNodes.size())&&(found==false)) {
	        	deep++;
	        	v=allNodes.size();
	        	DFID(start,deep, num2);
	        }
	        //if there is no change it's mean that there is no path
	        else if(found==false) {
	        	double endTime   = System.nanoTime();
			    double totalTime = (endTime - startTime)/1_000_000_000;
			    writeToFile(copyBoard, num2, totalTime, withTime, noPath);
	          }
	        }	      
	
	}
	//A star
	public void A_star(Board start) throws IOException {
        HashSet<Board> openList = new HashSet<Board>(); 
        PriorityQueue<Board> openListQ = new PriorityQueue<>(h_priority);
        openList.add(start);
        openListQ.add(start);
        HashSet<Board> closeList = new HashSet<Board>();
        Board curBoard=new Board();//create current board
    	Board copyBoard=new Board();//create copy Board //we need to crete a new Board evry time
    	boolean allowed=false, found=false, noPath=false;
    	int countOperation;
    	int v=0;
        while((!openListQ.isEmpty())&&(v<200)&&(found==false)) {
        	curBoard=openListQ.remove();//remove the first node from the queue
        	num++;
        	openList.remove(curBoard);//remove this node from open list
        	closeList.add(curBoard);//put the node in the close list 
        	copyBoard.copy(curBoard);
        	System.out.println("the board now");
        	print(copyBoard);
        	for(int i=1;(i<=12)&&(found==false);i++) {
        		
        		  if(i==1)  allowed=copyBoard.towLeft();
        		  if(i==2)  allowed=copyBoard.towUp();
        		  if(i==3)  allowed=copyBoard.towRight();
        		  if(i==4)  allowed=copyBoard.towDown();
        		  if(i==5)  allowed=copyBoard.oneLeft(1);
        		  if(i==6)  allowed=copyBoard.oneUp(1);
        		  if(i==7)  allowed=copyBoard.oneRight(1);
        		  if(i==8)  allowed=copyBoard.oneDown(1);
        		  if(i==9)  allowed=copyBoard.oneLeft(2);
        		  if(i==10) allowed=copyBoard.oneUp(2);
        		  if(i==11) allowed=copyBoard.oneRight(2);
        		  if(i==12) allowed=copyBoard.oneDown(2);
        		  	  
        		  System.out.println("the cuurent board");
                  print(curBoard);     
             	  System.out.println("the board after operator"+i);
             	  print(copyBoard);
        	    
        	      if((allowed==true)&&(!openList.contains(copyBoard))&&(!closeList.contains(copyBoard))) {
        	    	  System.out.println("checking if operator"+i+" get to the goal");
        	    	  if(Goal(copyBoard)) {
        	    		  noPath=true;
        	    		  double endTime   = System.nanoTime();
        	    		  double totalTime = (endTime - startTime)/1000000000;
        	    		  writeToFile(copyBoard, num, totalTime, withTime, noPath);
        	    		  found=true;
        	    		  }	
        	    	  else {
        	    	     System.out.println("operator "+i+" create new board that not in open and close list");
//        	    	     copyBoard.func=h(copyBoard)+copyBoard.count;
           	    	     openListQ.add(copyBoard);
   		                 openList.add(copyBoard);
        	    	  }
        		        
        	       }
        	      
        	  
        	 copyBoard=new Board(curBoard);
            }//close for
        	v++;
        }//close while
        if(openListQ.isEmpty()) {
        	double endTime   = System.nanoTime();
		    double totalTime = (endTime - startTime)/1_000_000_000;
		    writeToFile(copyBoard, num, totalTime, withTime, noPath);
		  }
        
	}
	
	
	
	
	
	
	
	
	//IDA star
	public void IDA_star(Board start) throws IOException{
		Stack<Board> L = new Stack<Board>();
		HashSet<Board> H = new HashSet<Board>(); 
		boolean allowed=false,found =false,noPath=false,notChange=true;
		Board n=new Board();
		Board copyBoard=new Board();//create copy Board //we need to crete a new Board evry time
		Board temp=new Board();
        int minF;
        int threshold=h(start);
        while((threshold!=1000)&&(found==false)) {
        	minF=1000;
        	L.push(start);
        	H.add(start);
        	while(!L.isEmpty()&&(found==false)) {
        		n=L.pop();
        		System.out.println("the new board");
        		print(n);
        		if(n.out==true) {
        			H.remove(n);
        		}
        		else {
        			n.out=true;
        			copyBoard=new Board(n);
        			L.push(n);
        			System.out.println("the board now");
                	print(copyBoard);
                	PriorityQueue<Board> howFirst = new PriorityQueue<>(h_priority);
        			//for each allowed operator copyBoard on n
        			for(int i=1;(i<=12)&&(found==false);i++) {
    	        		
  	        		  if(i==1)  allowed=copyBoard.towLeft();
  	        		  if(i==2)  allowed=copyBoard.towUp();
  	        		  if(i==3)  allowed=copyBoard.towRight();
  	        		  if(i==4)  allowed=copyBoard.towDown();
  	        		  if(i==5)  allowed=copyBoard.oneLeft(1);
  	        		  if(i==6)  allowed=copyBoard.oneUp(1);
  	        		  if(i==7)  allowed=copyBoard.oneRight(1);
  	        		  if(i==8)  allowed=copyBoard.oneDown(1);
  	        		  if(i==9)  allowed=copyBoard.oneLeft(2);
  	        		  if(i==10) allowed=copyBoard.oneUp(2);
  	        		  if(i==11) allowed=copyBoard.oneRight(2);
  	        		  if(i==12) allowed=copyBoard.oneDown(2);
  	        		  
  	        		  if((allowed==true)&&(f(copyBoard)>threshold)) {
  	        			  howFirst.add(copyBoard);
 
  	        		  }//enf if
  	        		copyBoard=new Board(n);
  	        		minF=min(minF,f(copyBoard));
        			}//end for
        			copyBoard=howFirst.poll();
        			while((notChange)&&(!howFirst.isEmpty())) {
  	        		  if(H.contains(copyBoard)&&(H.contains(copyBoard.out)==true)) {
  	        			copyBoard=howFirst.poll();  
  	        		  }
  	        		  else {
  	        			  notChange=false;
  	        		  }
        			}
  	        		  if(H.contains(copyBoard)&&(H.contains(copyBoard.out)==false)) {
  	        			  temp=findIfPresent(copyBoard, H);
  	        			  if(f(temp)>f(copyBoard)) {
  	        				 H.remove(copyBoard);
  	        				 L.remove(copyBoard);
  	        			  }
  	        			  else {
  	        				while((notChange)&&(!howFirst.isEmpty())) {
  	      	        		  if(H.contains(copyBoard)&&(H.contains(copyBoard.out)==true)) {
  	      	        			copyBoard=howFirst.poll();  
  	      	        		  }
  	      	        		  else {
  	      	        			  notChange=false;
  	      	        		  }
  	            			}
  	        			  }
  	        		  }
  	        		if(Goal(copyBoard)) {
      	    		  noPath=true;
      	    		  double endTime   = System.nanoTime();
      	    		  double totalTime = (endTime - startTime)/1000000000;
      	    		  writeToFile(copyBoard, num, totalTime, withTime, noPath);
      	    		  found=true;
      	    		  }	
  	        		System.out.println("the board befor go to h and l");
  	        		print(copyBoard);
  	        		L.push(copyBoard);
  	        		H.add(copyBoard);
  	        		
  	        		  
        			
        		}//end else
        		
        		
        	}//end second while
        	threshold=minF;
        }//end first while
        if(found==false) {
        double endTime   = System.nanoTime();
	    double totalTime = (endTime - startTime)/1_000_000_000;
	    writeToFile(copyBoard, num, totalTime, withTime, noPath);
        }
		
	}
	
	
	

}
