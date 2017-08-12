package core;

import core.Board;
import core.Player;
import core.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by danielwalker on 8/08/17.
 */
public class SwordsAndShields {

    private Player player1, player2, currentPlayer;
    private Board board;


    public SwordsAndShields(){
        player1 = new Player(1);
        player2 = new Player(2);
        currentPlayer = player1;
        board = new Board(player1, player2);
    }


    /**
     * This method can be used for testing purposes
     * I used it in the earlier stages of the program to make sure the tokens were working correctly
     * @param currentPlayer
     */
    public void printPlayerTokenIDs(Player currentPlayer) {
        int count = 1; //for keeping track of number of tokens

        if (currentPlayer.getPlayerID() == '1') {
            System.out.println("----------------" +
                    "\nPlayer 1's Tokens: ");
            for (Token token : currentPlayer.getThisPlayersTokens()) {
                System.out.println(count++ + ". " + token.toString());
            }
        }
        else {
            System.out.println();
            System.out.println("----------------" +
                    "\nPlayer 2's Tokens: ");
            for (Token token : currentPlayer.getThisPlayersTokens()) {
                System.out.println(count++ + ". " + token.toString());
            }
        }
    }


    public void printPlayerTokens(Player currentPlayer) {

        if (currentPlayer.getPlayerID() == '1') {
            System.out.println("----------------" +
                    "\nPlayer 1's Tokens: ");


            for (Token token : currentPlayer.getThisPlayersTokens()) {
                char ID = currentPlayer.getTokenID();
                token.drawToken();
            }
        }
        else {
            System.out.println();
            System.out.println("----------------" +
                    "\nPlayer 2's Tokens: ");

            for (Token token : currentPlayer.getThisPlayersTokens()) {
                char ID = currentPlayer.getTokenID();
                token.drawToken();
            }
        }
    }


    public void playGame() {
        System.out.println("Game has started");
        board.showBoard();
        InputStreamReader in = new InputStreamReader(System.in);

        while (true) { //while there is user input
            printPlayerTokens(currentPlayer);
            System.out.println("\n==>Player: " + currentPlayer.getPlayerID() + " Turn!");
            try{
                playPass(in);
                if("pass".equals(in)){
                    // No creation
                }
                else if("play".equals(in)){
                    // play by adding a piece to the board

                }
                else if("spotTaken".equals(in)){
                    System.out.println("Cant place token, creation spot currently has a token");

                }

            }
            catch (IOException e){
                e.printStackTrace();
            }
            board.showBoard();
            System.out.println("Next players turn");
            if(currentPlayer.equals(player1)){
                currentPlayer = player2;
            } else{
                currentPlayer = player1;
            }
            System.out.println("Current player is now: " + currentPlayer.getPlayerID());

        }
    }


    private String playPass(InputStreamReader isr) throws IOException {

        isr = new InputStreamReader(System.in);  //This takes in user input.
        BufferedReader br = new BufferedReader(isr);
        String input = "";
        System.out.println("==> play, pass or move?");
        input = br.readLine();
        if (input.equals("Pass") || input.equals("pass") || input.equals("PASS")){
            return "pass";
        }
        if(!board.canWeAddAPiece (currentPlayer)) {
                System.out.println("spotTaken");
                moveTokenOut(isr);

        }

        else if (input.equals("Play") || input.equals("play") || input.equals("PLAY")) {
            if (board.canWeAddAPiece(currentPlayer)) {
                create(isr);
            } else
                System.out.println("creation spot occupied");

            return "play";
        }
        else {
            throw new IOException("Invalid input");
        }
        return "invalidInput";
    }


    private Token moveTokenOut(InputStreamReader isr) throws IOException{
        isr = new InputStreamReader(System.in);  //This takes in user input.
        BufferedReader br = new BufferedReader(isr);
        String input = "";
        System.out.println("==> Which token would you like to move?");
        input = br.readLine();
        char chosenTokenID = input.charAt(0);
        Token chooseToken = currentPlayer.chooseToken(chosenTokenID);
        System.out.println("==> Which direction would you like to move the token?");
        input = br.readLine();
        String chosenDirection = input;
        mover(chosenTokenID, chosenDirection);
        return chooseToken;
    }


    private Token create(InputStreamReader isr) throws IOException {
        isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String input = "";
        System.out.println("==> Which token would you like to play?");
        input = br.readLine();
        char chosenTokenID = input.charAt(0);
        Token chooseToken = currentPlayer.chooseToken(chosenTokenID);
        board.PlaceToken(currentPlayer, chooseToken);
        currentPlayer.removePlayerToken(chooseToken);
        board.showBoard();
        System.out.println("==> Would you like to move the token? (yes/no)");
        input = br.readLine();
        if (input.equals("Yes") || input.equals("yes") || input.equals("YES")) {
            System.out.println("==> Which direction would you like to move the token?");
            input = br.readLine();
            String chosenDirection = input;
            mover(chosenTokenID, chosenDirection);
        }
        return chooseToken;

    }


    private void mover(char tokenID,String option) {
        Token selectedToken = board.getToken(tokenID);


            if (option.equals("up")) {
                board.pushTokenUp(selectedToken, tokenID);
            } else if (option.equals("down")) {
                board.pushTokenDown(selectedToken, tokenID);
            } else if (option.equals("left")) {
                board.pushTokenLeft(selectedToken, tokenID);
            } else if (option.equals("right")) {
                board.pushTokenRight(selectedToken, tokenID);
            } else {
                System.out.println("Not valid input, please type 'up', 'left', 'down', 'right'");
            }



    }
}
