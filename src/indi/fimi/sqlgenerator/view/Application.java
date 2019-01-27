package indi.fimi.sqlgenerator.view;

import indi.fimi.sqlgenerator.core.SqlGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

public class Application {

    private JTextArea colListText;
    private JTextField primaryKeyText;
    private JTextArea resultText;
    private JTextField tableNameText;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sql Generator");
        // Setting the width and height of frame
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        // add panel
        frame.add(panel);
        Application app = new Application();
        app.placeComponents(panel);
        // 设置界面可见
        frame.setVisible(true);
    }

    private void execute() {
        if (0 == colListText.getText().length() || 0 == primaryKeyText.getText().length() || 0 == tableNameText.getText().length()) {
            resultText.setText("col list or primaryKey or tableName is null!");
        } else {
            List<String> colList = Arrays.asList(colListText.getText().split(","));
            if (!colList.contains(primaryKeyText.getText())) {
                resultText.setText("primaryKey is not in colList");
            } else {
                SqlGenerator sqlGenerator = new SqlGenerator();
                sqlGenerator.setPrimaryKey(primaryKeyText.getText());
                sqlGenerator.setColNameList(colList);
                sqlGenerator.setTableName(tableNameText.getText());
                resultText.setText(sqlGenerator.getInsertSql() + "\n\n" + sqlGenerator.getUpdateSql() + "\n\n" + sqlGenerator.getSelectSql() + "\n\n" + sqlGenerator.getSelectByPrimaryKeySql() + "\n\n" + sqlGenerator.getDeleteSql());
            }
        }
    }

    private void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 JLabel
        JLabel colListLabel = new JLabel("Cols:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        colListLabel.setBounds(10, 20, 80, 25);
        panel.add(colListLabel);

        JLabel tipsLabel = new JLabel("(split with ',')");
        tipsLabel.setBounds(10, 50, 80, 25);
        panel.add(tipsLabel);

        /*
         * input colList(split with ",")
         */
        colListText = new JTextArea();
        colListText.setBounds(100, 20, 300, 100);
        panel.add(colListText);

        JLabel tableNameLabel = new JLabel("Table Name:");
        tableNameLabel.setBounds(10, 130, 80, 25);
        panel.add(tableNameLabel);

        /*
         * input tableName
         */
        tableNameText = new JTextField();
        tableNameText.setBounds(100, 130, 300, 25);
        panel.add(tableNameText);

        JLabel primaryKeyLabel = new JLabel("PrimaryKey:");
        primaryKeyLabel.setBounds(10, 160, 80, 25);
        panel.add(primaryKeyLabel);

        /*
         * input primaryKey
         */
        primaryKeyText = new JTextField();
        primaryKeyText.setBounds(100, 160, 300, 25);
        panel.add(primaryKeyText);
        primaryKeyText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    execute();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        // result'textArea
        JLabel passwordLabel = new JLabel("Result:");
        passwordLabel.setBounds(10, 190, 80, 25);
        panel.add(passwordLabel);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(100, 190, 300, 200);
        panel.add(scrollPane);

        resultText = new JTextArea();
        resultText.setEditable(false);
        resultText.setLineWrap(true);
        resultText.setBounds(100, 190, 300, 200);
        scrollPane.setViewportView(resultText);

        // 创建登录按钮
        JButton loginButton = new JButton("Execute");
        loginButton.setBounds(100, 400, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                execute();
            }
        });
        panel.add(loginButton);
    }
}
