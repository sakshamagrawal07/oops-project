import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Scanner;

class UserService {
    private static final String REGISTER_FILE = ".\\Files\\register.dat";
    private static final String LOGIN_FILE = ".\\Files\\login.dat";

    public void registerUser() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("REGISTRATION");
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        String password;
        String confirmPassword;
        while (true) {
            System.out.print("Enter password: ");
            password = sc.nextLine();

            if (!password.matches("[a-zA-Z0-9]{6,}")) {
                System.out.println("Password must be alphanumeric and at least 6 characters long.");
                continue;
            }

            System.out.print("Confirm password: ");
            confirmPassword = sc.nextLine();

            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");
            } else {
                break;
            }
        }

        User user = new User(username, password);
        user.saveToFile(REGISTER_FILE);

        System.out.println("Successfully Registered with User ID: " + user.getId());

        try {
            Main.task(username);
        } catch (IOException e) {
            System.out.println("Error displaying menu");
            e.printStackTrace();
        }
    }

    public void loginUser() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("LOGIN");
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = User.validateLogin(REGISTER_FILE, username, password);

        if (user != null) {
            System.out.println("Login Successful");
            logLogin(user.getId());

            try {
                Main.task(username);
            } catch (IOException e) {
                System.out.println("Error displaying menu");
                e.printStackTrace();
            }

        } else {
            System.out.println("Invalid Credentials");
        }
    }

    private void logLogin(int userId) {
        try (FileWriter fw = new FileWriter(LOGIN_FILE, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(userId + "," + LocalDateTime.now());
        } catch (IOException e) {
            System.out.println("Error logging login time");
            e.printStackTrace();
        }
    }

}

// public class Main {
// public static void main(String[] args) {
// showLoadingScreen();

// UserService userService = new UserService();
// Scanner sc = new Scanner(System.in);

// System.out.println("\nChoose an option:");
// System.out.println("1. Register");
// System.out.println("2. Login");
// int choice = sc.nextInt();
// sc.nextLine();

// if (choice == 1) {
// userService.registerUser();
// } else if (choice == 2) {
// userService.loginUser();
// } else {
// System.out.println("Invalid choice!");
// }
// sc.close();
// }

// public static void showLoadingScreen() {
// String text = "Expense Manager";
// System.out.print(text);

// try {
// for (int i = 0; i < 3; i++) {
// Thread.sleep(500);
// System.out.print("...");
// }
// Thread.sleep(500);
// } catch (InterruptedException e) {
// System.out.println("Loading interrupted");
// }
// }
// }
//