package ai;

public class Node {
    public CBoard board;
    public int evalFunc;
    public int minimaxMove;
    public int nodeDepth;
    
    public Node(){
    	board = new CBoard(); 	
    }
}
