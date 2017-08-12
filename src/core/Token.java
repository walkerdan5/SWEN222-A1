package core;

/**
 * Created by danielwalker on 2/08/17.
 */
public class Token implements BoardGame{
    private char top, left, right, bottom = 'W';
    char tokenID;

    public Player whosToken;

    public Token(char id, Player player) {
        tokenID = id;
        whosToken = player;
    }

    public void drawToken(){
        System.out.println(" " + top + "\n" + left + tokenID + right+ "\n" + " " + right);
    }

    public char getLeft(){
        return left;
    }

    public char getRight() {
        return right;
    }

    public char getTop() {
        return top;
    }

    public char getBottom() {
        return bottom;
    }

    public void setLeft(char left){
        this.left = left;
    }

    public void setRight(char right){
        this.right = right;
    }

    public void setTop(char top){
        this.top = top;
    }

    public void setBottom(char bottom){
        this.bottom = bottom;
    }



}
