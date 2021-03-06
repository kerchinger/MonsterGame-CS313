package com.oreilly.demo.android.pa.uidemo.model.cell;

import com.oreilly.demo.android.pa.uidemo.model.monster.PseudoMonster;
/**
 * Created by nickpredey on 12/15/16.
 */

/**
 * This class represents an arena of bloody death consisting of a rectangular grid
 * of cells.
 * This is a lot like the old class "Board", but instead it doesn't just use a 2D array, it actually
 * implements a Cell class.
 * @see Arena
 * @see Cell
 */
public class CellArena implements Arena {

    private Cell[][] grid;

    /**
     * This constructor builds a CellWorld from a rectangular
     * grid of cells.  It automatically makes horizontally, vertically,
     * and diagonally adjacent cells neighbors of each other.
     */
    public CellArena(final Cell[][] grid) {

        this.grid = grid;

        int rows = grid.length;
        int cols = grid[0].length;

        for (int i = 0; i < rows; i ++) {
            for (int j = 0; j < cols; j ++) {
                if (i > 0) {
                    if (j > 0) grid[i][j].addNeighbor(grid[i-1][j-1]);
                    grid[i][j].addNeighbor(grid[i-1][j]);
                    if (j < cols - 1) grid[i][j].addNeighbor(grid[i-1][j+1]);
                }
                if (j > 0) grid[i][j].addNeighbor(grid[i][j-1]);
                if (j < cols - 1) grid[i][j].addNeighbor(grid[i][j+1]);
                if (i < rows - 1) {
                    if (j > 0) grid[i][j].addNeighbor(grid[i+1][j-1]);
                    grid[i][j].addNeighbor(grid[i+1][j]);
                    if (j < cols - 1) grid[i][j].addNeighbor(grid[i+1][j+1]);
                }
            }
        }
    }

    /**
     * This method adds an pseudoMonster to the cell at the given position.
     * It is the responsibility of the caller of this method to
     * make sure that there is space for the pseudoMonster at the given
     * position.
     * @throws InternalError If the thread invoking this method
     * is interrupted
     */
    public void addActor(PseudoMonster pseudoMonster, int xpos, int ypos) {
        try {
            grid[ypos][xpos].enter(pseudoMonster);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }
}
