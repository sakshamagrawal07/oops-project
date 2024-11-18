import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Filter {
    private final String expenseFile;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Filter(String expenseFile) {
        this.expenseFile = expenseFile;
    }

    // Method to filter expenses on a specific date
    public void getExpensesOnDate(String specificDate) throws IOException, ParseException {
        double total = 0;
        Date date = dateFormat.parse(specificDate);
        FileInputStream fin = new FileInputStream(expenseFile);
        DataInputStream din = new DataInputStream(fin);
        boolean eof = false;

        System.out.println("Transactions on " + specificDate + ":");
        while (!eof) {
            try {
                String recordDate = din.readUTF();
                String category = din.readUTF();
                double amount = din.readDouble();
                if (dateFormat.parse(recordDate).equals(date)) {
                    System.out.println(category + " - Rs." + amount);
                    total += amount;
                }
            } catch (EOFException e) {
                eof = true;
            }
        }
        din.close();
        System.out.println("Total Transaction on " + specificDate + ": Rs." + total);
    }

    // Method to filter expenses within a date range
    public void getExpensesInRange(String startDate, String endDate) throws IOException, ParseException {
        double total = 0;
        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);
        FileInputStream fin = new FileInputStream(expenseFile);
        DataInputStream din = new DataInputStream(fin);
        boolean eof = false;

        System.out.println("Transactions from " + startDate + " to " + endDate + ":");
        while (!eof) {
            try {
                String recordDate = din.readUTF();
                String category = din.readUTF();
                double amount = din.readDouble();
                Date record = dateFormat.parse(recordDate);
                if (!record.before(start) && !record.after(end)) {
                    System.out.println(recordDate + " - " + category + " - Rs." + amount);
                    total += amount;
                }
            } catch (EOFException e) {
                eof = true;
            }
        }
        din.close();
        System.out.println("Total Transaction from " + startDate + " to " + endDate + ": Rs." + total);
    }

    // Method to filter expenses for a specific year and month
    public void getExpensesMonth(int year, int month) throws IOException, ParseException {
        double total = 0;
        FileInputStream fin = new FileInputStream(expenseFile);
        DataInputStream din = new DataInputStream(fin);
        boolean eof = false;

        System.out.println("Transactions for " + year + "-" + month + ":");
        while (!eof) {
            try {
                String recordDate = din.readUTF();
                String category = din.readUTF();
                double amount = din.readDouble();
                Date record = dateFormat.parse(recordDate);
                if (record.getYear() + 1900 == year && record.getMonth() + 1 == month) {
                    System.out.println(recordDate + " - " + category + " - Rs." + amount);
                    total += amount;
                }
            } catch (EOFException e) {
                eof = true;
            }
        }
        din.close();
        System.out.println("Total Transaction for " + year + "-" + month + ": Rs." + total);
    }

    // Method to filter expenses for a specific year
    public void getExpensesYear(int year) throws IOException, ParseException {
        double total = 0;
        FileInputStream fin = new FileInputStream(expenseFile);
        DataInputStream din = new DataInputStream(fin);
        boolean eof = false;

        System.out.println("Transactions for year " + year + ":");
        while (!eof) {
            try {
                String recordDate = din.readUTF();
                String category = din.readUTF();
                double amount = din.readDouble();
                Date record = dateFormat.parse(recordDate);
                if (record.getYear() + 1900 == year) {
                    System.out.println(recordDate + " - " + category + " - Rs." + amount);
                    total += amount;
                }
            } catch (EOFException e) {
                eof = true;
            }
        }
        din.close();
        System.out.println("Total Transaction for year " + year + ": Rs." + total);
    }

    // Method to filter expenses above a specified amount
    public void getExpensesAboveAmount(double minAmount) throws IOException {
        double total = 0;
        FileInputStream fin = new FileInputStream(expenseFile);
        DataInputStream din = new DataInputStream(fin);
        boolean eof = false;

        System.out.println("Transactions above Rs." + minAmount + ":");
        while (!eof) {
            try {
                String recordDate = din.readUTF();
                String category = din.readUTF();
                double amount = din.readDouble();
                if (amount > minAmount) {
                    System.out.println(recordDate + " - " + category + " - Rs." + amount);
                    total += amount;
                }
            } catch (EOFException e) {
                eof = true;
            }
        }
        din.close();
        System.out.println("Total Transaction above Rs." + minAmount + ": Rs." + total);
    }
}