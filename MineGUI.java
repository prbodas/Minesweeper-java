import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;

/**
 * This lab took me 9 hours.
 * Nothing worked quickly. The 
 * file URLs were messed up 
 * and the images were confusing. 
 * However, the recursive method was
 * not too difficult. All in all, 
 * this was a difficult lab.
 * 
 * Prachi Bodas Period 3
 */
public class MineGUI
{   
    public static int ROWS = 16;
    public static int COLS = 16;
    
    public static boolean time = false;
    static int timerSeconds = 0;

    public static BoardPanel[][] arr = new BoardPanel[ROWS][COLS];

    public static javax.swing.Timer timer = new javax.swing.Timer(1000, new TimerListener()); // FIX
    
    public static int numMines = 8; // number of mines, change in menu class
    
    public static int MIN = 0;
    
    static JLabel timeL;
    static JLabel mine;

    public static void draw()
    {
        // make new JFrame window
        JFrame frame = new JFrame("Minesweeper");
        frame.setLayout(new BorderLayout());

        JPanel main = new JPanel();
        //main.setBackground(Color.gray);
        main.setPreferredSize(new Dimension(400,400));

        frame.setResizable(true);
        main.setLayout(new GridLayout(ROWS,COLS));
        main.setBorder(BorderFactory.createLineBorder(Color.gray, 20, false));

        for (int i = 0; i<(ROWS); i++)
        {
            for (int j = 0; j<COLS; j++)
            {
                BoardPanel panel = new BoardPanel(i,j);
                panel.setBackground(Color.white);
                panel.setBorder(new BevelBorder(BevelBorder.RAISED));
                panel.addMouseListener(panel);
                panel.display = BoardPanel.NONE;
                arr[i][j] = panel;
                main.add(panel);
            }
        }

        JPanel buttons = new JPanel();
        buttons.setPreferredSize(new Dimension(100, 100));
        buttons.setLayout(new GridLayout(1,2));

        // add labels to buttons part of gui
        JPanel timeElapsed = new JPanel();
        timeElapsed.setPreferredSize(new Dimension(50,50));
        timeElapsed.setBorder(new TitledBorder("Time Elapsed"));
        timeL = new JLabel("0"); //setText can change text
        timeElapsed.add(timeL);

        JPanel mines = new JPanel();
        mines.setBorder(new TitledBorder("Mines Remaining"));
        mine = new JLabel(numMines + ""); //setText can change text
        mines.setPreferredSize(new Dimension(50,50));
        mines.add(mine);

        buttons.add(timeElapsed);
        buttons.add(mines);

        // MENU BAR
        JMenuBar menuBar = new JMenuBar();
        
        // Game menu
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem exit = new JMenuItem("Exit");
        
        newGame.setActionCommand("New Game");
        newGame.addActionListener(new MenuAction());
        exit.setActionCommand("Exit");
        exit.addActionListener(new MenuAction());
        
        // Options menu
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem totMines = new JMenuItem("Total Mines");
        totMines.setActionCommand("Total Mines");
        totMines.addActionListener(new MenuAction());
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem howtoPlay = new JMenuItem("How to Play");
        JMenuItem about = new JMenuItem("About");
        howtoPlay.setActionCommand("How to Play");
        howtoPlay.addActionListener(new MenuAction());
        about.setActionCommand("About");
        about.addActionListener(new MenuAction());
        
        // add bar
        gameMenu.add(newGame);
        gameMenu.add(exit);
        
        optionsMenu.add(totMines);
        
        helpMenu.add(howtoPlay);
        helpMenu.add(about);
        
      
        menuBar.add(gameMenu);
        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        //after initial settings
        buttons.setVisible(true);
        frame.add(main, BorderLayout.PAGE_START);
        frame.add(buttons, BorderLayout.PAGE_END);
        frame.pack();
        frame.setVisible(true);
        
        try
        {
            BoardPanel.questionImg = ImageIO.read(new File ("question.png"));
            BoardPanel.flagImg = ImageIO.read(new File ("flag.png"));
            BoardPanel.mineImg = ImageIO.read(new File ("mine.png"));
            BoardPanel.blankImg = ImageIO.read(new File ("blank.png"));
            BoardPanel.whiteImg = ImageIO.read(new File ("White12.jpg"));

        }catch (Exception e)
        {
            System.out.println("images");
        }

    }
    
    public static void showBoard(int thisrow, int thiscolumn)
    {
        int i;
        
        if (thisrow < 0 || thisrow >= ROWS) {
            return;
        }
        if (thiscolumn < 0 || thiscolumn >= COLS) {
            return;
        }
        
        
        if (arr[thisrow][thiscolumn].revealed) 
                return;
        arr[thisrow][thiscolumn].revealed = true;
        
        arr[thisrow][thiscolumn].setBackground(Color.gray);
        arr[thisrow][thiscolumn].repaint();
        
        if (arr[thisrow][thiscolumn].state == BoardPanel.NUMBER) {
            arr[thisrow][thiscolumn].display = BoardPanel.SHOWNUMBER;
            arr[thisrow][thiscolumn].setBackground(Color.white);
            arr[thisrow][thiscolumn].repaint();
        } else {
            showBoard(thisrow, thiscolumn + 1);
            showBoard(thisrow, thiscolumn - 1);
            showBoard(thisrow + 1, thiscolumn);
            showBoard(thisrow - 1, thiscolumn);
            showBoard(thisrow - 1, thiscolumn - 1);
            showBoard(thisrow + 1, thiscolumn + 1);
            showBoard(thisrow - 1, thiscolumn + 1);
            showBoard(thisrow + 1, thiscolumn - 1);
        }
    }
    
    public static void showallMines()
    {
        for (int i = 0; i<ROWS; i++)
        {
            for (int j = 0; j<COLS; j++)
            {
                if (arr[i][j].state == BoardPanel.MINE) {
                    arr[i][j].display = BoardPanel.SHOWMINE;
                    arr[i][j].repaint();
                };
            }
        }
    }
    
    public static void placeMines()
    {
        MIN = numMines;
        for (int i = 0; i<ROWS; i++)
        {
            for (int j = 0; j<COLS; j++)
            {
                arr[i][j].state = BoardPanel.BLANK;
            }
        }
        
        for (int i = 0; i<numMines; i++)
        {
            Random rand = new Random();
            int r = rand.nextInt(ROWS);
            int c = rand.nextInt(COLS);
            while(arr[r][c].state == BoardPanel.MINE)
            {
                r = rand.nextInt(ROWS);
                c = rand.nextInt(COLS);
            }
            arr[r][c].state = BoardPanel.MINE;
        }
        
        for (int i = 0; i<ROWS; i++)
        {
            for (int j = 0; j<COLS; j++)
            {
                arr[i][j].changeState();
            }
        }
    }

    public static void main (String[] args)
    {
        //MineGUI a = new MineGUI();
        MineGUI.draw();
        MineGUI.placeMines();
        //MinesweeperSolver.solveGUI();
    }
}

