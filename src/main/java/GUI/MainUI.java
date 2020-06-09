package GUI;

import javax.swing.*;
import java.awt.*;

public class MainUI {
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
        p3.add(find);p3.add(show);

        p.add(title); p.add(p1); p.add(p2); p.add(p3);

        GridLayout fl = new GridLayout();
        f.setLayout(fl);
        f.getContentPane().add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(450, 350);
        f.setVisible(true);
    }
}
