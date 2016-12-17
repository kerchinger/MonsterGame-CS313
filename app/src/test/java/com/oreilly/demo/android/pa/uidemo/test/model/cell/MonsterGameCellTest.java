package com.oreilly.demo.android.pa.uidemo.test.model.cell;

import com.oreilly.demo.android.pa.uidemo.model.cell.Cell;
import com.oreilly.demo.android.pa.uidemo.model.cell.MonsterGameCell;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Grazyna on 12/15/2016.
 */

public class MonsterGameCellTest {
    @Test
    public void init(){
        Cell cell = new MonsterGameCell(1, null);

        assertEquals(0, cell.getOccupants().size());
    }
}
