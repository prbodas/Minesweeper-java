import java.util.*;
import java.awt.event.*;
/**
 * REFLECTION
 * Prachi Bodas Period 3 apcs
 */
public class MinesweeperSolver
{
    static Random rand = new Random();
    public static void solveGUI ()
    {
        int r,c;
        int q = percentRevealed(); //DEBUG
        boolean flag = false;
        while (percentRevealed() < 1)
        {
            do
            {
                r = rand.nextInt(MineGUI.ROWS);
                c = rand.nextInt(MineGUI.COLS);
                q = percentRevealed();
                flag = MineGUI.arr[r][c].display == BoardPanel.FLAG;
            }while(MineGUI.arr[r][c].revealed || flag);
            MineGUI.arr[r][c].mouseClicked(new MouseEvent(MineGUI.arr[r][c], MouseEvent.MOUSE_CLICKED,1,0,0,0,1,false, MouseEvent.BUTTON1));
            if (MineGUI.arr[r][c].state == BoardPanel.MINE)
            {
                return;
            }
        }
        while (percentRevealed()<100)
        {
            do
            {
                r = rand.nextInt(MineGUI.ROWS);
                c = rand.nextInt(MineGUI.COLS);
            }while(MineGUI.arr[r][c].revealed || MineGUI.arr[r][c].display == BoardPanel.FLAG);
            MineGUI.arr[r][c].mouseClicked(new MouseEvent(MineGUI.arr[r][c], MouseEvent.MOUSE_CLICKED,1,0,0,0,1,false, MouseEvent.BUTTON1));
            if (MineGUI.arr[r][c].state == BoardPanel.MINE)
            {
                return;
            }
            boolean flagged = false;
            do
            {
                for (int i = 1; i<= 8; i++)
                {
                    flagged = revealNum(i);
                }
            }while (flagged);
        }
    }

    public static boolean revealNum (int y)
    {
        boolean a = false;
        for (int i = 0; i<MineGUI.ROWS; i++)
        {
            for (int j = 0; j<MineGUI.COLS; j++)
            {
                if (MineGUI.arr[i][j].neighbors == y && unrevealedNeighbors(i,j) == y)
                {
                    a = true;
                    flagUnrevealed(i,j);
                }
            }
        }
        return a;
    }
    
    public static void flagUnrevealed (int row, int column)
    {
        //int n = 0;
        if (inBounds(row+1,column) && isUnrevealed(row+1, column))
        {
            MineGUI.arr[row+1][column].mouseClicked(new MouseEvent(MineGUI.arr[row+1][column], MouseEvent.MOUSE_CLICKED,1,0,0,0,1,false, MouseEvent.BUTTON3));
        }
        if (inBounds(row-1,column) && isUnrevealed(row-1, column))
        {
            MineGUI.arr[row-1][column].mouseClicked(new MouseEvent(MineGUI.arr[row-1][column], MouseEvent.MOUSE_CLICKED,1,0,0,0,1,false, MouseEvent.BUTTON3));
        }
        if (inBounds(row,column-1) && isUnrevealed(row, column-1))
        {
            MineGUI.arr[row][column-1].mouseClicked(new MouseEvent(MineGUI.arr[row][column-1], MouseEvent.MOUSE_CLICKED,1,0,0,0,1,false, MouseEvent.BUTTON3));
        }
        if (inBounds(row,column+1) && isUnrevealed(row, column+1))
        {
           MineGUI.arr[row][column+1].mouseClicked(new MouseEvent(MineGUI.arr[row][column+1], MouseEvent.MOUSE_CLICKED,1,0,0,0,1,false, MouseEvent.BUTTON3));
        }
        if (inBounds(row-1,column-1) && isUnrevealed(row-1, column-1))
        {
            MineGUI.arr[row-1][column-1].mouseClicked(new MouseEvent(MineGUI.arr[row-1][column-1], MouseEvent.MOUSE_CLICKED,1,0,0,0,1,false, MouseEvent.BUTTON3));
        }
        if (inBounds(row-1,column+1) && isUnrevealed(row-1, column+1))
        {
            MineGUI.arr[row-1][column+1].mouseClicked(new MouseEvent(MineGUI.arr[row-1][column+1], MouseEvent.MOUSE_CLICKED,1,0,0,0,1,false, MouseEvent.BUTTON3));
        }
        if (inBounds(row+1,column-1) && isUnrevealed(row+1, column-1))
        {
            MineGUI.arr[row+1][column-1].mouseClicked(new MouseEvent(MineGUI.arr[row+1][column-1], MouseEvent.MOUSE_CLICKED,1,0,0,0,1,false, MouseEvent.BUTTON3));
        }
        if (inBounds(row+1,column+1) && isUnrevealed(row+1, column+1))
        {
            MineGUI.arr[row+1][column+1].mouseClicked(new MouseEvent(MineGUI.arr[row+1][column+1], MouseEvent.MOUSE_CLICKED,1,0,0,0,1,false, MouseEvent.BUTTON3));
        }
        //  return n;
    }

    public static int unrevealedNeighbors (int row, int column)
    {
        int n = 0;
        if (inBounds(row+1,column) && isUnrevealed(row+1, column))
        {
            n+=1;
        }
        if (inBounds(row-1,column) && isUnrevealed(row-1, column))
        {
            n+= 1;
        }
        if (inBounds(row,column-1) && isUnrevealed(row, column-1))
        {
            n+=1;
        }
        if (inBounds(row,column+1) && isUnrevealed(row, column+1))
        {
            n+=1;
        }
        if (inBounds(row-1,column-1) && isUnrevealed(row-1, column-1))
        {
            n+=1;
        }
        if (inBounds(row-1,column+1) && isUnrevealed(row-1, column+1))
        {
            n+=1;
        }
        if (inBounds(row+1,column-1) && isUnrevealed(row+1, column-1))
        {
            n+=1;
        }
        if (inBounds(row+1,column+1) && isUnrevealed(row+1, column+1))
        {
            n+=1;
        }
        return n;
    }

    public static boolean inBounds (int row, int column)
    {
        return (row < MineGUI.ROWS) && (column<MineGUI.COLS) && row>-1 && column>-1;
    }

    public static  boolean isUnrevealed(int row, int col)
    {
        return !MineGUI.arr[row][col].revealed;
    }

    public static int percentRevealed()
    {
        int total = MineGUI.ROWS*MineGUI.COLS;
        int numRev = 0;
        for (int i = 0; i<MineGUI.ROWS; i++)
        {
            for (int j = 0; j<MineGUI.COLS; j++)
            {
                if (MineGUI.arr[i][j].revealed)
                {
                    numRev++;
                }
            }
        }
        return (int)(((numRev * 1.0)/(total*1.0))*100);
    }
}
