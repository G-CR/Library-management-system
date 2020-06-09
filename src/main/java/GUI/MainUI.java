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
        String[] s = {}

        p.add(title);

        GridLayout fl = new GridLayout();
        f.setLayout(fl);
        f.getContentPane().add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(350, 350);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        new MainUI();
    }
}
