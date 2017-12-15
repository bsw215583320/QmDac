package lanbao.model;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

public class NumberInputPnl
  extends JPanel
{
  private Map<String, Integer> keyCodeMap;
  private Robot robot;
  private Action clickAction = new AbstractAction()
  {
    public void actionPerformed(ActionEvent e)
    {
      JButton b = (JButton)e.getSource();
      Integer code = (Integer)NumberInputPnl.this.keyCodeMap.get(b.getName());
      if (code != null)
      {
        NumberInputPnl.this.robot.keyPress(code.intValue());
        NumberInputPnl.this.robot.keyRelease(code.intValue());
      }
    }
  };
  private JButton _0btn;
  private JButton _1btn;
  private JButton _2btn;
  private JButton _3btn;
  private JButton _4btn;
  private JButton _5btn;
  private JButton _6btn;
  private JButton _7btn;
  private JButton _8btn;
  private JButton _9btn;
  private JButton decimalBtn;
  private JButton jButton14;
  
  public NumberInputPnl()
  {
    initComponents();
    setFocusable(false);
    try
    {
      this.robot = new Robot();
    }
    catch (AWTException ex)
    {
      Logger.getLogger(NumberInputPnl.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.keyCodeMap = new HashMap();
    for (int i = 0; i < 10; i++) {
      this.keyCodeMap.put(String.valueOf(i), Integer.valueOf(48 + i));
    }
    this.keyCodeMap.put(".", Integer.valueOf(110));
    this.keyCodeMap.put("<-", Integer.valueOf(8));
  }
  
  private void initComponents()
  {
    this._1btn = new JButton();
    this._2btn = new JButton();
    this._3btn = new JButton();
    this._4btn = new JButton();
    this._5btn = new JButton();
    this._6btn = new JButton();
    this._7btn = new JButton();
    this._8btn = new JButton();
    this._9btn = new JButton();
    this.decimalBtn = new JButton();
    this._0btn = new JButton();
    this.jButton14 = new JButton();
    setLayout(new GridLayout(3, 4));
    
    this._1btn.setAction(this.clickAction);
    this._1btn.setFont(new Font("宋体", 1, 12));
    this._1btn.setText("1");
    this._1btn.setFocusable(false);
    this._1btn.setName("1");
    add(this._1btn);
    
    this._2btn.setAction(this.clickAction);
    this._2btn.setFont(new Font("宋体", 1, 12));
    this._2btn.setText("2");
    this._2btn.setFocusable(false);
    this._2btn.setName("2");
    add(this._2btn);
    
    this._3btn.setAction(this.clickAction);
    this._3btn.setFont(new Font("宋体", 1, 12));
    this._3btn.setText("3");
    this._3btn.setFocusable(false);
    this._3btn.setName("3");
    add(this._3btn);
    
    this._4btn.setAction(this.clickAction);
    this._4btn.setFont(new Font("宋体", 1, 12));
    this._4btn.setText("4");
    this._4btn.setFocusable(false);
    this._4btn.setName("4");
    add(this._4btn);
    
    this._5btn.setAction(this.clickAction);
    this._5btn.setFont(new Font("宋体", 1, 12));
    this._5btn.setText("5");
    this._5btn.setFocusable(false);
    this._5btn.setName("5");
    add(this._5btn);
    
    this._6btn.setAction(this.clickAction);
    this._6btn.setFont(new Font("宋体", 1, 12));
    this._6btn.setText("6");
    this._6btn.setFocusable(false);
    this._6btn.setName("6");
    add(this._6btn);
    
    this._7btn.setAction(this.clickAction);
    this._7btn.setFont(new Font("宋体", 1, 12));
    this._7btn.setText("7");
    this._7btn.setFocusable(false);
    this._7btn.setName("7");
    add(this._7btn);
    
    this._8btn.setAction(this.clickAction);
    this._8btn.setFont(new Font("宋体", 1, 12));
    this._8btn.setText("8");
    this._8btn.setFocusable(false);
    this._8btn.setName("8");
    add(this._8btn);
    
    this._9btn.setAction(this.clickAction);
    this._9btn.setFont(new Font("宋体", 1, 12));
    this._9btn.setText("9");
    this._9btn.setFocusable(false);
    this._9btn.setName("9");
    add(this._9btn);
    
    this._0btn.setAction(this.clickAction);
    this._0btn.setFont(new Font("宋体", 1, 12));
    this._0btn.setText("0");
    this._0btn.setFocusable(false);
    this._0btn.setName("0");
    add(this._0btn);
    
    this.decimalBtn.setAction(this.clickAction);
    this.decimalBtn.setFont(new Font("宋体", 1, 12));
    this.decimalBtn.setText(".");
    this.decimalBtn.setFocusable(false);
    this.decimalBtn.setName(".");
    add(this.decimalBtn);
    this.jButton14.setAction(this.clickAction);
    this.jButton14.setFont(new Font("宋体", 1, 12));
    this.jButton14.setText("<-");
    this.jButton14.setFocusable(false);
    this.jButton14.setName("<-");
    add(this.jButton14);
  }
}
