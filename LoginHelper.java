import java.io.*;
import java.util.*;

public class LoginHelper {
    private static final String FILENAME = "users_list.ser";

    public static String loginAndReturnUsername(Scanner scanner) {
        List<Object> rawUsers = loadUsers();
        List<UserSignupApp.User> users = castUsers(rawUsers);

        System.out.println("=== User Login ===");

        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        for (UserSignupApp.User user : users) {
            if (user.email.equalsIgnoreCase(email) && user.password.equals(password)) {
                System.out.println("Login successful! Welcome, " + user.name);
                return user.name;  
            }
        }

        System.out.println("Login failed. Invalid email or password.");
        return null;
    }

    @SuppressWarnings("unchecked")
    private static List<Object> loadUsers() {
        File file = new File(FILENAME);
        if (!file.exists()) return new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILENAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<Object>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Failed to load users.");
            return new ArrayList<>();
        }
    }

    private static List<UserSignupApp.User> castUsers(List<Object> rawUsers) {
        List<UserSignupApp.User> result = new ArrayList<>();
        for (Object obj : rawUsers) {
            if (obj instanceof UserSignupApp.User) {
                result.add((UserSignupApp.User) obj);
            }
        }
        return result;
    }
}
