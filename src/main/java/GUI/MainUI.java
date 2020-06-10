package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MainUI {
    JTable table;
    JScrollPane sc;
    public MainUI() {
        JFrame f = new JFrame();
        JPanel p = new JPanel();

        JLabel title = new JLabel("图书借阅系统");

        // 查找书籍的板板
        JPanel p1 = new JPanel();
        JLabel tit1 = new JLabel("查找书籍 ");
        String[] s = {"书籍名称","ISBN编号","作者","出版社","书籍种类"};
        JComboBox type = new JComboBox(s); // 下拉框
        JTextField in_type = new JTextField(15);
        p1.add(tit1); p1.add(type); p1.add(in_type);

        JPanel p2 = new JPanel();
        JLabel tit2 = new JLabel("价格范围 ");
        JTextField price1 = new JTextField(7);
        JLabel space1 = new JLabel(" ------- ");
        JTextField price2 = new JTextField(7);
        JLabel space2 = new JLabel("         ");
        price1.setText("0"); price2.setText("999999"); // 设置默认值
        p2.add(tit2); p2.add(price1); p2.add(space1); p2.add(price2);p2.add(space2);

        JPanel p3 = new JPanel();
        JButton find = new JButton("查询");
        JButton show = new JButton("已借阅书籍");
        p3.add(find); p3.add(show);

        // 显示对话框下面的书籍信息
        con_sql conSql = new con_sql();
        Connection conn = conSql.getConn();
        String sql = "SELECT ISBN, Book_name, Author, Price, Press, Date_of_publication, Inventory, ty.type_name FROM Book, Types_of_books ty WHERE ty.type_num = Book.Type";
        String[] columnNames = {"ISBN编号","书名","作者","单价","出版社","出版日期","库存","书籍种类"};
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(columnNames); // 给书籍信息加上基础信息

        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("ISBN"));
                v.add(rs.getString("Book_name"));
                v.add(rs.getString("Author"));
                v.add(rs.getString("Price"));
                v.add(rs.getString("Press"));
                v.add(rs.getString("Date_of_publication"));
                v.add(rs.getString("Inventory"));
                v.add(rs.getString("ty.type_name"));

                dtm.addRow(v);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        table = new JTable(dtm);
        sc = new JScrollPane(table);

        // 添加查找按钮监听器
        find.addActionListener( e -> {
            String find_type = (String) type.getSelectedItem();
            String input = in_type.getText();
            int low = Integer.valueOf(price1.getText());
            int high = Integer.valueOf(price2.getText());

            if(find_type.equals("书籍名称")) find_type = "Book_name";
            else if(find_type.equals("ISBN编号")) find_type = "ISBN";
            else if(find_type.equals("书籍种类")) find_type = "Type";
            else if(find_type.equals("出版社")) find_type = "Press";
            else if(find_type.equals("作者")) find_type = "Author";


            String sql_ = "SELECT ISBN, Book_name, Author, Price, Press, Date_of_publication, Inventory, ty.type_name FROM Book, Types_of_books ty WHERE ty.type_num = Book.Type AND (Price BETWEEN " + low + " AND " + high+ ") AND " + find_type + " LIKE '%" + input + "%'";
            DefaultTableModel dtm1 = new DefaultTableModel();
            dtm1.setColumnIdentifiers(columnNames); // 给书籍信息加上基础信息
            PreparedStatement pstmt1;
            try {
                pstmt1 = (PreparedStatement) conn.prepareStatement(sql_);
                ResultSet rs = pstmt1.executeQuery();
                while(rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("ISBN"));
                    v.add(rs.getString("Book_name"));
                    v.add(rs.getString("Author"));
                    v.add(rs.getString("Price"));
                    v.add(rs.getString("Press"));
                    v.add(rs.getString("Date_of_publication"));
                    v.add(rs.getString("Inventory"));
                    v.add(rs.getString("ty.type_name"));
                    dtm1.addRow(v);

                    // 如果输入框里面没有任何输入那么就显示全部书籍信息
                    if(input.equals("")) {
                        table.setModel(dtm);
                    }
                }
            }
            catch (SQLException g) {
                g.printStackTrace();
            }
            table.setModel(dtm1);
        });
        /*-------------------------------------------------------------------------*/
        
        p.add(title); p.add(p1); p.add(p2); p.add(p3); p.add(sc);

        GridLayout fl = new GridLayout();
        f.setLayout(fl);
        f.getContentPane().add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(450, 450);
        f.setVisible(true);
    }
}
