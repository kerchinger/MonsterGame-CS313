package com.oreilly.demo.android.pa.uidemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.oreilly.demo.android.pa.uidemo.R;
import com.oreilly.demo.android.pa.uidemo.model.MonsterGameModel;
import com.oreilly.demo.android.pa.uidemo.model.cell.Cell;
import com.oreilly.demo.android.pa.uidemo.model.common.Constants;
import com.oreilly.demo.android.pa.uidemo.model.monster.Monster;

/**
 * Created by nickpredey on 12/15/16.
 */

public class DefaultMonsterGameView extends View {
    Context context;
    MonsterGameModel model;
    Paint paint;
    Bitmap bitmap;

    int cellSize;
    int displayHeight;
    int displayWidth;
    int gridWidth;
    int gridHeight;
    int pictureNumber = 0; //This will access the pictures in the file.


    //TODO: TEST THIS
    //This is where the screen is able to be rotated.
    public DefaultMonsterGameView(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        displayHeight = context.getResources().getDisplayMetrics().heightPixels;
        displayWidth = context.getResources().getDisplayMetrics().widthPixels;
        gridWidth = displayWidth / Constants.CELL_SIZE;
        gridHeight = (displayHeight - Constants.VIEW_HEIGHT_MINUS) / Constants.CELL_SIZE;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Cell[][] grid = model.getArena().getGrid();
        for (int i = 0; i < gridWidth; i += 1) {
            for (int j = 0; j < gridHeight; j += 1) {
                Cell cell = grid[i][j];
                if(cell.getOccupants().size() > 0) {
                    Log.i(Constants.TAG, "Pseudomonster count: " + cell.getOccupants().size());

                    if (cell.getOccupants().size() <= 0) return;

                    Monster m = (Monster)cell.getOccupants().get(0);
                    if (m.isProtected()) {
                        pictureNumber++;
                        if(pictureNumber == 1) {
                            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red);
                        }
                        else if(pictureNumber == 2){
                            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue);
                        }
                        else if(pictureNumber == 3){
                            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pink);
                            pictureNumber=0;
                        }
                    } else {
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unprotected);
                    }
                    canvas.drawBitmap(bitmap, i * cellSize, j * cellSize, paint);
                }
                /**Making grid visible here using drawRect()*/
                canvas.drawRect(i * cellSize,
                        j * cellSize,
                        i * cellSize + cellSize,
                        j * cellSize + cellSize,
                        paint);
            }
        }
        paint.setTextSize(25);
        canvas.drawText ("Ghosts eaten: " + model.getScore(),
                displayWidth/2-60, displayHeight-160, paint);
    }

    public void setModel(MonsterGameModel model) { this.model = model; }

    /**
     * Initializes a grid.
     * @param cells
     * @param cellSize
     */
    public void init(Cell[][] cells, int cellSize) {
        gridWidth = cells.length;
        gridHeight = cells[0].length;
        this.cellSize = cellSize;
    }

}
