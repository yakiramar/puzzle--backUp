package matala;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;




public class Ex1 extends Board {
	

	public static void main(String[] args) throws IOException {
	 String algoName;//algoName get the name of the algorithm that we need to use
	 boolean withTime;// withTime get true if we need to output the time that take us to solve the problem
	                  // and get false otherwise
	 int n,m;//n will get the number of rows and m get the number of columns
	 File file = new File("intest.txt"); 
	  
	 BufferedReader br = new BufferedReader(new FileReader(file));
	  
	 String st; 
	 st = br.readLine();
	 algoName=st;
	 st = br.readLine();
	 if(st.equals("with time")) {
		 withTime=true;
		 }
	 else {
		 withTime=false;
	 }
	 st = br.readLine();
	 n=Integer.parseInt(st.substring(0,1));
	 m=Integer.parseInt(st.substring(2,3));
	 String boardFill[][]=new String[n][m];// board that we want to fill.
	 //here we fill the board
	 for(int i=0;i<n;i++) {
		 st = br.readLine();
		 String segments[] = st.split(",");
		 int v=0;
		 for(int j=0;j<m;j++) {
			 boardFill[i][j]=segments[v];
			 v++;
		 }
	 }
	 Board start=new Board(boardFill);
	 System.out.println();

	 Algo g=new Algo(withTime);
	 if(algoName.equals("BFS")) {g.BFS(start);}
	 if(algoName.equals("DFID")) {g.DFID(start, 1, 0);}
	 if(algoName.equals("A*")) {g.A_star(start);}
	 if(algoName.equals("IDA*")) {g.IDA_star(start);}
	 
	 
	 
	 

 }
	
}
