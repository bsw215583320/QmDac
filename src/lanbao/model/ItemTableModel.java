package lanbao.model;

import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ItemTableModel
  implements TableModel
{
  private List<PuffSchemeItem> itemList;
  
  public ItemTableModel(List<PuffSchemeItem> itemList)
  {
    this.itemList = itemList;
  }
  
  public int getRowCount()
  {
    return this.itemList.size();
  }
  
  public int getColumnCount()
  {
    return 5;
  }
  
  public String getColumnName(int columnIndex)
  {
    if (columnIndex == 0) {
      return "ID";
    }
    if (columnIndex == 1) {
      return "序号";
    }
    if (columnIndex == 2) {
      return "牌号";
    }
    if (columnIndex == 3) {
      return "烟支长度";
    }
    if (columnIndex == 4) {
      return "烟蒂长度";
    }
    return null;
  }
  
  public Class<?> getColumnClass(int columnIndex)
  {
    return getValueAt(0, columnIndex).getClass();
  }
  
  public boolean isCellEditable(int rowIndex, int columnIndex)
  {
    return false;
  }
  
  public Object getValueAt(int rowIndex, int columnIndex)
  {
    PuffSchemeItem item = (PuffSchemeItem)this.itemList.get(rowIndex);
    if (columnIndex == 0) {
      return item.getId();
    }
    if (columnIndex == 1) {
      return item.getBankNum();
    }
    if (columnIndex == 2) {
      return item.getName();
    }
    if (columnIndex == 3) {
      return item.getCigarettelen();
    }
    if (columnIndex == 4) {
      return item.getButtlen();
    }
    return null;
  }
  
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
  
  public void addTableModelListener(TableModelListener l) {}
  
  public void removeTableModelListener(TableModelListener l) {}
}
