package core;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Created by daniel walker on 27/07/17.
 * Represents the 10x10 board in a game of Swords and Shields
 * Each square on the boards can either be empty or have 1 of 24 pieces placed on it
 * The board has two players ONLY
 */
public class Board {
    private static int BOARD_SIZE = 11;


    /**
     * The SwordsAndShields board is a 2-dimensional array 10x10 squares
     */
    private BoardGame[][] board = new BoardGame[BOARD_SIZE][BOARD_SIZE];

    private char[][] drawArray = new char[(BOARD_SIZE * 3)][(BOARD_SIZE * 3)];



    public Board(Player p1, Player p2){
        board[1][1] = p1;
        board[8][8] = p2;
    }


    /**
     *
     * @param player
     * For using the creation square to place a token in their respective positions
     * Check which player via their ID
     */
    public void PlaceToken(Player player, Token tokenID){

        //if this is player one
        if(player.playersID == 1)
            board[2][2] = tokenID;//creation spot for player1
        else if (player.playersID == 2)
            board[7][7] = tokenID; //creation spot for player2
    }

    private int[] FindTokenPosOnBoard(Token token, char ID) throws NoSuchElementException{
        int[] tokenLoc = new int[2];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] instanceof Token) {
                    Token t = ((Token) board[row][col]);
                    if (t.tokenID == ID) {
                        tokenLoc[0] = row;
                        tokenLoc[1] = col;
                        return tokenLoc;
                    }
                }
            }
        }

       throw new NoSuchElementException("Could not find " + token);
    }


    public boolean canWeAddAPiece(Player player){
        if (player.getPlayerID() == '1'){
            return board[2][2] instanceof BoardGame ? false : true;
        }
        if (player.getPlayerID() == '2'){
            return board[7][7] instanceof BoardGame ? false : true;
        }
        System.out.println("Creation Square not empty");
        return false;
    }


    public void pushTokenUp(Token t, char ID) {
        int[] tokenPos = FindTokenPosOnBoard(t, ID);
        int r = tokenPos[0];
        int c = tokenPos[1];
        if (r == 0)
            return;
        if (r == 2 && c == 1)
            return; // cant move onto player 1
        if (r == 9 && c == 8)
            return;// cant move onto p2
        board[r - 1][c] = board[r][c];
        board[r][c] = null;

    }

    public void pushTokenDown(Token t, char ID) {
        int[] tokenPos = FindTokenPosOnBoard(t, ID);
        int r = tokenPos[0];
        int c = tokenPos[1];
        if (r == 9)
            return;
        if (r == 7 && c == 8)
            return;
        if (r == 0 && c == 1)
            return;
        if(board[r + 1][c] instanceof Token){

        }
        board[r + 1][c] = board[r][c];
        board[r][c] = null;

    }

    public void pushTokenLeft(Token t, char ID) {
        int[] tokenPos = FindTokenPosOnBoard(t, ID);
        int r = tokenPos[0];
        int c = tokenPos[1];
        if (c == 0)
            return;
        if (r == 1 && c == 2)
            return;
        if (r == 8 && c == 9)
            return;
        board[r][c - 1] = board[r][c];
        board[r][c] = null;

    }

    public void pushTokenRight(Token t, char ID) {
        int[] tokenPos = FindTokenPosOnBoard(t, ID);
        int r = tokenPos[0];
        int c = tokenPos[1];
        if (c == 9)
            return;
        if (r == 8 && c == 7)
            return;
        if (r == 1 && c == 0)
            return;
        board[r][c + 1] = board[r][c];
        board[r][c] = null;

    }

    public Token getToken(char ID) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] instanceof Token) {
                    Token t = ((Token) board[i][j]);
                    if (t.tokenID == ID)
                        return t;

                }
            }
        }
    return null;
    }


    /**
     * Draws the appropriate char for showing the board.
     * Loops through each colmn and row drawing underlines or straight slashes
     */
    public void showBoard() {
        drawArray = new char[(BOARD_SIZE * 3)][(BOARD_SIZE * 3)];
        loadBoard();
        for (int row = 0; row < drawArray.length-3; row++) { //loop for each row
            System.out.println();
            if (row % 3 == 0) {
                System.out.print("_________________________________________\n");
            }
            for (int col = 0; col < drawArray.length; col++) { // loop for each column
                if (col % 3 == 0) {
                    System.out.print('|');
                }

                if (drawArray[row][col] == '\u0000') {
                    System.out.print(" ");

                } else {
                    System.out.print(drawArray[row][col]);
                }

            }
        }
        System.out.println();
        System.out.print("\n");
        System.out.println();
        loadBoard();
    }


    /**
     * For allocating board pieces to its correct location on the board
     */
    public void loadBoard(){
        for(int row = 0; row<board.length; row++){
            for (int col = 0; col<board.length; col++){
                if (board[row][col] instanceof Token){
                    Token token = (Token) board[row][col];
                    int draw_row = (row * 3) + 1;
                    int draw_col = (col * 3) + 1;
                    drawArray[draw_row][draw_col] = token.tokenID;

                    if (token.getBottom() != 'W'){
                        drawArray[draw_row+1][draw_col] = token.getBottom();
                    }
                    if (token.getTop() != 'W'){
                        drawArray[draw_row-1][draw_col] = token.getTop();
                    }
                    if (token.getLeft() != 'W'){
                        drawArray[draw_row][draw_col-1] = token.getLeft();
                    }
                    if(token.getRight() != 'W'){
                        drawArray[draw_row][draw_col+1] = token.getLeft();
                    }
                }
                if (board[row][col] instanceof Player){
                    Player p = (Player) board[row][col];
                    int draw_row = row*3 +1;
                    int draw_col = col*3 +1;
                    drawArray[draw_row][draw_col] = p.getPlayerID();
                }
                // Prints the square for the new token creation location
                if (((row == 2 && col == 2) || (row == 7 && col == 7)) && (!(board[row][col] instanceof Board))) {
                    int draw_row = row*3 +1;
                    int draw_col = col*3 +1;
                    drawArray[draw_row][draw_col - 1] = '[';
                    drawArray[draw_row][draw_col + 1] = ']';
                }
            }
        }
    }

}
