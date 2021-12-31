import java.util.Random; 
import java.util.LinkedList;
import java.io.FileWriter; 
import java.io.File;
import java.io.FileNotFoundException;  
import java.util.Scanner; 
import java.io.IOException; 
import java.lang.Object;
import BasicIO.*;

public class Assign_4 {
  
  /*
   * Fouzan Abdullah
   * 6840797            */
  
  public static void main(String[] args){
    
    ASCIIOutputFile output = new ASCIIOutputFile("output.txt");

    System.out.println("Program Starts");

    char wall = '1';
   
    char[][] maze;
    int r = 0;
    int c = 0;
    
    int[] rowcol = getRowCol();
    maze = new char [rowcol[0]][rowcol[1]+1];
    char[] line = new char[11];
    boolean secondline = false;
    
     try {
      File myObj = new File("mz1.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        for(int k = 0; k < data.length(); k++)
        {
         if(secondline)
           {
             maze[r-1][c] = data.charAt(k);
             c += 1;  
           }  
        }
        r+=1;
        c=0;

       secondline = true;
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

      int [] startend = findRandom(maze);
      while(maze[startend[0]][startend[1]] == '#' || maze[startend[2]][startend[3]] == '#' || (startend[0] != startend[2] && startend[1] != startend[3])){
        startend = findRandom(maze);
      }
      
      maze[startend[0]][startend[1]] = 'G';
      maze[startend[2]][startend[3]] = 'H';

      recursiveMaze(maze, startend[0], startend[1], output);
  }
  
  public static void printMaze(char[][] maze,  ASCIIOutputFile output){
  
    System.out.print("\n\n");
    for(int i = 0; i<maze.length; i++)
    {
       for(int j = 0; j<maze[0].length; j++)
       {
        if(maze[i][j] == ' ')
         {
          maze[i][j] = '.';
         } 
         output.writeC(maze[i][j]);
       }
         output.newLine(); 
    }
     System.out.print("\n\n");
  }
  
  public static int[] findRandom(char[][] maze){
    
    int large = 99;
    int small = 54;
    
    int start = (int)((maze.length - 1) * Math.random());
    int end = (int)((maze[0].length - 1) * Math.random());
    int _start = (int)((maze.length - 1) * Math.random());
    int _end = (int)((maze[0].length - 1) * Math.random());
    int [] startend = new int [] {start,end,_start,_end};
    
    return startend;
  }
  
  public static int[] getRowCol(){
    
    int row = 0;
    int col = 0;
     try {
      File myObj = new File("mz1.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        col = data.length();
        row+=1;
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    } 
    System.out.println("Row "+row);
    System.out.println("Col "+col);
 
    return new int[]{row,col};  
  }
  
  public static void recursiveMaze(char [][] maze, int _start, int _end, ASCIIOutputFile output){
    
    char wall = '#';
    char start = 'G';
    char end = 'H';
    char dot = '.';
    char up = '^';
    char down = 'v';
    char left ='<';
    char right = '>';
    
    char path = findPath(maze, _start, _end);
    
    System.out.print(path+ " Path");
    
    switch (path) {
      case '.':
        System.out.print("GAME ENDS HERE");
        System.out.print("\nCurrent Signal "+ path);
        printMaze(maze, output);
        break;
        
      case '^':
        maze[_start-1][_end] = up;
        System.out.print("\nCurrent Signal "+ path);
        recursiveMaze(maze, _start-1, _end, output);
        break;
        
      case 'v':
        maze[_start+1][_end] = down;
        System.out.print("\nCurrent Signal "+ path);
        recursiveMaze(maze, _start+1, _end,output);
        break;
      
      case '>':
        maze[_start][_end+1] = right;
        System.out.print("\nCurrent Signal "+ path);
        recursiveMaze(maze, _start, _end+1,output);
        break;
      
      case '<':
        maze[_start][_end-1] = left;
        System.out.print("\nCurrent Signal "+ path);
        recursiveMaze(maze, _start, _end-1,output);
        break;
        
       case 'H':
         System.out.print("\nCurrent Signal "+ path);
         System.out.print("\nGame Ends");
         printMaze(maze, output);
         break;
    }
  }
  
  public static char findPath(char [][] maze, int _start, int _end){
    
    char signal = ' ';

     if( maze[_start+1][_end] == 'H' || maze[_start-1][_end] == 'H' || maze[_start][_end+1] == 'H' || maze[_start][_end-1] == 'H'){
     signal = 'H';
    }
    else if( maze[_start+1][_end] == '#' && maze[_start-1][_end] == '#' && maze[_start][_end+1] == '#' && maze[_start][_end-1] == '#'){
     signal = '.';
    }
    else if(maze[_start-1][_end] == ' '){
      signal = '^';
    }
     else if(maze[_start+1][_end] == ' '){
      signal = 'v';
    }
     else if(maze[_start][_end+1] == ' '){
      signal = '>';
    }
     else if(maze[_start][_end-1] == ' '){
      signal = '<';
    }
     else{
     signal = '.';
     }
    return signal;
  }
}