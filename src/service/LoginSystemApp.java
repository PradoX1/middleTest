package service;


import entities.Button;
import entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginSystemApp extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPanel panel;
    private JLabel welcomeLabel;
    private String loggedInUser;
    private JButton loginButton;
    private JButton loginButtonAction;
    private JButton signupButton;
    private JButton forgotPasswordButton;
    private JButton logoutButton;
    private JButton changeUsernameButton;
    private JButton changeEmailButton;
    private JButton changePasswordButton;
    private JButton exitButton;
    private JButton backButton;
    private JButton submitButton;
    private final ArrayList<User> userDatabase;
    private boolean isValidEmail(String email) {
        // Sử dụng regex để kiểm tra định dạng email
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        // Kiểm tra độ dài mật khẩu và yêu cầu chứa ít nhất 1 ký tự in hoa và 1 ký tự đặc biệt
        return password.length() >= 7 && password.length() <= 15
                && password.matches(".*[A-Z].*")
                && password.matches(".*[.,-_;].*");
    }
    public LoginSystemApp() {
        setTitle("Login System");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        loginButton = new JButton("Đăng nhập");
        signupButton = new JButton("Đăng ký");
        forgotPasswordButton = new JButton("Quên mật khẩu!");


        loginButton.addActionListener(this);
        forgotPasswordButton.addActionListener(this);
        signupButton.addActionListener(this);

        panel.add(loginButton);
        panel.add(signupButton);
        panel.add(forgotPasswordButton);
        add(panel);
        setVisible(true);
        userDatabase = new ArrayList<>();
    }

    private void showLoginUI() {
        panel.removeAll();
        panel.add(loginButton);
        panel.add(forgotPasswordButton);
        panel.add(signupButton);
        revalidate();
        repaint();
    }

    private void showLoggedInUI() {
        panel.removeAll();
        welcomeLabel = new JLabel("Xin chào " + loggedInUser);
        changeUsernameButton = new JButton("Thay đổi tên người dùng");
        changeEmailButton = new JButton("Thay đổi email người dùng");
        changePasswordButton = new JButton("Thay đổi mật khẩu ");
        logoutButton = new JButton("Đăng xuất");
        exitButton = new JButton("Thoát!");

        changeUsernameButton.addActionListener(this);
        changeEmailButton.addActionListener(this);
        changePasswordButton.addActionListener(this);
        logoutButton.addActionListener(this);
        exitButton.addActionListener(this);

        panel.add(welcomeLabel);
        panel.add(changeUsernameButton);
        panel.add(changeEmailButton);
        panel.add(changePasswordButton);
        panel.add(logoutButton);
        panel.add(exitButton);

        revalidate();
        repaint();
    }
    //Xử lí giao diện đăng nhập
    private void showLogInUI(){
        panel.removeAll();
        JLabel usernameLabel = new JLabel("Tên người dùng:");
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButtonAction = new JButton("Đăng nhập");
        backButton = new JButton("Trở lại");
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButtonAction);
        panel.add(backButton);
        loginButtonAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                User foundUser = null;
                for (User user : userDatabase) {
                    if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                        foundUser = user;
                        break;
                    }
                }
                if (foundUser != null) {
                    loggedInUser = foundUser.getUserName();
                    showLoggedInUI();
                } else {
                    JOptionPane.showMessageDialog(panel, "Tên người dùng hoặc mật khẩu không hợp lệ."+"\n"+"Vui lòng nhập lại!");
                }
            }
        });
        backButton.addActionListener(e -> showLoginUI());
        revalidate();
        repaint();
    }
    // Xử lí giao diện nút Quên mật khẩu
    private void showForgotPasswordUI() {
        panel.removeAll();
        JLabel emailLabel = new JLabel("Nhập email của bạn:");
        emailField = new JTextField();
        submitButton = new JButton("Đồng ý");
        backButton = new JButton("Trở lại");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputEmail = emailField.getText();
                User foundUser = null;
                for (User user : userDatabase) {
                    if (user.getEmail().equals(inputEmail)) {
                        foundUser = user;
                        break;
                    }
                }

                if (foundUser != null) {
                  showChangePasswordUI(inputEmail);
                } else {
                    JOptionPane.showMessageDialog(panel, "Không tìm thấy email!"+"\n"+"Vui lòng nhập lại!");
                }
            }
        });

        backButton.addActionListener(e -> showLoginUI());

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(submitButton);
        panel.add(backButton);

        revalidate();
        repaint();
    }
    //Xử lí giao diện đăng ký!!!
    private void showSignUpUI(){
        panel.removeAll();
        JLabel usernameLabel = new JLabel("Tên người dùng mới:");
         usernameField = new JTextField();
        JLabel emailLabel = new JLabel("Email người dùng mới:");
        emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Mật khẩu mới");
        passwordField = new JPasswordField();
        JButton signUpButton = new JButton("Đăng ký");
        backButton = new JButton("Trờ lại");

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = usernameField.getText();
                String newEmail = emailField.getText();
                String newPassword = new String(passwordField.getPassword());

                boolean usernameExists = false;
                for (User user : userDatabase) {
                    if (user.getUserName().equals(newUsername)) {
                        usernameExists = true;
                        break;
                    }
                }

                boolean emailExists = false;
                for (User user : userDatabase) {
                    if (user.getEmail().equals(newEmail)) {
                        emailExists = true;
                        break;
                    }
                }

                boolean validEmailFormat = isValidEmail(newEmail);
                boolean validPassword = isValidPassword(newPassword);

                if (usernameExists) {
                    JOptionPane.showMessageDialog(panel, "Tên người dùng đã tồn tại!"+"\n"+"Vui lòng nhập lại!");
                } else if (emailExists) {
                    JOptionPane.showMessageDialog(panel, "Email người dùng đã tồn tại!"+"\n"+"Vui lòng nhập lại!");
                } else if (!validEmailFormat) {
                    JOptionPane.showMessageDialog(panel, "Vui lòng nhập đúng định dạng email!");
                } else if (!validPassword) {
                    JOptionPane.showMessageDialog(panel, "Vui lòng nhập đúng định dạng mật khẩu!");
                } else {
                    userDatabase.add(new User(newUsername, newEmail, newPassword));
                    JOptionPane.showMessageDialog(panel, "Tạo tài khoản mới thành công."+"\n"+"Mời bạn đăng nhập lại!");
                    showLoginUI();
                }
            }
        });

        backButton.addActionListener(e -> showLoginUI());

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signUpButton);
        panel.add(backButton);

        revalidate();
        repaint();
    }
    //Xử lí giao diện đổi Username
    private void showChangUsernameUI(){
        panel.removeAll();
        JLabel usernameLabel = new JLabel("Nhập tên người dùng mới của bạn:");
        usernameField = new JTextField();
        submitButton = new JButton("Đồng ý");
        backButton = new JButton("Trở lại");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean usernameExists = false;
                for (User user : userDatabase) {
                    if (user.getUserName().equals(usernameField.getText())) {
                        usernameExists = true;
                        break;
                    }
                }

                if (usernameExists) {
                    JOptionPane.showMessageDialog(panel, "Tên người dùng đã tồn tại."+"\n"+"Vui lòng nhập lại!");
                } else {
                    for (User user : userDatabase) {
                        if (user.getUserName().equals(loggedInUser)) {
                            user.setUserName(usernameField.getText());
                            loggedInUser = usernameField.getText();
                            JOptionPane.showMessageDialog(panel, "Đổi tên người dùng thành công."+"\n"+"Mời bạn đăng nhập lại!");
                            break;
                        }
                    }
                    showLoginUI();
                }
            }
        });
        backButton.addActionListener(e -> showLoggedInUI());
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(submitButton);
        panel.add(backButton);
        revalidate();
        repaint();
    }
