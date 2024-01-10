
public interface State {
	public boolean isGoal(int[][] board, int player);
	public double utility(int[][] board, int player) ;
	public double heuristic(int[][] board, int player);
}
