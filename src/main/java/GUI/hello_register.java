package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class hello_register {
    public hello_register() {
        JFrame f = new JFrame();
        JPanel p = new JPanel();
        JLabel type = new JLabel("注册图书借阅系统账号");

        JPanel p1 = new JPanel();
        JLabel ID = new JLabel("身份证号");
        JTextField in_ID = new JTextField(17);
        p1.add(ID); p1.add(in_ID);

        JPanel p2 = new JPanel();
        JLabel Name = new JLabel("姓名      ");
        JTextField in_Name = new JTextField(17);
        p2.add(Name); p2.add(in_Name);

        JPanel p3 = new JPanel();
        JLabel Sex = new JLabel("性别      ");
        JLabel space = new JLabel("                                    ");
        String[] sexs = {"男", "女"};
        JComboBox in_Sex = new JComboBox(sexs);
        p3.add(Sex); p3.add(in_Sex);p3.add(space);

        JPanel p4 = new JPanel();
        JLabel Phone = new JLabel("电话号码");
        JTextField in_Phone= new JTextField(17);
        p4.add(Phone); p4.add(in_Phone);

        JPanel p5 = new JPanel();
        JLabel Account = new JLabel("账号      ");
        JTextField in_Account = new JTextField(17);
        p5.add(Account); p5.add(in_Account);

        JPanel p6 = new JPanel();
        JLabel Pwd = new JLabel("密码      ");
        JTextField in_Pwd = new JTextField(17);
        p6.add(Pwd); p6.add(in_Pwd);

        JButton regi = new JButton("确认注册");
        regi.addActionListener((ActionListener) e-> {
                String Fid = (String) in_ID.getText();
                String Fname = (String) in_Name.getText();
                String Fsex = (String) in_Sex.getSelectedItem();
                String Fphone = (String) in_Phone.getText();
                String Faccount = (String) in_Account.getText();
                String Fpwd = (String) in_Pwd.getText();

                con_sql conSql = new con_sql();
                Connection conn = conSql.getConn();
                String sql = "insert into Reader (ID_num, reader_name, sex, phone_num, account, pwd) values(?,?,?,?,?,?)";

                PreparedStatement pstmt = null;
                try {
                    pstmt = (PreparedStatement) conn.prepareStatement(sql);
                    pstmt.setString(1, Fid);
                    pstmt.setString(2, Fname);
                    pstmt.setString(3, Fsex);
                    pstmt.setString(4, Fphone);
                    pstmt.setString(5, Faccount);
                    pstmt.setString(6, Fpwd);
                    JOptionPane.showMessageDialog(null, "请登陆使用本系统", "注册成功!", JOptionPane.PLAIN_MESSAGE);
                    f.dispose();
                    hello_login hl = new hello_login();
                }
                catch (SQLException g) {
                    g.printStackTrace();
                }

                try {
                    pstmt.execute(); // 提交数据库
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
        }
        );

        p.add(type); p.add(p1); p.add(p2); p.add(p3);
        p.add(p4); p.add(p5); p.add(p6); p.add(regi);

        GridLayout fl = new GridLayout();
        f.setLayout(fl);
        f.getContentPane().add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(350, 350);
        f.setVisible(true);
    }
}