//    Xử lí giao diện thay đổi email
    private void showChangeEmailUI(){
        panel.removeAll();
        JLabel emailLabel = new JLabel("Nhập tên email mới của bạn:");
        emailField = new JTextField();
        submitButton = new JButton("Đồng ý");
        backButton = new JButton("Trở lại");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean emailExists = false;
                for (User user : userDatabase) {
                    if (user.getEmail().equals(emailField.getText())) {
                        emailExists = true;
                        break;
                    }
                }

                if (emailExists) {
                    JOptionPane.showMessageDialog(panel, "Email đã tồn tại."+"\n"+"Vui lòng nhập lại!");
                } else if (!isValidEmail(emailField.getText())) {
                    JOptionPane.showMessageDialog(panel, "Vui lòng nhập email hợp lệ.");
                } else {
                    for (User user : userDatabase) {
                        if (user.getUserName().equals(loggedInUser)) {
                            user.setEmail(emailField.getText());
                            JOptionPane.showMessageDialog(panel, "Đổi email thành công."+"\n"+"Mời bạn đăng nhập lại!");
                            break;
                        }
                    }
                    showLoginUI();
                }
            }
        });
        backButton.addActionListener(e -> showLoggedInUI());
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(submitButton);
        panel.add(backButton);
        revalidate();
        repaint();
    }
    private void showChangePasswordUI(String inputEmail){
        panel.removeAll();
        JLabel newPasswordLabel = new JLabel("Nhập  mật khẩu mới của bạn:");
        passwordField = new JPasswordField();
        submitButton = new JButton("Đồng ý");
        backButton = new JButton("Trở lại");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean validPassword = isValidPassword(String.valueOf(passwordField.getPassword()));
                if (!validPassword) {
                    JOptionPane.showMessageDialog(panel, "Vui lòng nhâp mật khẩu hợp lệ."+"\n"+"Độ dài từ 7 đến 15 ký tự.\n" +
                            "Chứa ít nhất một ký tự viết hoa.\n" +
                            "Chứa ít nhất một ký tự đặc biệt (.,-_;).");
                } else {
                    for (User user : userDatabase) {
                        if (user.getEmail().equals(inputEmail)) {
                            user.setPassword(String.valueOf(passwordField));
                            JOptionPane.showMessageDialog(panel, "Thay đổi mật khẩu thành công."+"\n"+"Mời bạn đăng nhập lại!");
                            break;
                        } else if(user.getUserName().equals(loggedInUser)){
                            user.setPassword(String.valueOf(passwordField));
                            JOptionPane.showMessageDialog(panel, "Thay đổi mật khẩu thành công."+"\n"+"Mời bạn đăng nhập lại!");
                            break;
                        }
                    }
                  showLoginUI();
                }
            }
        });
        backButton.addActionListener(e -> showLoggedInUI());
        panel.add(newPasswordLabel);
        panel.add(passwordField);
        panel.add(submitButton);
        panel.add(backButton);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String inputEmail="";
        if (e.getSource() == loginButton) {
            showLogInUI();
        }
        if (e.getSource() == forgotPasswordButton) {
            showForgotPasswordUI();

        }
        if (e.getSource() == signupButton) {
            showSignUpUI();
        }
        if (e.getSource() == changeUsernameButton) {
            showChangUsernameUI();
        }
        if (e.getSource() == changeEmailButton) {
            showChangeEmailUI();
        }
        if (e.getSource() == changePasswordButton) {
           showChangePasswordUI(inputEmail);
        }
        if (e.getSource() == logoutButton) {
            loggedInUser = null;
            showLoginUI();
        }
        if (e.getSource() == exitButton) {
            System.exit(0);
        }

    }

}
