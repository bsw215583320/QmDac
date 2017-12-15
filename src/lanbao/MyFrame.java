package lanbao;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import lanbao.model.ItemTableModel;
import lanbao.model.NumberInputPnl;
import lanbao.model.PuffSchemeItem;
import lanbao.utils.JdbcUtil;
import oracle.net.aso.r;

import org.jdesktop.swingx.JXDatePicker;

public class MyFrame
  extends JFrame
{
  private static final long serialVersionUID = 1L;
  private JdbcUtil db = new JdbcUtil();
  private JPanel panel;
  private JTextField wgt;
  private JTextField pdo;
  private JXDatePicker datePicker;
  private JXDatePicker datePicker2;
  private JComboBox checker;
  private JComboBox chkBatch;
  private JComboBox batch;
  private JTable table;
  private JScrollPane jScrollPane;
  private Map<String, String> batchMap=new HashMap<>();
  
  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          MyFrame frame = new MyFrame();
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }
  
  public MyFrame()
  {
    setFont(new Font("Dialog", 0, 12));
    setResizable(false);
    setDefaultCloseOperation(3);
    
    Date date = new Date();
    setSize(486, 413);
    setTitle("兰宝数采终端");
    getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
    this.panel = new JPanel();
    getContentPane().add(this.panel);
    this.panel.setLayout(null);
    
    JPanel panel_2 = new JPanel();
    panel_2.setBounds(0, 0, 481, 124);
    this.panel.add(panel_2);
    panel_2.setLayout(null);
    
    this.datePicker = new JXDatePicker();
    this.datePicker.setBounds(90, 44, 109, 23);
    this.datePicker.setDate(date);
    this.datePicker.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MyFrame.this.getBatch();
      }
    });
    panel_2.add(this.datePicker);
    
    this.datePicker2 = new JXDatePicker();
    this.datePicker2.setBounds(90, 81, 109, 23);
    this.datePicker2.setDate(date);
    this.datePicker2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MyFrame.this.getData();
      }
    });
    panel_2.add(this.datePicker2);
    
    JLabel label = new JLabel("转盘吸烟机-质量数据采集");
    label.setBounds(164, 10, 148, 15);
    panel_2.add(label);
    
    JLabel label_1 = new JLabel("选择批次");
    label_1.setBounds(239, 48, 55, 15);
    panel_2.add(label_1);
    
    this.batch = new JComboBox();
    this.batch.setBounds(304, 45, 148, 21);
    panel_2.add(this.batch);
    
    JLabel label_2 = new JLabel("检验数据");
    label_2.setBounds(239, 85, 55, 15);
    panel_2.add(label_2);
    
    this.chkBatch = new JComboBox();
    this.chkBatch.setBounds(304, 82, 148, 21);
    this.chkBatch.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MyFrame.this.fillTable();
      }
    });
    panel_2.add(this.chkBatch);
    final NumberInputPnl nInputPnl = new NumberInputPnl();
    nInputPnl.setBounds(0, 180, 200, 100);
    this.panel.add(nInputPnl);
    nInputPnl.setVisible(false);
    final NumberInputPnl nInputPnl1 = new NumberInputPnl();
    nInputPnl1.setBounds(280, 180, 200, 100);
    this.panel.add(nInputPnl1);
    nInputPnl1.setVisible(false);
    
    JLabel label_6 = new JLabel("\u6279\u6B21\u65E5\u671F");
    label_6.setBounds(25, 49, 55, 15);
    panel_2.add(label_6);
    
    JLabel label_9 = new JLabel("\u68C0\u9A8C\u65E5\u671F");
    label_9.setBounds(25, 85, 54, 15);
    panel_2.add(label_9);
    
    JPanel panel_1 = new JPanel();
    panel_1.setBounds(0, 257, 481, 127);
    this.panel.add(panel_1);
    panel_1.setLayout(null);
    
    JLabel label_3 = new JLabel("平均重量");
    label_3.setBounds(35, 26, 57, 15);
    panel_1.add(label_3);
    
    this.wgt = new JTextField();
    this.wgt.addFocusListener(new FocusAdapter()
    {
      public void focusGained(FocusEvent e)
      {
        MyFrame.this.addkey(nInputPnl);
      }
      
      public void focusLost(FocusEvent e)
      {
        MyFrame.this.removekey(nInputPnl);
      }
    });
    this.wgt.setBounds(101, 23, 73, 21);
    this.wgt.setColumns(10);
    this.wgt.addKeyListener(new KeyAdapter()
    {
      public void keyTyped(KeyEvent e)
      {
        char c = e.getKeyChar();
        String str = String.valueOf(c);
        Pattern p = Pattern.compile("^\\d*|0|\\.$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
          return;
        }
        e.consume();
      }
    });
    panel_1.add(this.wgt);
    
    JLabel label_4 = new JLabel("g");
    label_4.setBounds(184, 26, 12, 15);
    panel_1.add(label_4);
    
    JButton button = new JButton("提交");
    button.setBounds(296, 67, 79, 36);
    button.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        String batchValue = "";
        String chkValue = "";
        String chker = "";
        Double wgtVal = Double.valueOf(0.0D);
        Double pdoVal = Double.valueOf(0.0D);
        if (MyFrame.this.batch.getSelectedItem() == null)
        {
          JOptionPane.showMessageDialog(null, "请选择批次号", "提示", 1);
          return;
        }
        batchValue = MyFrame.this.batch.getSelectedItem().toString();
        if ((MyFrame.this.chkBatch.getSelectedItem() == null) || (MyFrame.this.table.getSelectedRows().length < 1))
        {
          JOptionPane.showMessageDialog(null, "请选择检验数据", "提示", 1);
          return;
        }
        chkValue = MyFrame.this.chkBatch.getSelectedItem().toString();
        if (MyFrame.this.checker.getSelectedItem() == null)
        {
          JOptionPane.showMessageDialog(null, "请选择检验人", "提示", 1);
          return;
        }
        chker = MyFrame.this.checker.getSelectedItem().toString();
        if ("".equals(MyFrame.this.wgt.getText().trim()))
        {
          JOptionPane.showMessageDialog(null, "平均重量不可为空", "提示", 1);
          return;
        }
        wgtVal = Double.valueOf(Double.parseDouble(MyFrame.this.wgt.getText()));
        if ("".equals(MyFrame.this.pdo.getText().trim()))
        {
          JOptionPane.showMessageDialog(null, "平均吸阻不可为空", "提示", 1);
          return;
        }
        pdoVal = Double.valueOf(Double.parseDouble(MyFrame.this.pdo.getText()));
        
        String schemeId = UUID.randomUUID().toString().replaceAll("-", "");
        String queryScheme = "select * from puffscheme where id||name = '" + chkValue + "'";
        try
        {
          List<Map<String, Object>> schemes = db.execQuery(queryScheme, null, "h2");
          Object[] params;
          if ((schemes != null) && (schemes.size() > 0))
          {
            Map<String, Object> scheme = (Map)schemes.get(0);
            Double airflow = Double.valueOf(0.0D);
           List<?> airflows = db.execQuery("select values from airflow where id = " + scheme.get("AIRFLOWID"), null, "h2");
            if ((airflows != null) && (airflows.size() > 0))
            {
              Map<String, Object> flow = (Map)airflows.get(0);
              String[] flows = flow.get("VALUES").toString().split(",");
              String[] arrayOfString1;
              int j = (arrayOfString1 = flows).length;
              for (int i = 0; i < j; i++)
              {
                String string = arrayOfString1[i];
                airflow = Double.valueOf(airflow.doubleValue() + Double.parseDouble(string));
              }
              airflow = Double.valueOf(airflow.doubleValue() / flows.length);
            }
           String insertScheme = "insert into qm_puffscheme(ID,NAME,CREATETIME,FINISHTIME,AIRFLOW,PUFFVOLUME,PUFFDURATION,INTERVAL,BARB,TEMP,BATCH_CODE,WGT_AVG,PDO_AVG,CHECKER,CHECK_TIME)values(?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,sysdate)";
            
            params = new Object[] { schemeId, scheme.get("NAME"), scheme.get("CREATETIME").toString().substring(0, 19), scheme.get("FINISHTIME").toString().substring(0, 19), airflow, scheme.get("PUFFVOLUME"), 
              scheme.get("PUFFDURATION"), scheme.get("INTERVAL"), scheme.get("ATMOSPHERICPRESSURE"), scheme.get("TEMPERATURE"), batchMap.get(batchValue), 
              wgtVal, pdoVal, chker };
            MyFrame.this.db.execUpdate(insertScheme, params, "oracle");
          }
          int[] rows = table.getSelectedRows();
        
          for (int i = 0; i <  rows.length; i++)
          {
            List<?> items = db.execQuery("select * from puffschemeitem where id = " + table.getValueAt(i, 0), null, "h2");
            if ((items != null) && (items.size() > 0))
            {
              Object item = (Map)items.get(0);
              Double puffs = Double.valueOf(0.0D);
              String[] puffValues = ((Map)item).get("PUFFVALUES").toString().split(",");
              String[] arrayOfString2;
              int m = (arrayOfString2 = puffValues).length;
              for (int k = 0; k < m; k++)
              {
                String string = arrayOfString2[k];
                puffs = Double.valueOf(puffs.doubleValue() + Double.parseDouble(string));
              }
              Double AVGpuffs = Double.valueOf(puffs.doubleValue() / puffValues.length);
              String insert = "insert into qm_puffschemeitem(ID,NAME,SCHEME_ID,BANKNUM,CIG_LEN,BUTTLEN,CIG_COUNT,NOPUFF_COUNT,BF_WEIGHT,AF_WEIGHT,UNBURNED,COVALUE,PUFFVALUES,SPARED)values(sys_guid(),?,?,?,?,?,?,?,?,?,?,?,?,?)";
              
              Object[] params1 = { ((Map)item).get("NAME"), schemeId, ((Map)item).get("BANKNUM"), ((Map)item).get("CIGARETTELEN"), ((Map)item).get("BUTTLEN"), ((Map)item).get("CIGARETTECOUNT"), ((Map)item).get("NOPUFFCOUNT"), 
                ((Map)item).get("BEFOREWEIGHT"), ((Map)item).get("AFTERWEIGHT"), ((Map)item).get("UNBURNEDCIGARETTECOUNT"), ((Map)item).get("COVALUE"), AVGpuffs, puffs };
              MyFrame.this.db.execUpdate(insert, params1, "oracle");
            }
          }
         db.execUpdate("update qm_batch set isflag = 1 where category = 2 and  batch_code = '" + batchValue + "'", null, "oracle");
          getBatch();
          pdo.setText("");
          wgt.setText("");
          JOptionPane.showMessageDialog(null, "采集完成", "提示", 1);
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
          JOptionPane.showMessageDialog(null, "采集失败", "提示", 0);
        }
      }
    });
    panel_1.add(button);
    
    JLabel label_7 = new JLabel("平均吸阻");
    label_7.setBounds(264, 26, 57, 15);
    panel_1.add(label_7);
    
    this.pdo = new JTextField();
    this.pdo.setBounds(330, 23, 73, 21);
    this.pdo.addFocusListener(new FocusAdapter()
    {
      public void focusGained(FocusEvent e)
      {
        MyFrame.this.addkey(nInputPnl1);
      }
      
      public void focusLost(FocusEvent e)
      {
        MyFrame.this.removekey(nInputPnl1);
      }
    });
    this.pdo.setColumns(10);
    this.pdo.addKeyListener(new KeyAdapter()
    {
      public void keyTyped(KeyEvent e)
      {
        char c = e.getKeyChar();
        
        String str = String.valueOf(c);
        Pattern p = Pattern.compile("^\\d*|0|\\.$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
          return;
        }
        e.consume();
      }
    });
    panel_1.add(this.pdo);
    
    JLabel label_8 = new JLabel("Pa");
    label_8.setBounds(413, 26, 24, 15);
    panel_1.add(label_8);
    JLabel label_5 = new JLabel("检验员");
    label_5.setBounds(48, 78, 44, 15);
    panel_1.add(label_5);
    
    this.checker = new JComboBox();
    checker.setBounds(101, 75, 109, 21);
    panel_1.add(checker);
    
    this.table = new JTable();
    
    this.table.setPreferredScrollableViewportSize(new Dimension(469, 130));
    
    this.jScrollPane = new JScrollPane(this.table);
    this.jScrollPane.setBounds(0, 125, 481, 132);
    this.jScrollPane.setAutoscrolls(true);
    this.panel.add(this.jScrollPane);
    getData();
    getBatch();
    getChker();
    fillTable();
  }
  
  public List<String> initcombo(List<Map<String, Object>> list)
  {
    List<String> strings = new ArrayList();
    if ((list != null) && (list.size() > 0)) {
      for (Map<String, Object> map : list) {
        strings.add((String)map.get("NAME"));
      }
    }
    return strings;
  }
  
  public void getChker()
  {
    String selectDate = new SimpleDateFormat("yyyy-MM-dd").format(this.datePicker.getDate());
    List<Map<String, Object>> chkerList = this.db.execQuery("select snam||pnam name from BASIS_USR_POS a join BASIS_USR b on a.usr = b.id where pos = 90738675", null, "oracle");
    String[] checkers = new String[0];
    if (chkerList != null) {
      checkers = (String[])initcombo(chkerList).toArray(new String[chkerList.size()]);
    }
    this.checker.setModel(new DefaultComboBoxModel(checkers));
  }
  
  public void getBatch()
  {
    String selectDate = new SimpleDateFormat("yyyy-MM-dd").format(this.datePicker.getDate());
    List<Map<String, Object>> batchList = this.db.execQuery("select b.des name,a.batch_code code from QM_BATCH a,md_mat b  where a.category = 2 and a.typ is null and a.mat_code = b.id and to_char(a.batch_date,'yyyy-mm-dd') = '" + selectDate + "'", null, "oracle");
    String[] batchs = new String[0];
    if (batchList != null) {
      batchs = (String[])initcombo(batchList).toArray(new String[batchList.size()]);
    }
    this.batch.setModel(new DefaultComboBoxModel(batchs));getContentPane();
    for (Map<String, Object> map : batchList) {
		batchMap.put(map.get("NAME").toString(), map.get("CODE").toString());
	}
  }
  
  public void getData()
  {
    String selectDate = new SimpleDateFormat("yyyy-MM-dd").format(this.datePicker2.getDate());
    List<Map<String, Object>> dataList = this.db.execQuery("SELECT id,name FROM PUFFSCHEME where formatdatetime(createtime,'yyyy-MM-dd')='" + selectDate + "'", null, "h2");
    String[] datas = new String[0];
    if (dataList != null) {
      datas = (String[])initcombo(dataList).toArray(new String[dataList.size()]);
    }
    this.chkBatch.setModel(new DefaultComboBoxModel(datas));
  
  }
  
  public void fillTable()
  {
    Object[] params = { this.chkBatch.getSelectedItem() };
    String sql = "select a.id,a.name,a.puffschemeid,a.banknum,a.cigarettelen,a.buttlen,a.cigarettecount,a.nopuffcount,a.beforeweight,a.afterweight,a.unburnedcigarettecount,a.covalue,a.sumpuffcount,a.puffvalues from PUFFSCHEMEITEM a join puffscheme b on a.puffschemeid = b.id and b.id||b.name = ?";
    List<Map<String, Object>> mList = this.db.execQuery(sql, params, "h2");
    List<PuffSchemeItem> list = new ArrayList();
    PuffSchemeItem item = null;
    for (Map<String, Object> map : mList)
    {
      item = new PuffSchemeItem();
      item.setId(map.get("ID") == null ? null : map.get("ID").toString());
      item.setName(map.get("NAME") == null ? null : map.get("NAME").toString());
      item.setBankNum(map.get("BANKNUM") == null ? null : map.get("BANKNUM").toString());
      item.setButtlen(map.get("BUTTLEN") == null ? null : map.get("BUTTLEN").toString());
      item.setCigarettelen(map.get("CIGARETTELEN") == null ? null : map.get("CIGARETTELEN").toString());
      item.setSchemeId(map.get("PUFFSCHEMEID") == null ? null : map.get("PUFFSCHEMEID").toString());
      item.setCigarettecount(map.get("CIGARETTECOUNT") == null ? null : map.get("CIGARETTECOUNT").toString());
      
      list.add(item);
    }
    ItemTableModel model = new ItemTableModel(list);
    this.table.setModel(model);
    for (int i = 0; i < this.table.getColumnCount(); i++) {
      this.table.getColumnModel().getColumn(i).setPreferredWidth(80);
    }
    this.table.setRowHeight(20);
  }
  
  public void addkey(NumberInputPnl inputPnl)
  {
    inputPnl.setVisible(true);
  }
  
  public void removekey(NumberInputPnl inputPnl)
  {
    inputPnl.setVisible(false);
  }
}
