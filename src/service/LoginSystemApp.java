package service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;


public class AppService extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    private JTextArea messageArea;
    private Map<String, String> userDatabase;
    private String currentUser;

    public void UserAuthenticationApp() {
        JFrame frame = new JFrame();
        frame.setTitle("User Authentication App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        userDatabase = new HashMap<>();
        userDatabase.put("user1", "password1");  // Giả lập dữ liệu người dùng.

        JPanel mainPanel = new JPanel(new GridLayout(3, 1));

        JButton loginButton = new JButton("Đăng nhập");
        JButton registerButton = new JButton("Đăng ký");

        mainPanel.add(loginButton);
        mainPanel.add(registerButton);

        add(mainPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
    }
    // Thực hiên logic đăng nhập!
    //Kiểm tra tên người dùng và mật khẩu có khớp với userDatabase không!
    private void handleLogin() {


        if (userDatabase.containsKey(usernameField.getText())) {
            String storedPassword = userDatabase.get(usernameField.getText());
            if (storedPassword.equals(new String(passwordField.getPassword()))) {
                currentUser = usernameField.getText();
                showLoggedInScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu không chính xác.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Kiểm tra lại tên đăng nhập.");
        }
    }
        // Triển khai màn hình đăng nhập
    private void showLoggedInScreen() {

        remove(getContentPane());

        JPanel loggedInPanel = new JPanel(new GridLayout(5, 1));
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setText("Chào mừng " + currentUser + ", bạn có thể thực hiện các công việc sau:\n"
                + "1 - Thay đổi username\n"
                + "2 - Thay đổi email\n"
                + "3 - Thay đổi mật khẩu\n"
                + "4 - Đăng xuất\n"
                + "0 - Thoát chương trình");
        loggedInPanel.add(messageArea);

        getContentPane().add(loggedInPanel);
        revalidate();
        repaint();

        // Implement the options' logic
    }

    private void handleRegister() {
        // Implement registration logic here
        // Add the new user to the userDatabase

        String newUsername = JOptionPane.showInputDialog(this, "Nhập tên đăng nhập:");
        if (userDatabase.containsKey(newUsername)) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại.");
            return;
        }

        String newPassword = JOptionPane.showInputDialog(this, "Nhập mật khẩu:");
        userDatabase.put(newUsername, newPassword);
        JOptionPane.showMessageDialog(this, "Đăng ký thành công.");
    }
}
