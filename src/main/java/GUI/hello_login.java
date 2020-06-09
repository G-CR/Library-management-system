package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class hello_login {
    public hello_login() {
        JFrame f = new JFrame();
        JPanel p = new JPanel();
        JLabel types = new JLabel("图书借阅系统");
        JLabel account = new JLabel("账号: ");
        JLabel key = new JLabel("密码: ");

        JTextField in_account = new JTextField(17);
        JPasswordField in_key = new JPasswordField(17);

        JButton register = new JButton("注册");
        JButton login = new JButton("登陆");

        JPanel p1 = new JPanel();
        p1.add(account); p1.add(in_account);
        JPanel p2 = new JPanel();
        p2.add(key); p2.add(in_key);
        JPanel p3 = new JPanel();
        p3.add(register); p3.add(login);

        p.add(types); p.add(p1); p.add(p2);p.add(p3);

        login.addActionListener((ActionListener) e-> {
            String acc = (String) in_account.getText();
            String keys = (String) in_key.getText();
            if(check(acc, keys) == true) {
                JOptionPane.showMessageDialog(null,"欢迎使用图书借阅系统", "登陆成功!", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "账号或密码错误", "登陆失败..", JOptionPane.WARNING_MESSAGE);
            }
        }
        );

        register.addActionListener((ActionListener) e-> {
            String acc = (String) in_account.getText();
            String keys =  String.valueOf(in_key.getPassword()) ;
            if(check(acc, keys) == true) {
                JOptionPane.showMessageDialog(null,"账号已经存在,请直接登陆", "注册失败..", JOptionPane.WARNING_MESSAGE);
            }
            else {
                hello_register hr = new hello_register();
                f.dispose();
            }
        }
        );

        GridLayout fl = new GridLayout();
        f.setLayout(fl);
        f.getContentPane().add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(350, 250);
        f.setVisible(true);
    }

    public boolean check(String in_acc, String in_key) { // 检查输入的账号密码是否存在
        con_sql conSql = new con_sql();
        Connection conn = conSql.getConn();
        String name = null;
        String sql = "SELECT reader_name FROM Reader WHERE account = '" + in_acc + "' AND pwd = '"+ in_key + "'";
        PreparedStatement pstmt;
        String s = null;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            if(rs.next()) {
                s = rs.getString(col);
            }
//            System.out.println(col);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if(s == null) return false;
        return true;
    }
}