import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Reversi {
    public static void main(String[] args) {
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase().toCharArray();
        Scanner in = new Scanner(System.in);
        //Enter the number so you can choose the dimension of Reversi you want
        //you can play both 4x4reversi and 8x8 reversi
        System.out.println("Reversi by CSC242: Intro to AI");
        System.out.println("Choose your game:");
        System.out.println("1. Small 4x4 Reversi");
        System.out.println("2. Standard 8x8 Reversi");
        System.out.println("Your choice:");
        int game_type = in.nextInt();
        //Enter the number, you can choose to play both minimax ai and heuristic minimax alpha beta pruning ai with fixed depth
        System.out.println("Choose your opponent:");
        System.out.println("1. An agent that uses MINIMAX");
        System.out.println("2. An agent that uses H-MINIMAX with a fixed depth cutoff and a-b pruning");
        System.out.println("Your choice:");
        int opponent = in.nextInt();
        //Initialize the depth of heuristic minimax, and you can enter the depth cutoff you want to use
        int depth = 0;
        if(opponent == 2) {
            System.out.println("Please enter search limit you want");
            depth = in.nextInt();
        }
        //you can choose to play first or second, and the ai will adjust accordingly.
        System.out.println("Do you want to play DARK (x) or LIGHT (o)?");
        String sym = in.next();
        String pcSym;
        int player;;
        if (sym.equals("x"))
        {
            player = 1;
            pcSym="o";
        }
        else
        {
            player = -1;
            pcSym="x";
        }
        //get the dimension of the board game
        int dim;
        if(game_type == 1) {
            dim = 4;
        }
        else{
            dim = 8;
        }
        var G = new Game(dim,dim);


        int turn =1;
        System.out.println(G.DrawBoard(G.Cboard,null));
        //if it is not the terminal/goal state
        while (!G.isGoal(G.Cboard, player)){
            if(turn==player){
                //get the valid moves of the user
                var locs = G.GetSuccessors(G.Cboard,player);
                if(locs.size()>0) {
                    List<String> valid_moves = new ArrayList<String>();
                    for (var e : locs) {
                        valid_moves.add(Character.toString(alphabet[e[1]]) + Integer.toString(e[0] + 1));
                    }
                    //get time
                    long finish = System.currentTimeMillis();
                    boolean isvalid = false;
                    String move = "";
                    long start = System.currentTimeMillis();
                    //check and print user's turn, and check whether it is valid move
                    while (isvalid == false) {
                        if(player == 1) {
                            System.out.println("Your move (Dark/x): ");
                        }
                        else if(player == -1) {
                            System.out.println("Your move (Light/o): ");
                        }
                        System.out.println(valid_moves);
                        move = in.next();
                        if (valid_moves.contains(move)) {
                            isvalid = true;
                        }
                        else {
                            System.out.println("Invalid move!");
                            System.out.println("Valid moves are: " + valid_moves.toString());
                        }
                    }
                    //get and print elapse time
                    long timeElapsed = finish - start;
                    System.out.println(String.format("Elapsed time: %.3f secs", timeElapsed / 1000.0));
                    System.out.println(String.format("%s:%s", sym, move));
                    int x = Integer.parseInt(Character.toString(move.charAt(1))) - 1;
                    int y = 0;
                    for (int i = 0; i < alphabet.length; i++) {
                        if (alphabet[i] == move.charAt(0)) {
                            y = i;
                            break;
                        }
                    }

                    G.Cboard = G.Move(G.Cboard, player, x, y);
                }
            }
            else{
                //get the valid moves of the ai
                var locs = G.GetSuccessors(G.Cboard,player*-1);
                if(locs.size()>0) {
                    List<String> valid_moves = new ArrayList<String>();
                    for (var e : locs) {
                        valid_moves.add(Character.toString(alphabet[e[1]]) + Integer.toString(e[0] + 1));
                    }
                    long start = System.currentTimeMillis();
                    System.out.println("I¡¯m picking an optimal move...");

                    int[] bestMove;
                    //according to the choose of opponent, use interface and get either minimax or heuristic minimax
                    if(opponent == 1) {
                        SearchAlgorithm minimax = new mnm();
                        bestMove = minimax.GetOptimalMove(G.Cboard,player*-1,G);
                    }
                    else {
                        h_SearchAlgorithm minimax = new habmnm();
                        bestMove = minimax.GetOptimalMove(G.Cboard,player*-1,G,depth);
                    }
                    //print elapse time
                    String move = Character.toString(alphabet[bestMove[1]]) + Integer.toString(bestMove[0] + 1);
                    long finish = System.currentTimeMillis();
                    long timeElapsed = finish - start;
                    System.out.println(String.format("Elapsed time: %.3f secs", timeElapsed / 1000.0));
                    System.out.println(String.format("%s:%s", pcSym, move));
                    int x = bestMove[0];
                    int y = bestMove[1];

                    G.Cboard = G.Move(G.Cboard, player * -1, x, y);
                }
            }
            System.out.println(G.DrawBoard(G.Cboard, null));
            //if the current player can still move, but the next player does not have legal action, then
            //this turn is still for the player, and the next player should pass
            if(!G.GetSuccessors(G.Cboard, turn*-1).isEmpty()) {
                turn *= -1;
            }
            // else if (!G.GetSuccessors(G.Cboard, player).isEmpty()) {
            //turn *= -1;
            //}
        }
        //get the score and print the result
        int scorePlayer = G.GetScore(G.Cboard, player);
        int scoreAi = G.GetScore(G.Cboard, player*-1);
        if (scoreAi > scorePlayer ) {
            System.out.println("The winner is Ai with score: " + scorePlayer + " "+ " "+scoreAi);
        }
        else  if (scoreAi < scorePlayer ){
            System.out.println("The winner is You with score: " + scorePlayer + " "+ " "+scoreAi);
        }
        else{
            System.out.println("Tie with score: " + scorePlayer + " "+ " "+scoreAi);
        }
    }
}