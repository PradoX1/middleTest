package entities;

import javax.swing.*;

public class Button {
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

    public JButton getLoginButton() {
        loginButton = new JButton("Đăng nhập");
        return loginButton;
    }

    public JButton getLoginButtonAction() {
        return loginButtonAction;
    }

    public JButton getSignupButton() {
        signupButton = new JButton("Đăng ký");
        return signupButton;
    }

    public JButton getForgotPasswordButton() {
        forgotPasswordButton = new JButton("Quên mật khẩu!");
        return forgotPasswordButton;
    }

    public JButton getLogoutButton() {
        logoutButton = new JButton("Đăng xuất");
        return logoutButton;
    }

    public JButton getChangeUsernameButton() {
        changeUsernameButton = new JButton("Thay đổi tên người dùng");
        return changeUsernameButton;
    }

    public JButton getChangeEmailButton() {
        changeEmailButton = new JButton("Thay đổi email người dùng");
        return changeEmailButton;
    }

    public JButton getChangePasswordButton() {
        changePasswordButton = new JButton("Thay đổi mật khẩu ");
        return changePasswordButton;
    }

    public JButton getExitButton() {
        exitButton = new JButton("Thoát!");
        return exitButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }
}
