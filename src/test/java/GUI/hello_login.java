package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class hello_login {
    public hello_login() {
        JPanel p = new JPanel();
        JLabel types = new JLabel("图书借阅系统");
        JLabel account = new JLabel("账号: ");
        JLabel key = new JLabel("密码: ");

        JTextField in_account = new JTextField(17);
        JTextField in_key = new JTextField(17);

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
                JOptionPane.showMessageDialog(null, "账号或密码错误", "登陆失败。。", JOptionPane.WARNING_MESSAGE);
            }
        }
        );

        JFrame f = new JFrame();
        GridLayout fl = new GridLayout();
        f.setLayout(fl);
        f.getContentPane().add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(350, 250);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        hello_login ck = new hello_login();
        boolean ok = ck.check("1", "1");
        System.out.println(ok);
    }

    public boolean check(String in_acc, String in_key) {
        String path = "/Users/gongzhaorui/Documents/GitHub/Library-management-system/src/test/java/GUI/account.txt";
        File file = new File(path);
        String account = "", key = "";
        try {
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String s = null;
            int i = 0;
            while((s = br.readLine())!=null) { //使用readLine方法，一次读一行
                account = s;
                s = br.readLine();
                key = s;
                if(account.equals(in_acc) && key.equals(in_key)) {
                    return true;
                }
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
