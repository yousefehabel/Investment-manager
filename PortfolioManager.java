import java.io.*;
import java.util.*;

public class PortfolioManager {

    // Serializable Asset class
    public static class Asset implements Serializable {
        private static final long serialVersionUID = 1L;
        String name;
        double value;

        public Asset(String name, double value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Asset{name='" + name + "', value=" + value + "}";
        }
    }

    private static final String USER_FILE = "users_list.ser";
    private static final String ASSET_FILE = "user_assets.ser";

    // Load users from file
    @SuppressWarnings("unchecked")
    private static List<UserSignupApp.User> loadUsers() {
        File file = new File(USER_FILE);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<UserSignupApp.User>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Failed to load users.");
            return new ArrayList<>();
        }
    }

    // Load all assets for all users
    @SuppressWarnings("unchecked")
    private static Map<String, List<Asset>> loadAssets() {
        File file = new File(ASSET_FILE);
        if (!file.exists()) return new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<String, List<Asset>>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Failed to load assets.");
            return new HashMap<>();
        }
    }

    // Save all user assets
    private static void saveAssets(Map<String, List<Asset>> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ASSET_FILE))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.out.println("Failed to save assets.");
        }
    }

    // ===== Modular Methods for Integration with MainApp.java =====

    public static void viewAssets(String username) {
        Map<String, List<Asset>> data = loadAssets();
        List<Asset> assets = data.getOrDefault(username, new ArrayList<>());

        if (assets.isEmpty()) {
            System.out.println("No assets found.");
            return;
        }

        for (int i = 0; i < assets.size(); i++) {
            System.out.println((i + 1) + ". " + assets.get(i));
        }
    }

    public static void addAsset(Scanner scanner, String username) {
        Map<String, List<Asset>> data = loadAssets();
        List<Asset> assets = data.getOrDefault(username, new ArrayList<>());

        System.out.print("Enter asset name: ");
        String name = scanner.nextLine();
        System.out.print("Enter asset value: ");
        double value;

        try {
            value = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid value entered.");
            return;
        }

        assets.add(new Asset(name, value));
        data.put(username, assets);
        saveAssets(data);
        System.out.println("Asset added.");
    }

    public static void editAsset(Scanner scanner, String username) {
        Map<String, List<Asset>> data = loadAssets();
        List<Asset> assets = data.getOrDefault(username, new ArrayList<>());

        if (assets.isEmpty()) {
            System.out.println("No assets to edit.");
            return;
        }

        for (int i = 0; i < assets.size(); i++) {
            System.out.println((i + 1) + ". " + assets.get(i));
        }

        System.out.print("Select asset number to edit: ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (index < 0 || index >= assets.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new value: ");
        double newValue;

        try {
            newValue = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid value.");
            return;
        }

        assets.set(index, new Asset(newName, newValue));
        data.put(username, assets);
        saveAssets(data);
        System.out.println("Asset updated.");
    }

    public static void deleteAsset(Scanner scanner, String username) {
        Map<String, List<Asset>> data = loadAssets();
        List<Asset> assets = data.getOrDefault(username, new ArrayList<>());

        if (assets.isEmpty()) {
            System.out.println("No assets to delete.");
            return;
        }

        for (int i = 0; i < assets.size(); i++) {
            System.out.println((i + 1) + ". " + assets.get(i));
        }

        System.out.print("Enter asset number to delete: ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (index < 0 || index >= assets.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        assets.remove(index);
        data.put(username, assets);
        saveAssets(data);
        System.out.println("Asset deleted.");
    }
}
