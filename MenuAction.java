import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class MenuAction implements ActionListener
{

    public void actionPerformed(ActionEvent e) 
    {  
        if (e.getActionCommand().equals ("Exit")){
            System.exit(0);
        }
        if (e.getActionCommand().equals ("New Game")) {
            String[] a = {""};
            MineGUI.main(a);
        }
        if (e.getActionCommand().equals ("Total Mines")) {

            String s = (String)JOptionPane.showInputDialog(null,"Enter number of Mines:\n");
            MineGUI.numMines = Integer.parseInt(s);
            MineGUI.placeMines();
        }
        if (e.getActionCommand().equals ("About")) {
            JEditorPane editorPane = new JEditorPane();
            //String url= "InstructionsWebPage.html";    
            editorPane.setEditable(false);
            try {
                editorPane.setPage(new File("Aboutwebpage.html").toURI().toURL());
                editorPane.setVisible(true);
            } catch (IOException ekiki) {
                System.err.println("Attempted to read a bad URL: ");
            }
            //JOptionPane.showMessageDialog(null, "Hello");
            JFrame aboutFrame = new JFrame("About");
            aboutFrame.add(editorPane);
            aboutFrame.setSize(new Dimension(800, 800));
            aboutFrame.setVisible(true);
        }
        if (e.getActionCommand().equals("How to Play"))
        {
            JEditorPane editorPane = new JEditorPane();
            //String url= "file:///Aboutwebpage.html";    
            editorPane.setEditable(false);
            try {
                editorPane.setPage(new File("InstructionsWebPage.html").toURI().toURL());
                editorPane.setVisible(true);
            } catch (IOException ekiki) {
                System.err.println("Attempted to read a bad URL: ");
            }
            //JOptionPane.showMessageDialog(null, "Hello");
            JFrame aboutFrame = new JFrame("How to Play");
            aboutFrame.add(editorPane);
            aboutFrame.setSize(new Dimension(800, 800));
            aboutFrame.setVisible(true);
        }
    }
}

