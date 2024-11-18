import java.util.*;
import java.io.*;

class Expense extends Category {
    private final String username;
    private final Saving savings;
    private final String expenseCategoryFile;
    private final String expenseFile;

    Expense(String username, Saving savings) {
        this.username = username;
        this.savings = savings;
        this.expenseFile = ".\\Files\\" + this.username + "\\Expense.dat";
        this.expenseCategoryFile = ".\\Files\\" + this.username + "\\Expense_Category.dat";
        setFiles(expenseFile, expenseCategoryFile);
    }

    public void add() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose Category:");
        displayCategory();
        System.out.println("Enter your choice");
        int ch = sc.nextInt();
        String category = getCategory(ch);
        while (category.equals("NOCAT")) {
            System.out.println("\nInvalid Choice");
            System.out.println("1 - Choose Category");
            System.out.println("2 - Enter category");
            ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
                case 1:
                    displayCategory();
                    System.out.println("Enter your choice");
                    ch = sc.nextInt();
                    category = getCategory(ch);
                    break;

                case 2:
                    System.out.println("Enter Category");
                    category = sc.nextLine();
                    break;
                default:
                    System.out.println("Invalid Choice. Try Again.");
                    category = "NOCAT";
            }
        }
        System.out.println("Enter Date(DD/MM/YYYY)");
        String date = sc.next();
        sc.nextLine();
        System.out.println("Enter amount of Expenditure");
        double expense = sc.nextDouble();
        FileOutputStream fout = new FileOutputStream("temp.dat");
        DataOutputStream dout = new DataOutputStream(fout);
        try {
            FileInputStream fin = new FileInputStream(expenseFile);
            DataInputStream din = new DataInputStream(fin);
            boolean eof = false;
            while (!eof) {
                try {
                    dout.writeUTF(din.readUTF());
                    dout.writeUTF(din.readUTF());
                    dout.writeDouble(din.readDouble());
                } catch (EOFException e) {
                    eof = true;
                }
            }
            dout.writeUTF(date);
            dout.writeUTF(category);
            dout.writeDouble(expense);
            din.close();
            fin.close();
            File f = new File(expenseFile);
            f.delete();
        } catch (FileNotFoundException e) {
            dout.writeUTF(date);
            dout.writeUTF(category);
            dout.writeDouble(expense);
        }
        dout.close();
        fout.close();
        File f1 = new File(expenseFile);
        File f2 = new File("temp.dat");
        f2.renameTo(f1);
        // sc.close();
        savings.subtract(expense);
    }

    public void displayExpense() throws IOException {
        FileInputStream fin = new FileInputStream(expenseFile);
        DataInputStream din = new DataInputStream(fin);
        boolean eof = false;
        while (!eof) {
            try {
                System.out.println(din.readUTF() + " - " + din.readUTF() + " - " + din.readDouble());
            } catch (EOFException e) {
                eof = true;
            }
        }
        din.close();
        fin.close();
    }
}