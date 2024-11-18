import java.util.Scanner;
import java.io.*;
import java.text.ParseException;

class Main {
    public static void main(String[] args) throws ParseException, IOException {
        showLoadingScreen();

        UserService userService = new UserService();
        Scanner sc = new Scanner(System.in);

        System.out.println("\nChoose an option:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            userService.registerUser();
        } else if (choice == 2) {
            userService.loginUser();
        } else {
            System.out.println("Invalid choice!");
        }
        sc.close();
    }

    public static void showLoadingScreen() {
        String text = "Expense Manager";
        System.out.print(text);

        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500);
                System.out.print("...");
            }
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Loading interrupted");
        }
    }

    public static void task(String username) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("\f1 - ADD INCOME");
        System.out.println("2 - ADD EXPENSE");
        System.out.println("3 - DISPLAY INCOME");
        System.out.println("4 - DISPLAY EXPENSE");
        System.out.println("5 - ADD CATEGORY OF EXPENSE");
        System.out.println("6 - DISPLAY CATEGORIES OF EXPENSE");
        System.out.println("7 - ADD CATEGORY OF INCOME");
        System.out.println("8 - DISPLAY CATEGORIES OF INCOME");
        System.out.println("9 - DISPLAY TOTAL SAVINGS");
        System.out.println("10 - DISPLAY TOTAL EXPENSE");
        System.out.println("11 - DISPLAY TOTAL INCOME");
        System.out.println("12 - ADD SAVINGS CATEGORY");
        System.out.println("13 - DISPLAY SAVINGS CATEGORY");
        System.out.println("14 - APPLY FILTERS");
        // System.out.println("15 - DIVIDE MONEY IN SAVINGS");
        System.out.println("\nEnter your choice");
        int ch = sc.nextInt();
        sc.nextLine();
        String category;
        Saving savings = new Saving(username);
        Income incomes = new Income(username, savings);
        Expense expenses = new Expense(username, savings);
        switch (ch) {
            case 1:
                incomes.add();
                break;

            case 2:
                expenses.add();
                break;

            case 3:
                incomes.displayIncome();
                break;

            case 4:
                expenses.displayExpense();
                break;

            case 5:
                System.out.println("Enter new Category to Add");
                category = sc.nextLine();
                expenses.addCategory(category);
                break;

            case 6:
                expenses.displayCategory();
                break;

            case 7:
                System.out.println("Enter new Category to Add");
                category = sc.nextLine();
                incomes.addCategory(category);
                break;

            case 8:
                incomes.displayCategory();
                break;

            case 9:
                savings.display();
                break;

            case 10:
                expenses.displayTotal();
                break;

            case 11:
                incomes.displayTotal();
                break;

            case 12:
                System.out.println("Enter new Category to Add");
                category = sc.nextLine();
                savings.addCategory(category);
                break;

            case 13:
                savings.displayCategory();
                break;

            case 14:
                System.out.println("1 - Apply filter on Income");
                System.out.println("2 - Apply filter on Expense");
                System.out.println("Enter your choice");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        FilterMain.filterMenu(true, username);
                        break;

                    case 2:
                        FilterMain.filterMenu(false, username);
                        break;

                    default:
                        System.out.println("Invalid Input");
                        break;
                }
                break;
            // case 15:
            //     savings.divide();
            //     break;

            default:
                System.out.println("Invalid Input");
        }
        System.out.println("\n1 - MORE TASK");
        System.out.println("0 - END TASK");
        System.out.println("Enter your choice");
        ch = sc.nextInt();
        if (ch == 1)
            task(username);
    }
}