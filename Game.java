import java.util.ArrayList;
import java.util.List;

public class Game implements State, Action, Result{
    //get the rows and columns and board
    public int row;
    public int col;
    public  int[][] Cboard = null;
    public Game(int r, int col)
    {
        this.row = r;
        this.col = col;
        Cboard = GetInitialPosition();
    }
    //get the initial four pieces on the board
    public int[][] GetInitialPosition()
    {
        int[][] board = new int[this.row][this.col];
        board[this.row/2-1][this.col/2-1] = -1; //white
        board[this.row/2][this.col/2] = -1; //white
        board[this.row/2-1][this.col/2] = 1; //black
        board[this.row/2][this.col/2-1] = 1; //black
        return  board;
    }
    //generate successors of legal actions of each state, use the below isvalid to check whether it is legal action
    public List<int[]> GetSuccessors(int[][] board, int player)
    {
        List<int[]> successors = new ArrayList<int[]>();
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if(board[i][j] == 0) {
                    boolean isvalid = isValidH(board,player,i,j);
                    if(isvalid == false) {
                        isvalid = isValidV(board,player,i,j);
                    }
                    if(isvalid == false) {
                        isvalid = isValidD(board,player,i,j);
                    }
                    if(isvalid) {
                        int[] pair = new int[2];
                        pair[0] = i;
                        pair[1] = j;
                        successors.add(pair);
                    }
                }
            }
        }
        return  successors;
    }
    //get the copy of the board, can be used to get board after the move of pieces
    public int[][] CopyBoard(int[][] board)
    {
        int[][] board_copy = new int[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                board_copy[i][j] = board[i][j];
            }
        }
        return  board_copy;
    }
    //Make the moves below in to one method, imtegrate together so it flip together too, and return the copied board
    //copy board method is above
    public int[][] Move(int[][] board, int player,int x, int y)
    {
        var board_copy = CopyBoard(board);
        MoveH(board_copy,board,player,x,y);
        MoveV(board_copy,board,player,x,y);
        MoveD(board_copy,board,player,x,y);
        board_copy[x][y] = player   ;
        return  board_copy;
    }
    //Move horizontally and flip the pieces
    public void MoveH(int[][] board,int[][] refboard, int player,int x, int y)
    {
        var cont = true;
        var count =0;
        List<Integer> updatedCells = new ArrayList<Integer>();
        for (int i = y-1; i >=0; i--) {
            if(refboard[x][i] == player*-1) {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[x][i] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    for (var e:
                         updatedCells) {
                        board[x][e] = player;
                    }
                }
                break;
            }
        }
        cont = true;
        count =0;
        updatedCells = new ArrayList<Integer>();
        for (int i = y+1; i <this.col; i++) {
            if(refboard[x][i] == player*-1) {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[x][i] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    for (var e:
                            updatedCells) {
                        board[x][e] = player;
                    }
                }
                break;
            }
        }
    }
    //check whether the horizontal move of each player is valid according to the horizontal rule
    public boolean isValidH(int[][] board, int player,int x, int y)
    {
        var isvalid = false;
        var cont = true;
        var count =0;
        for (int i = y-1; i >=0; i--) {
            if(board[x][i] == player*-1) {
                count++;
            }
            else if(board[x][i] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    isvalid = true;
                }
                break;
            }
        }
        if(isvalid) {
            return isvalid;
        }
        cont = true;
        count =0;
        for (int i = y+1; i <this.col; i++) {
            if(board[x][i] == player*-1) {
                count++;
            }
            else if(board[x][i] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    isvalid = true;
                }
                break;
            }
        }
        return  isvalid;
    }
    //Move vertically and flip the pieces
    public void MoveV(int[][] board,int[][] refboard, int player,int x, int y)
    {
        var cont = true;
        var count =0;
        List<Integer> updatedCells = new ArrayList<Integer>();
        for (int i = x-1; i >=0; i--) {
            if(refboard[i][y] == player*-1) {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[i][y] == player) {
                cont = false;
            }
            else {
                break;
            }

            if (cont==false) {
                if (count>0) {
                    for (var e:
                            updatedCells) {
                        board[e][y] = player;
                    }
                }
                break;
            }
        }
        cont = true;
        count =0;
        updatedCells = new ArrayList<Integer>();
        for (int i = x+1; i <this.row; i++) {
            if(refboard[i][y] == player*-1) {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[i][y] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    for (var e:
                            updatedCells) {
                        board[e][y] = player;
                    }
                }
                break;
            }
        }
    }
    //check whether the vertical move of each player is valid according to the vertical rule
    public boolean isValidV(int[][] board, int player,int x, int y)
    {
        var isvalid = false;
        var cont = true;
        var count =0;
        for (int i = x-1; i >=0; i--) {
            if(board[i][y] == player*-1) {
                count++;
            }
            else if(board[i][y] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    isvalid = true;
                }
                break;
            }
        }
        if(isvalid) {
            return isvalid;
        }
        cont = true;
        count =0;
        for (int i = x+1; i <this.row; i++) {
            if(board[i][y]== player*-1) {
                count++;
            }
            else if(board[i][y] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    isvalid = true;
                }
                break;
            }
        }
        return  isvalid;
    }
    //Move diagonally and flip the pieces
    public void MoveD(int[][] board,int[][] refboard, int player,int x, int y)
    {
        var cont = true;
        var count =0;
        var max = this.col;
        if(max<this.row) {
            max= this.row;
        }
        List<Integer> updatedCells = new ArrayList<Integer>();
        for (int i = 1; i <= max; i++) {
            if (x-i<0 || y-i<0) {
                break;
            }
            if(refboard[x-i][y-i] == player*-1) {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[x-i][y-i] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    for (var e:
                            updatedCells) {
                        board[x-e][y-e] = player;
                    }
                }
            }
        }
        cont = true;
        count =0;
        updatedCells = new ArrayList<Integer>();
        for (int i = 1; i <= max; i++) {
            if (x+i>=this.row || y+i>=this.col) {
                break;
            }
            if(refboard[x+i][y+i]== player*-1) {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[x+i][y+i] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    for (var e:
                            updatedCells) {
                        board[x+e][y+e] = player;
                    }
                }
                break;
            }
        }
        cont = true;
        count =0;
        updatedCells = new ArrayList<Integer>();
        for (int i = 1; i <= max; i++) {
            if (x-i<0 || y+i>=this.col) {
                break;
            }
            if(refboard[x-i][y+i]== player*-1) {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[x-i][y+i] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    for (var e:
                            updatedCells) {
                        board[x-e][y+e] = player;
                    }
                }
                break;
            }
        }
        cont = true;
        count =0;
        updatedCells = new ArrayList<Integer>();
        for (int i = 1; i <= max; i++) {
            if (x+i>=this.row || y-i<0) {
                break;
            }
            if(refboard[x+i][y-i]== player*-1) {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[x+i][y-i] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    for (var e:
                            updatedCells) {
                        board[x+e][y-e] = player;
                    }
                }
                break;
            }
        }
    }
    //check whether the diagnol move of each player is valid according to the diagonal rule
    public boolean isValidD(int[][] board, int player,int x, int y)
    {
        var isvalid = false;
        var cont = true;
        var count =0;
        var max = this.col;
        if(max<this.row) {
            max= this.row;
        }
        for (int i = 1; i <= max; i++) {
            if (x-i<0 || y-i<0) {
                break;
            }
            if(board[x-i][y-i] == player*-1) {
                count++;
            }
            else if(board[x-i][y-i] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    isvalid = true;
                }
                break;
            }
        }
        if(isvalid) {
            return isvalid;
        }
        cont = true;
        count =0;
        for (int i = 1; i <= max; i++) {
            if (x+i>this.row-1 || y-i<0) {
                break;
            }
            if(board[x+i][y-i]== player*-1) {
                count++;
            }
            else if(board[x+i][y-i] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    isvalid = true;
                }
                break;
            }
        }
        if(isvalid) {
            return isvalid;
        }
        cont = true;
        count =0;
        for (int i = 1; i <= max; i++) {
            if (x-i<0 || y+i>this.col-1) {
                break;
            }
            if(board[x-i][y+i]== player*-1) {
                count++;
            }
            else if(board[x-i][y+i] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    isvalid = true;
                }
                break;
            }
        }
        if(isvalid) {
            return isvalid;
        }
        cont = true;
        count =0;
        for (int i = 1; i <= max; i++) {
            if (x+i>this.row-1 || y+i>this.col-1) {
                break;
            }
            if(board[x+i][y+i]== player*-1) {
                count++;
            }
            else if(board[x+i][y+i] == player) {
                cont = false;
            }
            else {
                break;
            }
            if (cont==false) {
                if (count>0) {
                    isvalid = true;
                }
                break;
            }
        }
        return  isvalid;
    }
    //print the initial board and where the pieces are.
    //print the copied of the board and the pieces updated(after flip)
    public String DrawBoard(int[][] board, List<int[]> successors) {
        var board_copy = CopyBoard(board);
        if (successors != null) {
            for (var s:
                 successors) {
                board_copy[s[0]][s[1]] = 2;
            }
        }
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase().toCharArray();
        String graph = "  ";
        for (int i = 0; i < this.col; i++) {
            graph += alphabet[i] + " ";
        }
        graph +="\n";
        for (int i = 0; i < this.row; i++) {
            graph += (i+1) + " ";
            for (int j = 0; j < this.col; j++) {
                    var sym = " ";
                    if(board_copy[i][j]==-1) {
                        sym = "o";
                    }
                    else if (board_copy[i][j]==1) {
                        sym = "x";
                    }
                    else if (board_copy[i][j]==2) {
                        sym = "s";
                    }
                    graph +=  sym+ " ";
            }
            graph += (i+1) + " \n";
        }
        graph +="  ";
        for (int i = 0; i < this.col; i++) {
            graph += alphabet[i] + " ";
        }
        graph +="\n";
        return  graph;
    }
    //Generate the final score of both side
    public int GetScore(int[][] board ,int player)
    {
        int total = 0;
        for (int i = 0; i < this.row; i++)
            for (int j = 0; j < this.col; j++)
            {
                if (board[i][j] == player)
                    total++;
            }
        return total;
    }
    //Utility function of the AI and player
    public double utility(int[][] board, int player){
        int util = 0;
        if(isGoal(board, player)==true){
            //Utility of AI is positive
            if(GetScore(board, player) < GetScore(board,player*-1)){
                util = 64;
            }//Utility of user is negative
            else if(GetScore(board,player) > GetScore(board, player * -1)){
                util = -64;
            }
            else{//zero sum game
                util = 0;
            }
        }
        return util;
    }
    //check whether the board reach terminal state
    public boolean isGoal(int[][]board, int player){
        if(GetSuccessors(board, player).isEmpty() && GetSuccessors(board, player*-1).isEmpty()){
            return true;
        }    
        else{
            return false;
        }
    }
    //Heuristic Function score base on checking number of corner occupied and number of next to corner occupied
    //then give each score a weight
    public double heuristic(int[][] board, int player)
    {
        //corner is a good thing, so we weight this as positive
        double score =0;
        int size = col - 1;
        int playertilec=0;
        int oppotilec=0;
        int cornercheck =0;
        if (board[0][0] == player){
            playertilec++;}
        else if (board[0][0] == player*-1){
            oppotilec++;}
        if (board[0][size] == player){
            playertilec++;}
        else if (board[0][size] == player*-1){
            oppotilec++;}
        if (board[size][size] == player){
            playertilec++;}
        else if (board[size][size] == player*-1){
            oppotilec++;}
        if (board[size][0] == player){
            playertilec++;}
        else if (board[size][0] == player*-1){
            oppotilec++;}
        cornercheck = 25* (playertilec - oppotilec);
        //next to corner is not a good thing, so we weight this as negative
        int playertilenc=0;
        int oppotilenc=0;
        double ncornercheck =0;
        if(board[0][0] == '_') {
            if (board[0][1] == player){
                playertilenc++;}
            else if (board[0][1] == player*-1){
                oppotilenc++;}
            if (board[1][1] == player){
                playertilenc++;}
            else if (board[1][1] == player*-1){
                oppotilenc++;}
            if (board[1][0] == player){
                playertilenc++;}
            else if (board[1][0] == player*-1){
                oppotilenc++;}
        }
        if(board[size][size] == '_') {
            if (board[size-1][size] == player){
                playertilenc++;}
            else if (board[size-1][size] == player*-1){
                oppotilenc++;}
            if (board[size-1][size-1] == player){
                playertilenc++;}
            else if (board[size-1][size-1] == player*-1){
                oppotilenc++;}
            if (board[size][size-1] == player){
                playertilenc++;}
            else if (board[size][size-1] == player*-1){
                oppotilenc++;}
        }
        if(board[0][size] == '_') {
            if (board[0][size-1] == player){
                playertilenc++;}
            else if (board[0][size-1] == player*-1){
                oppotilenc++;}
            if (board[1][size-1] == player){
                playertilenc++;}
            else if (board[1][size-1] == player*-1){
                oppotilenc++;}
            if (board[1][size] == player){
                playertilenc++;}
            else if (board[1][size] == player*-1){
                oppotilenc++;}
        }
        if(board[size][0] == '_') {
            if (board[size-1][0] == player){
                playertilenc++;}
            else if (board[size-1][0] == player*-1){
                oppotilenc++;}
            if (board[size-1][1] == player){
                playertilenc++;}
            else if (board[size-1][1] == player*-1){
                oppotilenc++;}
            if (board[size][1] == player){
                playertilenc++;}
            else if (board[size][1] == player*-1){
                oppotilenc++;}
        }
        ncornercheck = -12.5*(playertilenc-oppotilenc);
        score = (801.724 * cornercheck) + (382.026 * ncornercheck);
        return score;
    }




}
