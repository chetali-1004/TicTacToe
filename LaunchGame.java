import java.util.*;

/*
Tic Tac Toe is nothing but a 2D (3 rows and each row has 3 columns) character array containing X and O.
 */

import java.util.Scanner;

class TicTacToe {
    //declaring the array only. The array will be created during the object creation of this class because class does not exist in reality. what exists in reality is objects to those classes.

    static char[][] board;

    //The board is made static so that we can access the board in another class without creating an object using just the class name.

    //To initialise an object during object creation, we use the concept of CONSTRUCTORS.

    //The constructor is as follows: It has the same name as the class without any return type
    public TicTacToe(){
        board = new char[3][3];

        //array is an object in java and objects are initialised with default values which is "/u0000",i.e., null character. We want all the cells to store spaces instead of null character. To do this, we create method initBoard() to initialise the array with spaces.
        initBoard();
    }
    void initBoard(){
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[0].length; j++){
                board[i][j] = ' ';
            }
        }
    }

    //method to display the board:
    static void dispBoard(){
        System.out.println("-------------");
        for(int i = 0; i<board.length; i++){
            System.out.print("| ");
            for(int j = 0; j<board[0].length; j++){
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
    //creating a method to place X nad O in the board:
    static void placeMark(int row, int col, char mark){
        if(row >= 0 && row<=2 && col>=0 && col<=2) {
            board[row][col] = mark;
        }
        else{
            System.out.println("invalid position");
        }
    }

    static boolean checkColumnWin(){
        for(int j = 0; j<board[0].length; j++){
            if(board[0][j]!= ' ' && board[0][j]==board[1][j] && board[1][j] == board[2][j]){
                return true;
            }
        }
        return false;
    }

    static boolean checkRowWin(){
        for(int i = 0; i<3; i++){
            if(board[i][0]!= ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]){
                return true;
            }
        }
        return false;
    }

    static boolean checkDiagonalWin(){
        if (board[1][1]!= ' ' && ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) || (board[0][2] == board[1][1] && board[1][1] == board[2][0]))){
            return true;
        }
        return false;
    }
}

abstract class Player{
    String name ;
    char mark;

    abstract void makeMove();

    boolean isValidMove(int row, int col){
        if(row>=0 && row <=2 && col>=0 && col<=2){
            if(TicTacToe.board[row][col] == ' '){
                return true;
            }
        }
        return false;
    }
}
class HumanPlayer extends Player{

    HumanPlayer(String name, char mark){
        this.name = name;
        this.mark = mark;
    }

    void makeMove(){
        Scanner sc = new Scanner(System.in);
        int row; int col;
        do{
            System.out.println("Enter the row and column");
            row = sc.nextInt();
            col = sc.nextInt();
        }while (!isValidMove(row, col));

        TicTacToe.placeMark(row, col, mark);
    }
}

class AIplayer extends Player{

    AIplayer(String name, char mark){
        this.name = name;
        this.mark = mark;
    }
    void makeMove(){
        Scanner sc = new Scanner(System.in);
        int row; int col;
        do{
            Random r = new Random();
            //nextInt() method of random class generates a random integer
            row = r.nextInt(3); // This generates a random integer out of 0, 1, 2 which are the row numbers (similarly for column)
            col = r.nextInt(3);
        }while(!isValidMove(row, col));

        TicTacToe.placeMark(row, col, mark);
    }
}

public class LaunchGame {
    public static void main(String[] args) {
        TicTacToe t = new TicTacToe();
        HumanPlayer p1 = new HumanPlayer("Bob", 'X');
//        HumanPlayer p2 = new HumanPlayer("Alice", 'O');
        AIplayer p2 = new AIplayer("TAI", 'O');
        //creating a refernce (not an object) to track the current player (cp)
//        HumanPlayer cp; // This can be used only when both players are human players.


        // When 1 player is of class HumanPlayer and other of class AIplayer, we make use of INHERITANCE - PARENT REFERENCE TO CHILD OBJECT. Therefore, create a parent class of HumanPlayer and AIplayer which will be called player, i.e., HumanPlayer extends Player and AIplayer extends Player.


        // Now, inheritance has the benefit of code re-usability. Therefore, we can put some code from AIplayer and HumanPlayer in player class.
        //The player with mark 'X' always goes first

        Player cp;

        //reference type assignment in java:
        cp = p1;
        int count = 0;
        while(true){
            System.out.println(cp.name + "'s turn");
            count++;
            if(count>9){
                System.out.println("the board is full and no one has won. It is a draw match");
                break;
            }
            cp.makeMove();
            TicTacToe.dispBoard();
            if(TicTacToe.checkColumnWin() || TicTacToe.checkRowWin() || TicTacToe.checkDiagonalWin()){
                System.out.println(cp.name + " has won!");
                break;
            }
            else{
                if(cp==p1){
                    cp = p2;
                }
                else{
                    cp = p1;
                }
            }
        }
    }
}
