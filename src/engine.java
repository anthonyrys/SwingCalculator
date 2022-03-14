import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

import java.awt.Font;
import java.awt.Color;

public class engine
{
  class frame
  {
    private JFrame f_;

    private JLabel initlabel(String name, int x, int y, int w, int h)
    {
      JLabel label = new JLabel(name, SwingConstants.RIGHT);
      label.setFont(new Font(this.label_font, Font.PLAIN, this.label_size));
      label.setBounds(x, y, w, h);
      label.setForeground(Color.white);

      return (label);
    }
    
    private JButton initbutton(String name, int x, int y, int w, int h, int style)
    {
      JButton button = new JButton(name);  
      button.setFont(new Font(this.button_font, Font.PLAIN, this.button_size));
      button.setBounds(x, y, w, h);
      button.setFocusPainted(false);

      switch (style)
      {
          case 1: 
            button.setBackground(Color.white);
            break;
          
          case 2: 
            button.setBackground(Color.orange);
            break;
          
          case 3: 
            button.setBackground(Color.darkGray);    
            button.setForeground(Color.white);
            break;
      }

      return (button);
    }
    
    frame(String t)
    {
      f_ = new JFrame(t);
      f_.setLayout(null); 

      switch(t)
      {
        case "calculator":
          String[] labels = 
          {
            "AC#1", "!#1", "^#1", "/#2", 
            "7#3", "8#3", "9#3", "*#2",
            "4#3", "5#3", "6#3", "-#2",
            "1#3", "2#3", "3#3", "+#2",
            "0#3", " ", ".#3", "=#2"
          };
          
          f_.setSize(300, 430);
          f_.getContentPane().setBackground(Color.black);
          
          f_.add(this.initlabel("null", 0, 0, (f_.getWidth() - 30), 100));
          
          int gx = 0, gy = -1;
          for (int i = 0; i < labels.length; i++)
          {
            if (gx % 4 == 0) gx = 0; 
            if (gx == 0) gy += 1;

            String istr = "";
            int isty = 1;

            try
            {
              istr = labels[i].substring(0, labels[i].indexOf('#'));
              isty = Integer.parseInt(labels[i].substring((labels[i].indexOf('#') + 1)));
            }

            catch(Exception e)
            {  
            }
            
            try
            {
              if (labels[i + 1].equals(" ")) 
              {
                f_.add(this.initbutton(istr, ((gx * 75)), (100 + ((gy * 60))), 150, 60, isty)); 
                gx += 2; 
                i += 1; 
                continue;
              }  
            }
              
            catch(Exception e) 
            {
            }
            
            f_.add(this.initbutton(istr, ((gx * 75)), (100 + ((gy * 60))), 75, 60, isty)); 
            gx += 1;
          }
                 
          f_.setVisible(true);
          break;
      }
    } 
    
    JFrame getframe()
    {
      return (f_);
    }

    String label_font = "Open Sans", button_font = "Open Sans";
    int label_size = 32, button_size = 18;
  }
    
  class compute
  {
  }
    
  class input implements KeyListener
  {
    @Override
    public void keyPressed(KeyEvent e) 
    {
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
    }
  }
}