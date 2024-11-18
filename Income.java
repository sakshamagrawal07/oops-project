import java.io.*;
import java.util.*;

class Income extends Category {
    private final String username;
    private final Saving savings;
    private final String incomeFile;
    private final String incomeCategoryFile;

    Income(String username, Saving savings) {
        this.username = username;
        this.savings = savings;
        this.incomeFile = ".\\Files\\" + this.username + "\\Income.dat";
        this.incomeCategoryFile = ".\\Files\\" + this.username + "\\Income_Category.dat";
        setFiles(incomeFile, incomeCategoryFile);
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
        System.out.println("Enter amount of Income");
        double income = sc.nextDouble();
        FileOutputStream fout = new FileOutputStream("temp.dat");
        DataOutputStream dout = new DataOutputStream(fout);
        try {
            FileInputStream fin = new FileInputStream(incomeFile);
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
            dout.writeDouble(income);
            din.close();
            fin.close();
            File f = new File(incomeFile);
            f.delete();
        } catch (FileNotFoundException e) {
            dout.writeUTF(date);
            dout.writeUTF(category);
            dout.writeDouble(income);
        }
        dout.close();
        fout.close();
        File f1 = new File(incomeFile);
        File f2 = new File("temp.dat");
        f2.renameTo(f1);
        // sc.close();
        savings.add(income);
    }

    public void displayIncome() throws IOException {
        FileInputStream fin = new FileInputStream(incomeFile);
        DataInputStream din = new DataInputStream(fin);
        boolean eof = false;
        while (!eof) {
            try {
                System.out.println(din.readUTF() + "\t" + din.readUTF() + "\t" + din.readDouble());
            } catch (EOFException e) {
                eof = true;
            }
        }
        din.close();
        fin.close();
    }
}