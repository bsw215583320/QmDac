package lanbao.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JdbcUtil
{
  private static String USERNAME;
  private static String PASSWORD;
  private static String DRIVER;
  private static String URL;
  private static String OUSERNAME;
  private static String OPASSWORD;
  private static String ODRIVER;
  private static String OURL;
  public static final String ORACLE = "oracle";
  public static final String H2 = "h2";
  private Connection connection;
  private PreparedStatement pstmt;
  private ResultSet resultSet;
  
  static {
    try
    {
      Properties prop = new Properties();
      
    //  String dir = System.getProperty("user.dir");
    //  InputStream inStream = new FileInputStream(new File("/jdbc.properties"));
      InputStream inStream = JdbcUtil.class  
              .getResourceAsStream("/jdbc.properties");
      prop.load(inStream);
      USERNAME = prop.getProperty("jdbc.username");
      PASSWORD = prop.getProperty("jdbc.password");
      DRIVER = prop.getProperty("jdbc.driver");
      URL = prop.getProperty("jdbc.url");
      OUSERNAME = prop.getProperty("oracle.username");
      OPASSWORD = prop.getProperty("oracle.password");
      ODRIVER = prop.getProperty("oracle.driver");
      OURL = prop.getProperty("oracle.url");
    }
    catch (Exception e)
    {
      throw new RuntimeException("读取数据库配置文件异常！", e);
    }
  }
  
  public Connection getConnection(String type)
  {
    try
    {
      if (type.equals("h2"))
      {
        Class.forName(DRIVER);
        this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      }
      else if (type.equals("oracle"))
      {
        Class.forName(ODRIVER);
        this.connection = DriverManager.getConnection(OURL, OUSERNAME, OPASSWORD);
      }
    }
    catch (Exception e)
    {
      throw new RuntimeException("get connection error!", e);
    }
    return this.connection;
  }
  
  public void close(ResultSet rs, PreparedStatement pstmt, Connection con)
  {
    try
    {
      if (rs != null) {
        rs.close();
      }
      if (pstmt != null) {
        pstmt.close();
      }
      if (con != null) {
        con.close();
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public int execUpdate(String sql, Object[] params, String type)
  {
    try
    {
      getConnection(type);
      this.pstmt = this.connection.prepareStatement(sql);
      if (params != null) {
        for (int i = 0; i < params.length; i++) {
          this.pstmt.setObject(i + 1, params[i]);
        }
      }
      return this.pstmt.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw new RuntimeException();
    }
    finally
    {
      close(this.resultSet, this.pstmt, this.connection);
    }
  }
  
  public List<Map<String, Object>> execQuery(String sql, Object[] params, String type)
  {
    try
    {
      getConnection(type);
      this.pstmt = this.connection.prepareStatement(sql);
      if (params != null) {
        for (int i = 0; i < params.length; i++) {
          this.pstmt.setObject(i + 1, params[i]);
        }
      }
      ResultSet rs = this.pstmt.executeQuery();
      
      List<Map<String, Object>> al = new ArrayList();
      
      ResultSetMetaData rsmd = rs.getMetaData();
      
      int columnCount = rsmd.getColumnCount();
      while (rs.next())
      {
        Map<String, Object> hm = new HashMap();
        for (int i = 0; i < columnCount; i++)
        {
          String columnName = rsmd.getColumnName(i + 1);
          
          Object columnValue = rs.getObject(columnName);
          
          hm.put(columnName, columnValue);
        }
        al.add(hm);
      }
      return al;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    finally
    {
      close(this.resultSet, this.pstmt, this.connection);
    }
    return null;
  }
}
