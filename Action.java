import java.util.List;

public interface Action {
	public List<int[]> GetSuccessors(int[][] board, int player);
}
