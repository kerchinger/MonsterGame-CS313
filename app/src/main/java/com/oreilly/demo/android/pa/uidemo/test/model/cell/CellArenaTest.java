package com.oreilly.demo.android.pa.uidemo.test.model.cell;

import com.oreilly.demo.android.pa.uidemo.model.cell.Cell;
import com.oreilly.demo.android.pa.uidemo.model.cell.CellArena;
import com.oreilly.demo.android.pa.uidemo.model.cell.MonsterGameCell;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Grazyna on 12/15/2016.
 */

public class CellArenaTest {
    @Test
    public void init(){
        Cell[][] grid = new MonsterGameCell[4][4];
        for(int i = 0; i < grid.length; ++i){
            for(int j = 0; j < grid[i].length; ++j){
                grid[i][j] = new MonsterGameCell(1, null);
            }
        }
        CellArena cellArena = new CellArena(grid);

        assertEquals(4, cellArena.getGrid().length);
    }
}
