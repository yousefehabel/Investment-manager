import java.io.*;
import java.util.*;

public class UserSignupApp {

    static class User implements Serializable {
        private static final long serialVersionUID = 1L;

        int id;
        String name;
        String email;
        String password;
        String phoneNumber;

        public User(int id, String name, String email, String password, String phoneNumber) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String toString() {
            return "User{id=" + id + ", name='" + name + "', email='" + email +
                    "', phoneNumber='" + phoneNumber + "'}";
        }
    }

    private static final String FILENAME = "users_list.ser";

    public static void main(String[] args) {
        List<User> users = loadUsers();

        System.out.println("=== User Sign-Up ===");

        
        Scanner scanner = new Scanner(System.in);

        String name;
        while (true) {
            System.out.print("Enter username: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Username cannot be empty.");
            } else if (isUsernameTaken(users, name)) {
                System.out.println("Username is already taken. Please try another one.");
            } else {
                break;
            }
        }

        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine().trim();
            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Must contain '@' and end with '.com'");
            } else if (isEmailTaken(users, email)) {
                System.out.println("Email is already used. Please use a different email.");
            } else {
                break;
            }
        }

        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty.");
            } else {
                break;
            }
        }

        String phoneNumber;
        while (true) {
            System.out.print("Enter phone number (11 digits): ");
            phoneNumber = scanner.nextLine().trim();
            if (!phoneNumber.matches("\\d{11}")) {
                System.out.println("Invalid phone number. Must be exactly 11 digits.");
            } else {
                break;
            }
        }

        int newId = users.isEmpty() ? 1 : users.get(users.size() - 1).id + 1;
        User newUser = new User(newId, name, email, password, phoneNumber);
        users.add(newUser);
        saveUsers(users);

        System.out.println("User signed up successfully!");
        System.out.println(newUser);
    }

    private static boolean isUsernameTaken(List<User> users, String name) {
        for (User user : users) {
            if (user.name.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmailTaken(List<User> users, String email) {
        for (User user : users) {
            if (user.email.equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.endsWith(".com");
    }

    @SuppressWarnings("unchecked")
    private static List<User> loadUsers() {
        File file = new File(FILENAME);
        if (!file.exists()) return new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILENAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<User>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Failed to load users. Starting with an empty list.");
            return new ArrayList<>();
        }
    }

    private static void saveUsers(List<User> users) {
        try (FileOutputStream fos = new FileOutputStream(FILENAME);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Failed to save users.");
        }
    }
}
