import java.io.*;
import java.util.*;

class Saving extends Category {
    private final String username;
    private final String savingFile;
    private final String savingCategoryFile;

    Saving(String username) {
        super();
        this.username = username;
        this.savingFile = ".\\Files\\" + this.username + "\\Savings.dat";
        this.savingCategoryFile = ".\\Files\\" + this.username + "\\Savings_Category.dat";
        setFiles(savingFile, savingCategoryFile);
    }

    public void add(double amt) throws IOException {
        Scanner sc = new Scanner(System.in);
        FileOutputStream fout = new FileOutputStream("temp.dat");
        DataOutputStream dout = new DataOutputStream(fout);
        String category;
        do {
            displayCategory();
            System.out.println("Choose Savings Category to Add Money TO");
            int ch = sc.nextInt();
            // System.err.println("getting saving category...");
            category = getCategory(ch);
            // System.err.println("got saving category...");
            if (category.equals("NOCAT")) {
                System.out.println("\n**INVALID CHOICE**\n");
            }
        } while (category.equals("NOCAT"));
        try {
            FileInputStream fin = new FileInputStream(savingFile);
            DataInputStream din = new DataInputStream(fin);
            boolean eof = false;
            boolean updated = false;
            while (!eof) {
                try {
                    String c = din.readUTF();
                    double s = din.readDouble();
                    if (c.equals(category)) {
                        dout.writeUTF(c);
                        dout.writeDouble(s + amt);
                        updated = true;
                    } else {
                        dout.writeUTF(c);
                        dout.writeDouble(s);
                    }
                } catch (EOFException e) {
                    eof = true;
                }
            }
            if (!updated) {
                dout.writeUTF(category);
                dout.writeDouble(amt);
            }
            din.close();
            fin.close();
            File f = new File(savingFile);
            f.delete();
        } catch (FileNotFoundException e) {
            System.out.println("Exception");
            dout.writeUTF(category);
            dout.writeDouble(amt);
        }
        dout.close();
        fout.close();
        File f1 = new File(savingFile);
        File f2 = new File("temp.dat");
        // f1.delete();
        f2.renameTo(f1);
        // sc.close();
    }

    public void subtract(double amt) throws IOException {
        Scanner sc = new Scanner(System.in);
        FileOutputStream fout = new FileOutputStream("temp.bin");
        DataOutputStream dout = new DataOutputStream(fout);
        String category;
        do {
            displayCategory();
            System.out.println("Choose Savings Category to Withdraw Money FROM");
            int ch = sc.nextInt();
            category = getCategory(ch);
            if (category.equals("NOCAT")) {
                System.out.println("\n**INVALID CHOICE**\n");
            }
        } while (category.equals("NOCAT"));
        try {
            FileInputStream fin = new FileInputStream(savingFile);
            DataInputStream din = new DataInputStream(fin);
            boolean eof = false;
            while (!eof) {
                try {
                    String c = din.readUTF();
                    double s = din.readDouble();
                    if (c.equals(category)) {
                        dout.writeUTF(c);
                        dout.writeDouble(s - amt);
                    } else {
                        dout.writeUTF(c);
                        dout.writeDouble(s);
                    }
                } catch (EOFException e) {
                    eof = true;
                }
            }
            din.close();
            fin.close();
            File f = new File(savingFile);
            f.delete();
        } catch (FileNotFoundException e) {
            dout.writeUTF(category);
            dout.writeDouble(-amt);
        }
        dout.close();
        fout.close();
        File f1 = new File(savingFile);
        File f2 = new File("temp.bin");
        f2.renameTo(f1);
        // sc.close();
    }

    public void display() throws IOException {
        FileInputStream fin = new FileInputStream(savingFile);
        DataInputStream din = new DataInputStream(fin);
        boolean eof = false;
        double tot = 0.0;
        while (!eof) {
            try {
                String c = din.readUTF();
                double s = din.readDouble();
                System.out.println(c + "\t" + s);
                tot += s;
            } catch (EOFException e) {
                eof = true;
            }
        }
        System.out.println("\nTOTAL SAVINGS = Rs." + tot);
        din.close();
        fin.close();
    }

    // public void divide() throws IOException {
    // Scanner sc = new Scanner(System.in);
    // display();
    // String cat1;
    // do {
    // displayCategory();
    // System.out.println("Choose Category to Transfer Money FROM");
    // int ch = sc.nextInt();
    // cat1 = getCategory(ch);
    // if (cat1.equals("NOCAT")) {
    // System.out.println("\n**INVALID CHOICE**\n");
    // }
    // } while (cat1.equals("NOCAT"));
    // String cat2;
    // do {
    // displayCategory();
    // System.out.println("Choose Category to Transfer Money TO");
    // int ch = sc.nextInt();
    // cat2 = getCategory(ch);
    // if (cat2.equals("NOCAT")) {
    // System.out.println("\n**INVALID CHOICE**\n");
    // }
    // } while (cat2.equals("NOCAT"));
    // System.out.println("Enter Amount to Transfer");
    // double amt = sc.nextDouble();
    // FileInputStream fin = new FileInputStream(savingFile);
    // DataInputStream din = new DataInputStream(fin);
    // FileOutputStream fout = new FileOutputStream("temp.bin");
    // DataOutputStream dout = new DataOutputStream(fout);
    // boolean eof = false;
    // while (!eof) {
    // try {
    // String c = din.readUTF();
    // double s = din.readDouble();
    // if (c.equals(cat1)) {
    // s = s - amt;
    // }
    // if (c.equals(cat2)) {
    // s = s + amt;
    // }
    // dout.writeUTF(c);
    // dout.writeDouble(s);
    // } catch (EOFException e) {
    // eof = true;
    // }
    // }
    // dout.close();
    // fout.close();
    // din.close();
    // fin.close();
    // File f1 = new File(savingFile);
    // File f2 = new File("temp.bin");
    // f1.delete();
    // f2.renameTo(f1);
    // // sc.close();
    // }
}