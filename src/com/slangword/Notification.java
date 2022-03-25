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
        notify.add("Chưa nhập từ");      //0
        notify.add("Chưa nhập nghĩa");   //1
        notify.add("Thêm thành công");   //2
        notify.add("Sửa thành công");    //3
        notify.add("Xóa thành công");    //4
        notify.add("Đã reset từ điển");  //5

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
