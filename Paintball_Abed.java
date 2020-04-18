import java.util.Scanner;
import java.util.Random;
import java.lang.Math;

public class Paintball_Abed{
  public static void main(String[] args){
    // the next few line we make and modify and print out grid
    char[][] my_grid = createGrid(10, 10, '-');
    my_grid = modifyGrid(my_grid);
    System.out.println("---------- GRID ----------");
    displayGrid(my_grid);
    // the next few lines we store the cordinets of each target and soilder and display them to the termial
    int[] s = getCoordinates(my_grid, 0, 0 , 'S');
    int[] t1 = getCoordinates(my_grid, 0, 0 , 'T');
    int[] t2 = new int[2];
    if(t1[1] < my_grid[0].length - 1){
      t2 = getCoordinates(my_grid, t1[0], t1[1] + 1 , 'T');
    }else{
      t2 = getCoordinates(my_grid, t1[0] + 1 , 0, 'T');
    }

    System.out.println();
    System.out.println();
    System.out.println("Solider points: (" + s[0] + "," + s[1] + ")");
    System.out.println("Target points: (" + t1[0] + "," + t1[1] + ") (" + t2[0] + "," + t2[1] + ")");
    // the next few lines we choose the closert target and do the distamnce we display them
    int[] closer_target = chooseTarget(s, t1, t2);
    System.out.println("ALI!! ELIMINATE TARGET LOCATED IN THESE COORDINATES: (" + closer_target[0] + ", " + closer_target[1] + ")");

    Double distance_to_target = getDistance(s, closer_target);
    System.out.print("Distance from Soldies to Target is ");
    System.out.printf("%.3f", distance_to_target);
    System.out.println(" m");

    // here we do the travel time and display it
    int fps_usa = 2421;
    bulletTravelTime(distance_to_target, fps_usa);

    // kill the targer and display the grid after murder
    char[][] killed_target_grid = eliminateTarget(my_grid, closer_target);
    System.out.println();
    System.out.println();
    displayGrid(killed_target_grid);

  
  }

  /*
  * this method takes number of rows and columns and symbols and it will creat
  * a 2d array sized the number of rows and columns and fill it using 
  * the provided symbol, and retuens a 2d grid thats filled with the symbol.
  */
  public static char[][] createGrid(int rows, int columns, char symbol){
    char[][] grid = new char[rows][columns];
    for(int i = 0; i < grid.length; i++){
      for(int j = 0; j < grid[i].length; j++){
        grid[i][j] = '-';
      }
    }
    return grid;
  }
  /*
  * this method takes 2d array and display it on the scrren
  * it add number on top and side based on the grid length and it makes it looks nice
  * it dose not retuen anything it only prints
  */
  public static void displayGrid(char[][] grid){

    System.out.print("  ");
    for(int i = 0; i < grid.length; i++){
      System.out.print(i + " ");
    }
    System.out.println();
    for(int i = 0; i < grid.length; i++){
      System.out.print(i + " ");
      for(int j = 0; j < grid[i].length; j++){
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
  }
  /*
  * this method take 2d array and randmly fills two(not the same target) and one player all random
  * it retuen that 2d array grid filled with 3 random sopt for target and player
  */
  public static char[][] modifyGrid(char[][] grid){
    // creating the random obj inside the method
    Random rndm = new Random();
    // here we generate 6 new random numbers each 2 are point on the grid
    int[] s_player = {rndm.nextInt(grid.length - 1),rndm.nextInt(grid.length - 1)};
    int[] t_1 = {rndm.nextInt(grid.length - 1),rndm.nextInt(grid.length - 1)};
    int[] t_2 = {rndm.nextInt(grid.length - 1),rndm.nextInt(grid.length - 1)};
    // the next few lines we have to check if we have the same x,y for the players
    while(s_player[0] == t_1[0] && s_player[1] == t_1[1]){
      s_player[0] = rndm.nextInt(grid.length - 1);
    }
    while(s_player[0] == t_2[0] && s_player[1] == t_2[1]){
      s_player[0] = rndm.nextInt(grid.length - 1);
    }
    while(t_1[0] == t_2[0] && t_1[1] == t_2[1]){
      t_1[0] = rndm.nextInt(grid.length - 1);
    }

    // here we insert the random points into out grid
    grid[s_player[0]][s_player[1]] = 'S';
    grid[t_1[0]][t_1[1]] = 'T';
    grid[t_2[0]][t_2[1]] = 'T';
    return grid;
    
  }
  /*
  * this mathod takes 2d array as input and a char symbol it will search for the symbol recusrsivly and that why it also take two extra rows and clos input
  * it return 1d array which are the x,y of the symbol we give it
  */
  public static int[] getCoordinates(char[][] grid, int row, int columns, char symbol){
    if(grid[row][columns] == symbol){
      int[] cordi = {row, columns};
      return cordi;
    }else if(row < grid.length - 1 || columns < grid[row].length - 1){
      columns += 1;
      if(columns > grid[row].length - 1){
        columns = 0;
        row += 1 ;
      }
    }

    return getCoordinates(grid, row, columns, symbol);

  }
  /*
  * this method only tales 1d array and prints its value
  */
  public static void printCoordinates(int[] points){
    for(int i = 0; i < points.length - 1; i++){
      System.out.println(points[i]);

    }
  }
  /*
  * this method takes two 1d array and using the distance formula it get the distance between the points
  * the return type is double because the formual sqrt the answer
  */
  public static double getDistance(int[] player, int[] target){
    int player_x = player[1];
    int player_y = player[0];

    int target_x = target[1];
    int target_y = target[0];

    double dis_x = (target_x - player_x) * (target_x - player_x);
    double dis_y = (target_y - player_y) * (target_y - player_y);

    double dis_x_y = dis_x + dis_y;

    return Math.sqrt(dis_x_y);

  }
  /*
  * this method takes 3 1d array the first one is where the player is located and the other two are the targets
  * it call the getDistance method and compare which is the closer target to the player and retuen it x,y
  */
  public static int[] chooseTarget(int[] player, int[] target, int[] target2){
    double a = getDistance(player, target);
    double b = getDistance(player, target2);

    if(a > b)
      return target2;
    else if (a < b)
      return target;
    else
      return target;
  }
  /*
  * this method tasks the 2d array and also takes 1d array with the x,y for the closer target
  * it replases the target after being killed with X
  * it retuen the 2d array with X instead of a T
  */
  public static char[][] eliminateTarget(char[][] grid, int[] target){
    grid[target[0]][target[1]] = 'X';
    return grid;
  }
  /*
  * this method takes distance and the fps and get the bullet travel time 
  * it also print the final number with only 3 decimal places
  */
  public static void bulletTravelTime(double dis, double fps){
    double time = dis / fps;
    System.out.println();
    System.out.print("Bullet arrived to target in ");
    System.out.printf("%.3f", time);
    System.out.println(" seconds");

  }
}