import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class FilterMain {
    public static void filterMenu(boolean isIncome, String username) throws IOException, ParseException {
        String file = ".\\Files\\" + username;
        String message = "";
        if (isIncome) {
            message = "income";
            file += "\\Income.dat";
        } else {
            message = "expense";
            file += "\\Expense.dat";
        }

        Scanner scanner = new Scanner(System.in);
        Filter filter = new Filter(file);

        System.out.println("Select an option to filter " + message + ":");
        System.out.println("1 - View " + message + " on a specific date");
        System.out.println("2 - View " + message + " in a specific date range");
        System.out.println("3 - View total " + message + " for a specific year");
        System.out.println("4 - View total " + message + " for a specific month");
        System.out.println("5 - View all " + message + " above a specific amount");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter date (DD/MM/YYYY): ");
                String specificDate = scanner.next();
                filter.getExpensesOnDate(specificDate);
                break;

            case 2:
                System.out.print("Enter start date (DD/MM/YYYY): ");
                String startDate = scanner.next();
                System.out.print("Enter end date (DD/MM/YYYY): ");
                String endDate = scanner.next();
                filter.getExpensesInRange(startDate, endDate);
                break;

            case 3:
                System.out.print("Enter year (YYYY): ");
                int year = scanner.nextInt();
                filter.getExpensesYear(year);
                break;

            case 4:
                System.out.print("Enter year (YYYY): ");
                int monthYear = scanner.nextInt();
                System.out.print("Enter month (1-12): ");
                int month = scanner.nextInt();
                filter.getExpensesMonth(monthYear, month);
                break;

            case 5:
                System.out.print("Enter minimum amount: ");
                double minAmount = scanner.nextDouble();
                filter.getExpensesAboveAmount(minAmount);
                break;

            default:
                System.out.println("Invalid choice. Please select a valid option.");
        }
    }
}