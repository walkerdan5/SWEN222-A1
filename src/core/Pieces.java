package core;

/**
 * Created by danielwalker on 27/07/17.
 */
public class Pieces  {

    private String top;
    private String bottom;
    private String left;
    private String right;
    private String sw;
    private String sh;


   /**
    * Construct a new piece object
    */
    public Pieces(String top, String left, String right, String bottom){
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;

    }


}

