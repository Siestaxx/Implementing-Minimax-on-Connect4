import java.util.ArrayList;
import java.util.List;

public class mnm implements SearchAlgorithm{
	
	 //Pick the most optimal move which has maximum value of all min_Value of legal actions
    public int[] GetOptimalMove(int[][] board, int player, Game G){
        List<int[]> action = new ArrayList<int[]>();
        action = G.GetSuccessors(board, player);//Get the legal actions
        int[] x = action.get(0);
        double max = min_value(G,G.Move(board,player,x[0],x[1]), player*-1);
        for(int i = 0; i < action.size();i++){
            if(max <= min_value(G, G.Move(board,player,action.get(i)[0], action.get(i)[1]),player*-1)){
                max = min_value(G, G.Move(board,player,action.get(i)[0], action.get(i)[1]),player*-1);
                x = action.get(i);
            }
        }//find the maximum value 
        return x;
    }

    //Return the maximum value of all min_Value of legal actions
    public double max_value(Game G, int[][] board, int player){

        List<int[]> action = new ArrayList<int[]>();
        if(G.isGoal(board, player)){
            return G.utility(board, player);
        }//if the state is terminal, return the utility value of state
        action = G.GetSuccessors(board, player*-1);
        double v = -10000000;
        double z;
        for(int i = 0; i<action.size();i++){
            z = min_value(G, G.Move(board,player*-1,action.get(i)[0], action.get(i)[1]),player);
            if (z>=v){
                v=z;
            }
        }

        return v;
    }

  //Return the minimum value of all max_Value of legal actions
    public double min_value(Game G, int[][] board, int player){

        List<int[]> action = new ArrayList<int[]>();
        if(G.isGoal(board, player*-1)){
            return G.utility(board, player*-1);
        }//if the state is terminal, return the utility value of state
        action = G.GetSuccessors(board, player);
        double v = 10000000;
        double z;
        for(int i = 0; i < action.size(); i++){
            z = max_value(G, G.Move(board,player*-1,action.get(i)[0], action.get(i)[1]),player);
            if(z <= v){
                v = z;
            }
        }

        return v;
    }
}