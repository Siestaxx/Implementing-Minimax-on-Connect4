/**
 * The interface Shape specifies the behaviors
 *   of this implementations subclasses.
 */
public interface h_SearchAlgorithm {  // Use keyword "interface" instead of "class"
    // List of public abstract methods to be implemented by its subclasses
    // All methods in interface are "public abstract".
    // "protected", "private" and "package" methods are NOT allowed.
    int[] GetOptimalMove(int[][] board, int player, Game G,int depth);
}