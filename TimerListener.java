import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
public class TimerListener implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        MineGUI.timerSeconds += 1;
        MineGUI.timeL.setText(MineGUI.timerSeconds+"");
    }
}
