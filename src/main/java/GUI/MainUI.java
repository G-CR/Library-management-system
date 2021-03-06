package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class MainUI {
    JTable table;
    JScrollPane sc;
    JPopupMenu jpm;
    JMenuItem item0 = new JMenuItem("借阅此书");
    JMenuItem item1 = new JMenuItem("续租此书");
    JMenuItem item2 = new JMenuItem("归还此书");
    int rows;
    DefaultTableModel dtm2;

    public MainUI(String account, String pwd) {
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


        con_sql conSql = new con_sql();
        Connection conn = conSql.getConn();

        // 显示对话框下面的书籍信息
        String sql_all = "CALL sql_all()";
        String[] columnNames = {"ISBN编号","书名","作者","单价","出版社","出版日期","库存","书籍种类"};
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(columnNames); // 给书籍信息加上基础信息
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql_all); // 执行sql语句
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

        table = new JTable(dtm) {
            public boolean isCellEditable(int row, int column) {
                return false;
            } //表格不允许被编辑
        };
        sc = new JScrollPane(table);

        // 添加查找按钮监听器
        find.addActionListener( e -> {
            // 添加鼠标右键弹出借阅按钮
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e){
                    if (e.getButton() == MouseEvent.BUTTON3){
                        //在table显示
                        jpm = new JPopupMenu();
                        //表格 的rowAtPoint方法返回坐标所在的行号，参数为坐标类型，
                        int i = table.rowAtPoint(e.getPoint());
                        jpm.add(item0);
                        jpm.show(table, e.getX(), e.getY());
                    }
                }
            });

            String find_type = (String) type.getSelectedItem();
            String input = in_type.getText();
            int low = Integer.valueOf(price1.getText());
            int high = Integer.valueOf(price2.getText());

            assert find_type != null;
            if(find_type.equals("书籍名称")) find_type = "Book_name";
            else if(find_type.equals("ISBN编号")) find_type = "ISBN";
            else if(find_type.equals("书籍种类")) find_type = "Type";
            else if(find_type.equals("出版社")) find_type = "Press";
            else if(find_type.equals("作者")) find_type = "Author";

            String sql_search = "SELECT ISBN, Book_name, Author, Price, Press, Date_of_publication, Inventory, ty.type_name FROM Book, Types_of_books ty WHERE ty.type_num = Book.Type AND (Price BETWEEN " + low + " AND " + high+ ") AND " + find_type + " LIKE '%" + input + "%' ORDER BY Price";
            DefaultTableModel dtm1 = new DefaultTableModel();
            dtm1.setColumnIdentifiers(columnNames); // 给书籍信息加上基础信息
            PreparedStatement pstmt1;
            try {
                pstmt1 = conn.prepareStatement(sql_search);
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

        // 添加查询借阅情况按钮监听器
        show.addActionListener(e -> {
            // 添加鼠标右键弹出借阅按钮
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e){
                    if (e.getButton() == MouseEvent.BUTTON3){
                        //在table显示
                        jpm = new JPopupMenu();
                        //表格 的rowAtPoint方法返回坐标所在的行号，参数为坐标类型，
                        int i = table.rowAtPoint(e.getPoint());
                        jpm.add(item1);
                        jpm.add(item2);
                        jpm.show(table, e.getX(), e.getY());
                    }
                }
            });

            String sql_borrow = "CALL sql_borrow('" + account + "','" + pwd + "')";
            dtm2 = new DefaultTableModel();
            String[] colBorrow = {"书名", "ISBN编号", "借阅日期", "借阅时限"};
            dtm2.setColumnIdentifiers(colBorrow); // 借阅信息表头
            PreparedStatement pstmt1;
            try {
                pstmt1 = conn.prepareStatement(sql_borrow);
                ResultSet rs = pstmt1.executeQuery();
                while(rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("bo.Book_name"));
                    v.add(rs.getString("bo.ISBN"));
                    v.add(rs.getString("b.Borrowing_date"));
                    String date = rs.getString("b.Borrowing_date");
                    Long day = getDay(date);
                    String y_days = String.valueOf(day-30);
                    String s_days = String.valueOf(30-day);
                    if(day > 30) v.add("逾期"+y_days+"天");
                    else v.add("剩余"+s_days+"天");
                    dtm2.addRow(v);
                }
            }
            catch (SQLException g) {
                g.printStackTrace();
            }
            table.setModel(dtm2);
        });

        /*-------------------------------------------------------------------------*/

        // 添加鼠标右键弹出借阅按钮
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if (e.getButton() == MouseEvent.BUTTON3){
                    //在table显示
                    jpm = new JPopupMenu();
                    //表格 的rowAtPoint方法返回坐标所在的行号，参数为坐标类型，
                    rows = table.rowAtPoint(e.getPoint());
                    jpm.add(item0);
                    jpm.show(table, e.getX(), e.getY());
                }
            }
        });

        // 借阅书籍监听器
        item0.addActionListener(e -> {
            String ISBN = (String) dtm.getValueAt(rows, 0);
            System.out.println(rows);
            String id_num = ""; // 身份证号
            String date_now = ""; // 当前日期
            String sql_find = "CALL sql_find('" + account + "', '" + pwd + "', '" + ISBN + "')"; // 查询是否借阅过这本书
            String sql_date = "CALL sql_date('" + account + "', '" + pwd + "')"; // 查询书籍的ISBN号对应的借阅日期，检测是否有书籍逾期
            String sql_num = "CALL sql_num('" + account + "', '" + pwd + "')"; // 查询这个人借了多少本书


            PreparedStatement pre = null;
            try {
                // 检测是否可以借书 (检测是否 有逾期书籍未归还 or 借阅书籍超过十本)
                pre = conn.prepareStatement(sql_date);
                ResultSet res = pre.executeQuery();
                boolean ok1 = true, ok2 = true;
                while(res.next()) {
                    if(getDay(res.getString("Borrowing_date")) > 30) { // 如果有借阅的书籍超过30天就是逾期了 OK不通过
                        ok1 = false;
                        System.out.println(res.getString("Borrowing_date"));
                        break;
                    }
                }
                if(ok1 == false) { // 有图书逾期的时候的借阅失败弹窗
                    JOptionPane.showMessageDialog(null,"您有图书已逾期,请先处理再进行图书借阅", "借阅失败", JOptionPane.PLAIN_MESSAGE);
                }
                if(ok1 == true) {
                    pre = conn.prepareStatement(sql_num);
                    res = pre.executeQuery();
                    if(res.next()) if(res.getInt("COUNT(*)") >= 10) {
                        ok2 = false;
                        JOptionPane.showMessageDialog(null,"您借阅的图书超过10本,请先归还再进行借阅", "借阅失败", JOptionPane.PLAIN_MESSAGE);
                    }
                }

                if(ok1 == true && ok2 == true) {
                    pre = conn.prepareStatement(sql_find); // 执行sql语句 搞到需要借阅的书名好存入借阅信息表里面
                    res = pre.executeQuery();

                    if(res.next()) if(res.getInt("COUNT(*)") > 0) {
                        JOptionPane.showMessageDialog(null,"您已经借阅过这本书,请选择其他书籍", "借阅失败", JOptionPane.PLAIN_MESSAGE);
                    }
                    else {
                        // 获取这个用户的身份证号 用于添加借阅信息
                        String sql_id = "CALL sql_id('" + account + "', '" + pwd + "')";
                        pre = conn.prepareStatement(sql_id);
                        res = pre.executeQuery();
                        if(res.next()) id_num = res.getString("ID_num");

                        // 获取当前日期
                        date_now = dateNow();
                        System.out.println("ISBN = " + ISBN);
                        String sql_change = "CALL sql_change('" + id_num + "','" + ISBN + "','" + date_now + "')"; // 借书之后 借阅列表加上借阅信息

                        pre = conn.prepareStatement(sql_change);
                        pre.execute();

                        table.updateUI();
                        JOptionPane.showMessageDialog(null,"请在一个月以内归还或者续租", "借阅成功", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            } catch (SQLException | ParseException throwables) {
                throwables.printStackTrace();
            }
        });

        // 续租书籍监听器
        item1.addActionListener(e -> {
            String ISBN = (String) dtm2.getValueAt(rows,1);
            String ID_num = (String) dtm2.getValueAt(rows, 0);
//            System.out.println(ISBN);
            if(getDay((String) dtm2.getValueAt(rows, 2)) > 30) {
                JOptionPane.showMessageDialog(null,"该书已经逾期,请先交罚款", "续租提示", JOptionPane.PLAIN_MESSAGE);
            }
            String date_now = "";
            try {
                date_now = dateNow();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            String sql_conti = "CALL sql_conti('" + date_now + "', '" + ISBN + "', '" + account + "', '" + pwd + "')";
            try {
                PreparedStatement pre = conn.prepareStatement(sql_conti);
                pre.execute();
                JOptionPane.showMessageDialog(null,"续租成功！请刷新界面", "续租提示", JOptionPane.PLAIN_MESSAGE);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        // 退还书籍监听器
        item2.addActionListener(e -> {
            if(getDay((String) dtm2.getValueAt(rows, 2)) > 30) {
                JOptionPane.showMessageDialog(null,"该书已经逾期,请先交罚款", "还书提示", JOptionPane.PLAIN_MESSAGE);
            }

            String sql_id = "CALL sql_id('" + account + "', '" + pwd + "')";

            String id_num = "";
            try {
                PreparedStatement pre = conn.prepareStatement(sql_id);
                ResultSet res = pre.executeQuery();
                if(res.next()) id_num = res.getString("ID_num");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            String sql_return = "CALL sql_return('" + id_num + "', '" + dtm2.getValueAt(rows,1) + "')";
            try {
                PreparedStatement pre = conn.prepareStatement(sql_return);
                pre.execute();

                JOptionPane.showMessageDialog(null,"还书成功！请刷新界面", "还书提示", JOptionPane.PLAIN_MESSAGE);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        p.add(title); p.add(p1); p.add(p2); p.add(p3); p.add(sc);
        GridLayout fl = new GridLayout();
        f.setLayout(fl);
        f.getContentPane().add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(450, 450);
        f.setVisible(true);
    }

    public Long getDay(String date) { // 返回系统日期与借阅日期之间的差值
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Long days = null;
        try {
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));//现在系统当前时间
            Date pastTime = dateFormat.parse(date);//过去时间
            long diff = currentTime.getTime() - pastTime.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public String dateNow() throws ParseException {
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime = format.parse(format.format(new Date()));//现在系统当前时间
        return format.format(currentTime);
    }
}
