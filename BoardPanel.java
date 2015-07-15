import java.awt.*; 
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
//import javax.image.*;
import java.net.*;

public class BoardPanel extends JPanel implements MouseListener
{
    public int display = 0;
    public static int NONE = 0;
    public static int FLAG = 1 ;
    public static int QUESTION = 2;
    public static int SHOWNUMBER = 3;
    public static int SHOWMINE = 4;
    public static int WHITE = 5;

    public int state = 0;
    public boolean revealed = false;
    public static int BLANK = 0;
    public static int NUMBER = 1 ;
    public static int MINE = 2;

    public int neighbors = -1;

    public int row = 0;
    public int column = 0;

    BufferedImage img = null;

    public static BufferedImage questionImg;
    public static BufferedImage flagImg;
    public static BufferedImage blankImg;
    public static BufferedImage mineImg;
    public static BufferedImage whiteImg;

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        super.paintComponent(g);
        if (display == QUESTION) {
            //super.paintComponent(g);
            if (questionImg != null) {
                g.drawImage(questionImg.getScaledInstance(getHeight(), getWidth(),Image.SCALE_DEFAULT), 0, 0, null);
            }
        } else if (display == FLAG) {
            //super.paintComponent(g);
            if (flagImg != null) {
                g.drawImage(flagImg.getScaledInstance(getHeight(), getWidth(),Image.SCALE_DEFAULT), 0, 0, null);
            }
        } else if (display == NONE) {
            //super.paintComponent(g);
            if (blankImg != null) 
                g.drawImage(blankImg.getScaledInstance(getHeight(), getWidth(),Image.SCALE_DEFAULT), 0, 0, null);
        } else if (display == SHOWNUMBER) {
            g.setColor(Color.red);
            g.drawString(""+neighbors, getHeight()/2, getWidth()/2);
            //g.drawImage(flagImg.getScaledInstance(getHeight(), getWidth(),Image.SCALE_DEFAULT), 0, 0, null);
        } else if (display == SHOWMINE) {
            g.setColor(Color.red);
            g.drawImage(mineImg.getScaledInstance(getHeight(), getWidth(),Image.SCALE_DEFAULT), 0, 0, null);
        } else if (display == WHITE)
        {
            if (whiteImg != null) 
                g.drawImage(whiteImg.getScaledInstance(getHeight(), getWidth(),Image.SCALE_DEFAULT), 0, 0, null);
        }
    }

    public BoardPanel(int r, int c)
    {
        row = r;
        column = c;
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void mouseClicked(MouseEvent e)
    {
        if (!MineGUI.time)
        {
            MineGUI.timer.start();
        }
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            if (state == MINE)
            {
                gameOver();
            }
            else
                MineGUI.showBoard(row, column);
        } else if (e.getButton() == MouseEvent.BUTTON3)
        {
            //label things //need debug
            if (display == NONE||display == WHITE)
            {
                display = FLAG;
                MineGUI.numMines -= 1;
                MineGUI.mine.setText(MineGUI.numMines+"");
                
            }else if (display == FLAG)
            {
                display = QUESTION;
                MineGUI.numMines += 1;
                MineGUI.mine.setText(MineGUI.numMines+"");
                checkWin();
            }else if (display == QUESTION)
            {
                display = WHITE;
            }
            repaint();
            //changeDisplay();
        }
    }
    
    public void checkWin()
    {
        int n = 0;
        for (int i = 0; i<MineGUI.ROWS; i++)
        {
            for (int j = 0; j<MineGUI.COLS; j++)
            {
                if (MineGUI.arr[i][j].display == FLAG && MineGUI.arr[i][j].state == MINE)
                {
                    n++;
                }
            }
        }
        if (n==MineGUI.MIN)
        {
            MineGUI.mine.setText("You win!");
            MineGUI.timer.stop();
        }
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void gameOver()
    {
        // game over actions
        MineGUI.showallMines();
        MineGUI.timer.stop();
        //System.exit(0);
    }

    public void clickActions(int r, int c)
    {
        if (MineGUI.arr[r][c].state != BLANK)
        {

        }
    }

    public void changeState()
    {
        int n = countLiveNeighbors();
        if (state != MINE && n>0)
        {
            state = NUMBER;
            neighbors = n;
        }
    }    

    public int countLiveNeighbors ()
    {
        int n = 0;
        if (inBounds(row+1,column))
        {
            if (isMine(row+1, column)){
                n+=1;
            }
        }
        if (inBounds(row-1,column) )
        {
            if (isMine(row-1, column)){
                n+=1;
            }
        }
        if (inBounds(row,column-1) && isMine(row, column-1))
        {
            n+=1;
        }
        if (inBounds(row,column+1) && isMine(row, column+1))
        {
            n+=1;
        }
        if (inBounds(row-1,column-1) && isMine(row-1, column-1))
        {
            n+=1;
        }
        if (inBounds(row-1,column+1) && isMine(row-1, column+1))
        {
            n+=1;
        }
        if (inBounds(row+1,column-1) && isMine(row+1, column-1))
        {
            n+=1;
        }
        if (inBounds(row+1,column+1) && isMine(row+1, column+1))
        {
            n+=1;
        }
        return n;
    }

    public boolean inBounds (int row, int column)
    {
        return (row < MineGUI.ROWS) && (column<MineGUI.COLS) && row>-1 && column>-1;
    }

    public boolean isMine(int row, int col)
    {
        return MineGUI.arr[row][col].state == MINE;
    }
}
