import javax.swing.*;

import java.awt.event.*;
import java.awt.Font;
import java.awt.Color;

import java.lang.Math;

public class engine
{
  class frame
  {
    private JFrame f_;

    private JLabel initlabel(String name, int[] dmns)
    {
      JLabel label = new JLabel(name, SwingConstants.RIGHT);
      label.setFont(new Font(this.label_font, Font.PLAIN, this.label_size));
      label.setBounds(dmns[0], dmns[1], dmns[2], dmns[3]);
      label.setForeground(Color.white);

      return (label);
    }
    
    private JButton initbutton(String name, int[] dmns, int style, JLabel frame, compute comp)
    {
      JButton button = new JButton(name);  
      button.setFont(new Font(this.button_font, Font.PLAIN, this.button_size));
      button.setBounds(dmns[0], dmns[1], dmns[2], dmns[3]);
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

      button.addActionListener(new ActionListener()
      {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
          comp.addc(name);
          updateframe(frame, comp);
        }
      });

      return (button);
    }

    private void updateframe(JLabel frame, compute comp)
    {
      frame.setText(comp.getdisplay());
    }
    
    frame(String t)
    {
      f_ = new JFrame(t);
      f_.setLayout(null); 
      f_.setResizable(false);

      switch(t)
      {
        case "calculator":
          compute comp = new compute();
          String[] labels = 
          {
            "AC#1", "√#1", "^#1", "/#2", 
            "7#3", "8#3", "9#3", "*#2",
            "4#3", "5#3", "6#3", "-#2",
            "1#3", "2#3", "3#3", "+#2",
            "0#3", " ", ".#3", "=#2"
          };
          
          f_.setSize(300, 430);
          f_.getContentPane().setBackground(Color.black);

          int[] l_dmns = {0, 0, (f_.getWidth() - 30), 100};
          JLabel frame = this.initlabel("null", l_dmns);
          f_.add(frame);
          
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
            } catch(Exception e) {}
            
            try
            {
              if (labels[i + 1].equals(" ")) 
              {
                int[] b_dmns = {(gx * 75), (100 + (gy * 60)), 150, 60};
                f_.add(this.initbutton(istr, b_dmns, isty, frame, comp)); 
                gx += 2; 
                i += 1; 
                continue;
              }  
            } catch(Exception e) {}

            int[] b_dmns = {(gx * 75), (100 + (gy * 60)), 75, 60};
            f_.add(this.initbutton(istr, b_dmns, isty, frame, comp)); 
            gx += 1;
          }
                 
          f_.setVisible(true);
          updateframe(frame, comp);
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
    private int stg;
    private double total;
    private String operator = "", prim = "", seco = "", disp = "";
    private char[] operators = 
    {
      '^', '/', '*', '-', '+'
    }, special = {'√', '='};

    compute() 
    {
      this.total = 0;
      this.stg = 0;
    }

    private void setdisp(String dis)
    {
      if (((Double.parseDouble(dis)) % 1) == 0) this.disp = String.valueOf((int) (Double.parseDouble(dis))); 
      else this.disp = dis;
    }

    private void calculate()
    {
      double p, s;
      p = Double.parseDouble(prim);
      s = Double.parseDouble(seco);

      switch (operator)
      {
        case "^":
          this.total = Math.pow(p, s);
          break;
        case "/":
          this.total = (p / s);
          break;
        case "*":
          this.total = (p * s);
          break;
        case "-":
          this.total = (p - s);
          break;
        case "+":
          this.total = (p + s);
          break;
      }

      setdisp(String.valueOf(this.total));
    }
    
    void addc(String n)
    {
      if (n.equals("√"))
      {
        switch (stg)
        {
          case 0:
            if (prim.equals("")) return;
            try
            {
              Double.parseDouble(prim);
              prim = String.valueOf(Math.sqrt(Double.parseDouble(prim)));
            }
            catch(Exception e)
            {
              prim += "0";
              prim = String.valueOf(Math.sqrt(Double.parseDouble(prim)));
            }
            setdisp(prim);
            return;

          case 1:
            if (prim.equals("")) return;
            try
            {
              Double.parseDouble(prim);
              prim = String.valueOf(Math.sqrt(Double.parseDouble(prim)));
            }
            catch(Exception e)
            {
              prim += "0";
              prim = String.valueOf(Math.sqrt(Double.parseDouble(prim)));
            }
            setdisp(prim);
            return;

          case 2:
            try
            {
              Double.parseDouble(seco);
              seco = String.valueOf(Math.sqrt(Double.parseDouble(seco)));
            }
            catch(Exception e)
            {
              seco += "0";
              seco = String.valueOf(Math.sqrt(Double.parseDouble(seco)));
            }
            setdisp(prim);
            return;
            
          case 3:
            total = Math.sqrt(total);
            setdisp(String.valueOf(total));
            return;
        }
      }
      
      if (n.equals("AC")) 
      {
        this.total = 0;
        this.stg = 0;
        this.prim = "";
        this.seco = "";
        this.operator = "";
        setdisp("0");
      }
      else
      {
        if (String.valueOf(operators).contains(n) && stg == 0)
        {
          try
          {
            Double.parseDouble(prim);
          }
          catch (Exception e)
          {   
            prim += "0";
          }
          stg += 1;
        }
        else if (!String.valueOf(operators).contains(n) && stg == 1)
        {
          if (String.valueOf(special).contains(n)) return;
          stg += 1;
        }
        else if (stg == 3)
        {
          if (!String.valueOf(operators).contains(n) && !String.valueOf(special).contains(n))
          {
            this.total = 0;
            this.stg = 0;
            this.prim = n;
            this.seco = "";
            this.disp = "";
            this.operator = "";
            setdisp(prim);
            
            return;
          }
          else if (String.valueOf(operators).contains(n))
          {
            this.prim = String.valueOf(this.total);
            this.seco = "";
            this.operator = String.valueOf(n);
            stg = 2;
            
            return;
          }
          else if (n.equals("="))
          {
            this.prim = String.valueOf(this.total);
            calculate();
            
            return;
          }
        }
        else if (n.equals("="))
        {
          if (this.operator.equals("") || this.seco.equals("")) return;
          try
          {
            Double.parseDouble(seco);
          }
          catch (Exception e)
          {   
            seco += "0";
          }
          calculate();
          stg += 1;
          return;
        }
        
        switch (stg)
        {
          case 0:          
            if (n.equals(".") && prim.contains(".")) return;
            this.prim += n;
            setdisp(prim);
            break;

          case 1:
            this.operator = String.valueOf(n);
            break;

          case 2:
            if (String.valueOf(operators).contains(n))
            {
              try
              {
                Double.parseDouble(seco);
              }
              catch (Exception e)
              {   
                seco += "0";
              }
              calculate();
              this.prim = String.valueOf(this.total);
              this.seco = "";
              this.operator = n;
              stg = 2;
              
              return;
            }          
            if (n.equals(".") && seco.contains(".")) return;
            this.seco += n;
            setdisp(seco);
            break;
        }
      }
    }

    String getdisplay()
    {
      return (this.disp);
    }
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