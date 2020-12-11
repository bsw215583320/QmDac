package lanbao;

import java.awt.*;
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
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import lanbao.model.ItemTableModel;
import lanbao.model.NumberInputPnl;
import lanbao.model.PuffSchemeItem;
import lanbao.utils.JdbcUtil;
import oracle.net.aso.r;

import org.jdesktop.swingx.JXDatePicker;

class myCombo
        extends JComboBox {
  public myCombo() {
    super();
    setUI(new myComboUI());
  } //end of default constructor

  class myComboUI
          extends BasicComboBoxUI {
    protected ComboPopup createPopup() {
      BasicComboPopup popup = new BasicComboPopup(comboBox) {
        protected JScrollPane createScroller() {
          return new JScrollPane(list,
                  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                  ScrollPaneConstants.
                          HORIZONTAL_SCROLLBAR_AS_NEEDED);
        } //end of method createScroller
      };
      return popup;
    } //end of method createPopup
  } //end of inner class myComboUI
}
class CustomComboBoxRenderer extends BasicComboBoxRenderer
{
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                boolean cellHasFocus)
  {
    if (isSelected)
    {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
      if (-1 < index)
      {
        list.setToolTipText((value == null) ? null : value.toString());
      }
    }
    else
    {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }
    setFont(list.getFont());
    setText((value == null) ? "" : value.toString());
    return this;
  }
}

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
  private JTextField inter;

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
    this.batch.setRenderer(new CustomComboBoxRenderer());
    this.batch.setBounds(304, 45, 148, 21);
   /* this.batch.setPreferredSize(new Dimension(20, 20));
    this.batch.setMaximumSize(new Dimension(30, 20));*/
    panel_2.add(this.batch);

    JLabel label_2 = new JLabel("检验数据");
    label_2.setBounds(239, 85, 55, 15);
    panel_2.add(label_2);

    this.chkBatch = new JComboBox();
    this.chkBatch.setRenderer(new CustomComboBoxRenderer());
    this.chkBatch.setBounds(304, 82, 148, 21);
    this.chkBatch.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MyFrame.this.fillTable();
      }
    });
    panel_2.add(this.chkBatch);
  /*  final NumberInputPnl nInputPnl = new NumberInputPnl();
    nInputPnl.setBounds(0, 180, 200, 100);
    this.panel.add(nInputPnl);
    nInputPnl.setVisible(false);
    final NumberInputPnl nInputPnl1 = new NumberInputPnl();
    nInputPnl1.setBounds(280, 180, 200, 100);
    this.panel.add(nInputPnl1);
    nInputPnl1.setVisible(false);*/

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
    label_3.setBounds(21, 26, 57, 15);
    panel_1.add(label_3);

    this.wgt = new JTextField();
    /*this.wgt.addFocusListener(new FocusAdapter()
    {
      public void focusGained(FocusEvent e)
      {
        MyFrame.this.addkey(nInputPnl);
      }

      public void focusLost(FocusEvent e)
      {
        MyFrame.this.removekey(nInputPnl);
      }
    });*/
    this.wgt.setBounds(88, 23, 73, 21);
    this.wgt.setColumns(10);
   /* this.wgt.addKeyListener(new KeyAdapter()
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
    });*/
    panel_1.add(this.wgt);

    JLabel label_4 = new JLabel("g");
    label_4.setBounds(171, 26, 12, 15);
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
        Double interval = Double.valueOf(0D);
        Double wgtVal = Double.valueOf(0.0D);
        Double pdoVal = Double.valueOf(0.0D);
        // 判断批次下拉框是否为空
        if (MyFrame.this.batch.getSelectedItem() == null)
        {
          JOptionPane.showMessageDialog(null, "请选择批次号", "提示", 1);
          return;
        }
        // 获取批次数据
        batchValue = MyFrame.this.batch.getSelectedItem().toString();

        // 判断检验批次下拉框是否为空
        if ((MyFrame.this.chkBatch.getSelectedItem() == null) || (MyFrame.this.table.getSelectedRows().length < 1))
        {
          JOptionPane.showMessageDialog(null, "请选择检验数据", "提示", 1);
          return;
        }
        //  获取检验批次数据
        chkValue = MyFrame.this.chkBatch.getSelectedItem().toString();

        // 判断检验人下拉框是否为空
        if (MyFrame.this.checker.getSelectedItem() == null)
        {
          JOptionPane.showMessageDialog(null, "请选择检验人", "提示", 1);
          return;
        }
        // 获取检验人
        chker = MyFrame.this.checker.getSelectedItem().toString();

        // 判断重量是否为空
        if ("".equals(MyFrame.this.wgt.getText().trim()))
        {
          JOptionPane.showMessageDialog(null, "平均重量不可为空", "提示", 1);
          return;
        }
        // 获取重量数据
        wgtVal = Double.valueOf(Double.parseDouble(MyFrame.this.wgt.getText()));

        // 判断吸阻是否为空
        if ("".equals(MyFrame.this.pdo.getText().trim()))
        {
          JOptionPane.showMessageDialog(null, "平均吸阻不可为空", "提示", 1);
          return;
        }
        // 获取吸阻数据
        pdoVal = Double.valueOf(Double.parseDouble(MyFrame.this.pdo.getText()));

        // 判断相对温度是否为空
        if ("".equals(inter.getText().trim()))
        {
        	JOptionPane.showMessageDialog(null, "相对湿度不可为空", "提示", 1);
        	return;
        }
        // 获取相对湿度数据
        interval = Double.valueOf(Double.parseDouble(inter.getText()));

        String schemeId = UUID.randomUUID().toString().replaceAll("-", "");

        try
        {
          // 根据选择的检验批次查询检验数据
          List<Map<String, Object>> schemes = db.execQuery("select * from puffscheme where id||name = '" + chkValue + "'", null, "h2");
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
            String selectScheme = "select id from qm_puffscheme where  batch_code = ?";
            Object[] params1 = {batchMap.get(batchValue)};
            List<Map<String, Object>> sList = db.execQuery(selectScheme, params1, db.ORACLE);
            if (sList !=null && sList.size()>0) {
				schemeId = sList.get(0).get("ID").toString();
			}else {

	           String insertScheme = "insert into qm_puffscheme(ID,NAME,CREATETIME,FINISHTIME,AIRFLOW,PUFFVOLUME,PUFFDURATION,INTERVAL,BARB,TEMP,BATCH_CODE,WGT_AVG,PDO_AVG,CHECKER,CHECK_TIME)values(?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?,?,?,?,?,?,?,?,?,sysdate)";

	            params = new Object[] { schemeId, scheme.get("NAME"), scheme.get("CREATETIME").toString().substring(0, 19), scheme.get("FINISHTIME").toString().substring(0, 19), airflow, scheme.get("PUFFVOLUME"),
	              scheme.get("PUFFDURATION"), interval, scheme.get("ATMOSPHERICPRESSURE"), scheme.get("TEMPERATURE"), batchMap.get(batchValue),
	              wgtVal, pdoVal, chker };
	            db.execUpdate(insertScheme, params, "oracle");
			}
          }
          int[] rows = table.getSelectedRows();
          for (int i = 0; i <  rows.length; i++)
          {
            List<?> items = db.execQuery("select * from puffschemeitem where id = " + table.getValueAt(rows[i], 0), null, "h2");
            if ((items != null) && (items.size() > 0))
            {
              Map<String, Object> item = (Map)items.get(0);
              Double puffs = Double.valueOf(0.0D);
              Double numPuffs = 0D;
              Double AVGpuffs = 0D;

              String[] puffValues = new String[0];
              String[] puffStatus = new String[0];
              if (item.get("PUFFVALUES")!=null&&!"null".equals(item.get("PUFFVALUES"))&&item.get("PUFFSTATUS")!=null&&!"null".equals(item.get("PUFFSTATUS"))) {
            	  puffValues = item.get("PUFFVALUES").toString().split(",");
            	  puffStatus = item.get("PUFFSTATUS").toString().split(",");
            	  int m = puffValues.length;
                  for (int k = 0; k < m; k++)
                  {
                    String string = puffValues[k];
                    Double dd = Double.parseDouble(string);
                    numPuffs = numPuffs+dd;
                    if ("1".equals(puffStatus[k])) {
                    	puffs = Double.valueOf(puffs +dd);
					}
                  }
                  AVGpuffs = Double.valueOf(puffs.doubleValue() / (Integer.parseInt(item.get("CIGARETTECOUNT").toString())-Integer.parseInt(item.get("UNBURNEDCIGARETTECOUNT").toString())));
			}
              String insert = "insert into qm_puffschemeitem(ID,NAME,SCHEME_ID,BANKNUM,CIG_LEN,BUTTLEN,CIG_COUNT,NOPUFF_COUNT,BF_WEIGHT,AF_WEIGHT,UNBURNED,COVALUE,PUFFVALUES,SPARED)values(sys_guid(),?,?,?,?,?,?,?,?,?,?,?,?,?)";

              Object[] params1 = { ((Map)item).get("NAME"), schemeId, ((Map)item).get("BANKNUM"), ((Map)item).get("CIGARETTELEN"), ((Map)item).get("BUTTLEN"), ((Map)item).get("CIGARETTECOUNT"), ((Map)item).get("NOPUFFCOUNT"),
                ((Map)item).get("BEFOREWEIGHT"), ((Map)item).get("AFTERWEIGHT"), ((Map)item).get("UNBURNEDCIGARETTECOUNT"), ((Map)item).get("COVALUE"), AVGpuffs, numPuffs };
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
    label_7.setBounds(244, 26, 57, 15);
    panel_1.add(label_7);

    this.pdo = new JTextField();
    this.pdo.setBounds(311, 23, 73, 21);
  /*  this.pdo.addFocusListener(new FocusAdapter()
    {
      public void focusGained(FocusEvent e)
      {
        MyFrame.this.addkey(nInputPnl1);
      }

      public void focusLost(FocusEvent e)
      {
        MyFrame.this.removekey(nInputPnl1);
      }
    });*/
    this.pdo.setColumns(10);
   /* this.pdo.addKeyListener(new KeyAdapter()
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
    });*/
    panel_1.add(this.pdo);

    JLabel label_8 = new JLabel("Pa");
    label_8.setBounds(394, 26, 24, 15);
    panel_1.add(label_8);
    JLabel label_5 = new JLabel("检验员");
    label_5.setBounds(21, 64, 44, 15);
    panel_1.add(label_5);

    this.checker = new JComboBox();
    checker.setBounds(88, 61, 109, 21);
    panel_1.add(checker);

    JLabel label_10 = new JLabel("\u76F8\u5BF9\u6E7F\u5EA6");
    label_10.setBounds(21, 102, 54, 15);
    panel_1.add(label_10);

    inter = new JTextField();
    inter.setBounds(88, 99, 66, 21);
    panel_1.add(inter);
    inter.setColumns(10);

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
    List<Map<String, Object>> batchList = this.db.execQuery("select b.des||'_'||substr(a.batch_code,-3) name,a.batch_code code from QM_BATCH a,md_mat b  where a.category = 2 and a.typ=0 and a.mat_code = b.id and to_char(a.batch_date,'yyyy-mm-dd') = '" + selectDate + "'", null, "oracle");
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
    List<Map<String, Object>> dataList = this.db.execQuery("SELECT id||name name FROM PUFFSCHEME where formatdatetime(createtime,'yyyy-MM-dd')='" + selectDate + "'", null, "h2");
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
      item.setNopuffcount(map.get("NOPUFFCOUNT")==null?null:map.get("NOPUFFCOUNT").toString());
      item.setBeforeweight(map.get("BEFOREWEIGHT")==null?null:map.get("BEFOREWEIGHT").toString());
      item.setAfterweight(map.get("AFTERWEIGHT")==null?null:map.get("AFTERWEIGHT").toString());
      item.setUnbrunedcigarettecount(map.get("UNBURNEDCIGARETTECOUNT")==null?null:map.get("UNBURNEDCIGARETTECOUNT").toString());
      item.setCovalue(map.get("COVALUE")==null?null:map.get("COVALUE").toString());
      item.setSumpuffcount(map.get("SUMPUFFCOUNT")==null?null:map.get("SUMPUFFCOUNT").toString());
      item.setPuffvalues(map.get("PUFFVALUES")==null?null:map.get("PUFFVALUES").toString());
      list.add(item);
    }
    ItemTableModel model = new ItemTableModel(list);
    this.table.setModel(model);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
