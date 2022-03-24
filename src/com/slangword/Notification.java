package com.slangword;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Notification extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel noTi;

    public Notification(int type) {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Thông báo");
        getRootPane().setDefaultButton(buttonOK);

        Vector<String> notify = new Vector<>();
        notify.add("Chưa nhập từ");
        notify.add("Chưa nhập nghĩa");
        notify.add("Thêm thành công");
        notify.add("Sửa thành công");
        notify.add("Xóa thành công");

        noTi.setText(notify.get(type));
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
