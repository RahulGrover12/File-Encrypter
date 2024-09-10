// https://github.com/RahulGrover12/
// Rahul Grover
package views;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    private final Scanner sc = new Scanner(System.in);

    public void welocmeScreen() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the App");
        System.out.println("Press 1 to Login");
        System.out.println("Press 2 to Signup");
        System.out.println("Press 0 to Exit");

        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> login();
            case 2 -> signup();
            case 0 -> System.exit(0);
        }
    }

    private void login() {
        System.out.print("Enter your gmail: ");
        String email = sc.nextLine();
        try {
            if (UserDAO.isExists(email)) {
                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, genOTP);
                System.out.print("Enter the OTP: ");
                String otp = sc.nextLine();

                if (otp.equals(genOTP)) {
                    UserView uv = new UserView(email);
                    uv.home();
                } else {
                    System.out.println("OTP is incorrect!!");
                }
            } else {
                System.out.println("User not Found, PLease signup!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void signup() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your Gmail: ");
        String email = sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.print("Enter the OTP: ");
        String otp = sc.nextLine();
        if (otp.equals(genOTP)) {
            User user = new User(name, email);
            int response = UserService.saveUser(user);

            switch (response) {
                case 0 -> {
                    System.out.println("User Registered!!");
                    UserView uv = new UserView(email);
                    uv.home();
                }
                case 1 -> System.out.println("User already exists!");
                default -> System.out.println("Error during registration.");
            }
        } else {
            System.out.println("OTP is incorrect!!");
        }
    }
}
