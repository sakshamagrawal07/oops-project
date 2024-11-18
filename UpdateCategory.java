import java.io.*;

class UpdateCategory {
    public static int countCategory(String categoryFile) throws IOException {
        int count = 0;
        try {
            FileInputStream fin = new FileInputStream(categoryFile);
            DataInputStream din = new DataInputStream(fin);
            boolean eof = false;
            while (!eof) {
                try {
                    din.readInt();
                    din.readUTF();
                    count++;
                } catch (EOFException e) {
                    eof = true;
                }
            }
            din.close();
            fin.close();
        } catch (Exception e) {
            System.out.println("Error in reading " + categoryFile);
            System.out.println(e.getStackTrace());
        }
        return count;
    }

    public static void addCategory(String category, String categoryFile) throws IOException {
        try {
            FileOutputStream fout = new FileOutputStream("temp.dat");
            DataOutputStream dout = new DataOutputStream(fout);
            int count = 0;
            FileInputStream fin = new FileInputStream(categoryFile);
            DataInputStream din = new DataInputStream(fin);
            boolean eof = false;
            while (!eof) {
                try {
                    dout.writeInt(din.readInt());
                    dout.writeUTF(din.readUTF());
                    count++;
                } catch (EOFException e) {
                    eof = true;
                }
            }
            dout.writeInt(count + 1);
            dout.writeUTF(category);
            din.close();
            fin.close();

            File f = new File(categoryFile);
            f.delete();

            dout.close();
            fout.close();

            File f1 = new File(categoryFile);
            File f2 = new File("temp.dat");
            f2.renameTo(f1);
        } catch (Exception e) {
            System.out.println("Error adding the new category : "+category);
            System.out.println(e.getStackTrace());
        }
    }

    public static void displayCategory(String categoryFile) throws IOException {
        try {
            FileInputStream fin = new FileInputStream(categoryFile);
            DataInputStream din = new DataInputStream(fin);
            boolean eof = false;
            while (!eof) {
                try {
                    System.out.println(din.readInt() + " - " + din.readUTF());
                } catch (EOFException e) {
                    eof = true;
                }
            }
            din.close();
            fin.close();
        } catch (Exception e) {
            System.out.println("Error in reading " + categoryFile);
            e.getStackTrace();
        }
    }

    // return NOCAT if category not found
    public static String getCategory(int n, String categoryFile) throws IOException {
        String category = "";
        boolean found = false;
        try {
            FileInputStream fin = new FileInputStream(categoryFile);
            DataInputStream din = new DataInputStream(fin);
            boolean eof = false;
            while (!eof) {
                try {
                    int catNum = din.readInt();
                    String cat = din.readUTF();
                    // System.out.println("\n"+catNum+cat);
                    if (catNum == n) {
                        found = true;
                        category = cat;
                    }
                } catch (EOFException e) {
                    eof = true;
                }
            }
            din.close();
            fin.close();
        } catch (Exception e) {
            System.out.println("Error in reading " + categoryFile);
            System.out.println(e.getStackTrace());
        }
        if (!found) {
            category = "NOCAT";
        }
        return category;
    }

    public static void displayTotal(String dataFile, String categoryFile) throws IOException {
        try {
            FileInputStream fin1 = new FileInputStream(categoryFile);
            DataInputStream din1 = new DataInputStream(fin1);
            boolean categoryEOF = false;
            while (!categoryEOF) {
                double total = 0.0;
                try {
                    din1.readInt();
                    String c = din1.readUTF();
                    FileInputStream fin2 = new FileInputStream(dataFile);
                    DataInputStream din2 = new DataInputStream(fin2);
                    boolean eof = false;
                    while (!eof) {
                        try {
                            din2.readUTF();
                            String category = din2.readUTF();
                            double amt = din2.readDouble();
                            if (c.equalsIgnoreCase(category)) {
                                total += amt;
                            }
                        } catch (EOFException e) {
                            eof = true;
                        }
                    }
                    din2.close();
                    fin2.close();
                    System.out.println(c + " - Rs." + total);
                } catch (EOFException e) {
                    categoryEOF = true;
                }
            }
            din1.close();
            fin1.close();
        } catch (Exception e) {
            System.out.println("Error in reading " + categoryFile + " or " + dataFile);
            System.out.println(e.getStackTrace());
        }
    }
}