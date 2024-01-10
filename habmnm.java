import java.util.ArrayList;
import java.util.List;

public class habmnm implements h_SearchAlgorithm{
    double pos_infinity = 1000000000;//beta
    double neg_infinity = -1000000000;//alpha

    ////Pick the most optimal move which has maximum value from all legal actions
    public int[] GetOptimalMove(int[][] board, int player, Game G, int depth){
        List<int[]> action = new ArrayList<int[]>();
        action = G.GetSuccessors(board, player);
        int[] x = action.get(0);
        double max = min_value(G,G.Move(board,player,x[0],x[1]), player*-1, depth, neg_infinity, pos_infinity);
        for(int i = 0; i < action.size();i++){
            if(max < min_value(G, G.Move(board,player,action.get(i)[0], action.get(i)[1]),player*-1, depth, neg_infinity, pos_infinity))
            {
                max = min_value(G, G.Move(board,player,action.get(i)[0], action.get(i)[1]),player*-1, depth, neg_infinity, pos_infinity);
                x = action.get(i);
            }
        }
        return x;
    }

    //Return the maximum value from all min_Value of legal action
    public double max_value(Game G, int[][] board, int player, int depth, double a, double b){
        List<int[]> action = new ArrayList<int[]>();
        if(G.isGoal(board, player) || depth ==0){
            return G.heuristic(board, player*-1);
        }
        action = G.GetSuccessors(board, player*-1);
        double v = -10000000;
        double z;
        for(int i = 0; i<action.size();i++){
            z = min_value(G, G.Move(board,player*-1,action.get(i)[0], action.get(i)[1]),player, depth-1, a, b);
            if (z>=v){
                v=z;
            }
            if (v>=b){
                return v;
            }
            if (v>=a){
                a = v;
            }
            }
        
        return v;
    }

    //Return the minimum value from all max_Value of legal actions
    public double min_value(Game G, int[][] board, int player, int depth, double a, double b){
        List<int[]> action = new ArrayList<int[]>();
        if(G.isGoal(board, player*-1) || depth ==0){
            return G.heuristic(board, player*-1);
        }
        action = G.GetSuccessors(board, player);
        double v = 10000000;
        double z;
        for(int i = 0; i < action.size(); i++){
            z = max_value(G, G.Move(board,player*-1,action.get(i)[0], action.get(i)[1]),player, depth-1, a, b);
            if(z <= v){
                v = z;
            }
            if(v <=a){
                return v;
            }
            if (v<=b){
                b=v;
            }
        }

        return v;
    }
}