import java.io.*;

class User {
    private static int currentID = 1; // Static variable to maintain unique IDs
    private int id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.id = currentID++;
        this.username = username;
        this.password = password;
        // createUserDirectory();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void saveToFile(String filename) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename, true))) {
            dos.writeInt(id); // Save the user's ID
            dos.writeUTF(username);
            dos.writeUTF(password);
            System.out.println("User saved successfully.");
            createUserDirectory();
        } catch (IOException e) {
            System.out.println("Error saving user data.");
            e.printStackTrace();
        }
    }

    private void createUserDirectory() {
        String userDirPath = ".\\Files\\" + username;
        File userDir = new File(userDirPath);

        if (userDir.mkdirs()) {
            try {
                new File(userDirPath + "\\Expense.dat").createNewFile();
                new File(userDirPath + "\\Expense_Category.dat").createNewFile();
                new File(userDirPath + "\\Income.dat").createNewFile();
                new File(userDirPath + "\\Income_Category.dat").createNewFile();
                new File(userDirPath + "\\Savings.dat").createNewFile();
                new File(userDirPath + "\\Savings_Category.dat").createNewFile();
                System.out.println("User setup successful.");
            } catch (IOException e) {
                System.out.println("Error setting up user...");
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to create user directory or it already exists.");
        }
    }

    public static User validateLogin(String filename, String username, String password) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            while (dis.available() > 0) {
                int id = dis.readInt(); // Read the user's ID
                String storedUsername = dis.readUTF();
                String storedPassword = dis.readUTF();

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    User user = new User(storedUsername, storedPassword);
                    user.id = id; // Preserve the original ID
                    return user;
                }
            }
        } catch (EOFException e) {
            System.out.println("End of file reached.");
        } catch (IOException e) {
            System.out.println("Error reading user data.");
            e.printStackTrace();
        }
        return null;
    }
}
