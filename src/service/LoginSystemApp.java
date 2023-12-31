package service;


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
    private final JPanel panel;
    private String loggedInUser;
    private String emailLoggedInUser;
    private String passWordLoggedInUser;
    private final JButton loginButton;
    private final JButton signupButton;
    private final JButton forgotPasswordButton;
    private JButton logoutButton;
    private JButton changeUsernameButton;
    private JButton changeEmailButton;
    private JButton changePasswordButton;
    private final JButton exitButton;
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

    //Xử lí giao diện màn hình bắt đầu
    public LoginSystemApp() {
        setTitle("Login System");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        JLabel hello = new JLabel("Chào mừng bạn đến với ứng dụng của chúng tôi!!!");
        loginButton = new JButton("Đăng nhập");
        signupButton = new JButton("Đăng ký");
        forgotPasswordButton = new JButton("Quên mật khẩu!");
        exitButton = new JButton("Thoát!");


        loginButton.addActionListener(this);
        forgotPasswordButton.addActionListener(this);
        signupButton.addActionListener(this);
        exitButton.addActionListener(this);

        panel.add(hello);
        panel.add(loginButton);
        panel.add(signupButton);
        panel.add(forgotPasswordButton);
        panel.add(exitButton);
        add(panel);
        setVisible(true);
        userDatabase = new ArrayList<>();
    }

    private void showLoginUI() {
        panel.removeAll();
        panel.add(loginButton);
        panel.add(forgotPasswordButton);
        panel.add(signupButton);
        panel.add(exitButton);
        revalidate();
        repaint();
    }

    private void showLoggedInUI() {
        panel.removeAll();
        JLabel welcomeLabel = new JLabel("Xin chào " + loggedInUser);
        changeUsernameButton = new JButton("Thay đổi tên người dùng");
        changeEmailButton = new JButton("Thay đổi email người dùng");
        changePasswordButton = new JButton("Thay đổi mật khẩu ");
        logoutButton = new JButton("Đăng xuất");

        changeUsernameButton.addActionListener(this);
        changeEmailButton.addActionListener(this);
        changePasswordButton.addActionListener(this);
        logoutButton.addActionListener(this);


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
    private void showLogInUI() {
        panel.removeAll();
        JLabel usernameLabel = new JLabel("Tên người dùng:");
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButtonAction = new JButton("Đăng nhập");
        backButton = new JButton("Trở lại");
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
                    passWordLoggedInUser= foundUser.getPassword();
                    showLoggedInUI();
                } else {
                    JOptionPane.showMessageDialog(panel, "Tên người dùng hoặc mật khẩu không hợp lệ." + "\n" + "Vui lòng nhập lại!");
                    showAfterLogInFailUI();
                }
            }
        });
        backButton.addActionListener(e -> showLoginUI());

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButtonAction);
        panel.add(backButton);

        revalidate();
        repaint();
    }

    private void showAfterLogInFailUI() {
        panel.removeAll();
        JButton newButton = new JButton("Đăng nhập lại.");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLogInUI();
            }
        });
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showForgotPasswordUI();
            }
        });
        backButton.addActionListener(e -> showLoginUI());
        panel.add(newButton);
        panel.add(forgotPasswordButton);
        panel.add(backButton);
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
                    JOptionPane.showMessageDialog(panel, "Không tìm thấy email!" + "\n" + "Vui lòng nhập lại!");
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
    private void showSignUpUI() {
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
                loggedInUser = usernameField.getText();
                emailLoggedInUser = emailField.getText();
                passWordLoggedInUser = new String(passwordField.getPassword());

                boolean usernameExists = false;
                for (User user : userDatabase) {
                    if (user.getUserName().equals(loggedInUser)){
                        usernameExists = true;
                        break;
                    }
                }

                boolean emailExists = false;
                for (User user : userDatabase) {
                    if (user.getEmail().equals(emailLoggedInUser)) {
                        emailExists = true;
                        break;
                    }
                }

                boolean validEmailFormat = isValidEmail(emailLoggedInUser);
                boolean validPassword = isValidPassword(passWordLoggedInUser);

                if (usernameExists) {
                    JOptionPane.showMessageDialog(panel, "Tên người dùng đã tồn tại!" + "\n" + "Vui lòng nhập lại!");
                } else if (emailExists) {
                    JOptionPane.showMessageDialog(panel, "Email người dùng đã tồn tại!" + "\n" + "Vui lòng nhập lại!");
                } else if (!validEmailFormat) {
                    JOptionPane.showMessageDialog(panel, "Vui lòng nhập đúng định dạng email!" + "\n" + "Ví dụ: Dat.98-java21@java-21.techmaster.com");
                } else if (!validPassword) {
                    JOptionPane.showMessageDialog(panel, """
                            Vui lòng nhâp mật khẩu hợp lệ.
                            Độ dài từ 7 đến 15 ký tự.
                            Chứa ít nhất một ký tự viết hoa.
                            Chứa ít nhất một ký tự đặc biệt (.,-_;).""");
                } else {
                    userDatabase.add(new User(loggedInUser, emailLoggedInUser, passWordLoggedInUser));
                    JOptionPane.showMessageDialog(panel, "Tạo tài khoản mới thành công." + "\n" + "Mời bạn đăng nhập vào tài khoản mới!");
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
    private void showChangUsernameUI() {
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
                    JOptionPane.showMessageDialog(panel, "Tên người dùng đã tồn tại." + "\n" + "Vui lòng nhập lại!");
                } else {
                    for (User user : userDatabase) {
                        if (user.getUserName().equals(loggedInUser)) {
                            user.setUserName(usernameField.getText());
                            loggedInUser = usernameField.getText();
                            JOptionPane.showMessageDialog(panel, "Đổi tên người dùng thành công." + "\n" + "Mời bạn đăng nhập lại!");
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
    private void showChangeEmailUI() {
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
                    JOptionPane.showMessageDialog(panel, "Email đã tồn tại." + "\n" + "Vui lòng nhập lại!");
                } else if (!isValidEmail(emailField.getText())) {
                    JOptionPane.showMessageDialog(panel, "Vui lòng nhập email hợp lệ." + "\n" + "Ví dụ: Dat.98-java21@java-21.techmaster.com");
                } else {
                    for (User user : userDatabase) {
                        if (user.getUserName().equals(loggedInUser)) {
                            user.setEmail(emailField.getText());
                            emailLoggedInUser= emailField.getText();
                            JOptionPane.showMessageDialog(panel, "Đổi email thành công." + "\n" + "Mời bạn đăng nhập lại!");
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

    private void showChangePasswordUI(String inputEmail) {
        panel.removeAll();
        JLabel infoUser = new JLabel("Tên người dùng của bạn là: "+loggedInUser);
        JLabel newPasswordLabel = new JLabel("Nhập  mật khẩu mới của bạn:");
        passwordField = new JPasswordField();
        submitButton = new JButton("Đồng ý");
        backButton = new JButton("Trở lại");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean validPassword = isValidPassword(String.valueOf(passwordField.getPassword()));
                if (!validPassword) {
                    JOptionPane.showMessageDialog(panel, """
                            Vui lòng nhâp mật khẩu hợp lệ.
                            Độ dài từ 7 đến 15 ký tự.
                            Chứa ít nhất một ký tự viết hoa.
                            Chứa ít nhất một ký tự đặc biệt (.,-_;).""");
                } else {
                    for (User user : userDatabase) {
                        if (user.getEmail().equals(inputEmail)) {
                            user.setPassword(String.valueOf(passwordField));
                            passWordLoggedInUser= String.valueOf(passwordField);
                            JOptionPane.showMessageDialog(panel, "Thay đổi mật khẩu thành công." + "\n" + "Mời bạn đăng nhập lại!");
                            break;
                        } else if (user.getUserName().equals(loggedInUser)) {
                            user.setPassword(String.valueOf(passwordField));
                            passWordLoggedInUser= String.valueOf(passwordField);
                            JOptionPane.showMessageDialog(panel, "Thay đổi mật khẩu thành công." + "\n" + "Mời bạn đăng nhập lại!");
                            break;
                        }
                    }
                    showLoginUI();
                }
            }
        });
        backButton.addActionListener(e -> showLoggedInUI());
        panel.add(infoUser);
        panel.add(newPasswordLabel);
        panel.add(passwordField);
        panel.add(submitButton);
        panel.add(backButton);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String inputEmail = "";
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
