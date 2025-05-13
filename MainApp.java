import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String currentUser = null;

        while (true) {
            System.out.println("\n=== Welcome ===");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select option: ");

        

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    UserSignupApp.main(null);
                    break;
                case "2":
                    currentUser = LoginHelper.loginAndReturnUsername(scanner);
                    if (currentUser != null) {
                        runUserMenu(scanner, currentUser);
                        currentUser = null; // Reset after logout
                    }
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void runUserMenu(Scanner scanner, String username) {
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Add Asset");
            System.out.println("2. Edit Asset");
            System.out.println("3. Delete Asset");
            System.out.println("4. generate Assets report");
            System.out.println("5. Calculate Zakat");
            System.out.println("6. Exit to Main Menu");
            System.out.print("Select option: ");

            if (!scanner.hasNextLine()) {
                System.out.println("No input. Returning to main menu.");
                return;
            }

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    PortfolioManager.addAsset(scanner, username);
                    break;
                case "2":
                    PortfolioManager.editAsset(scanner, username);
                    break;
                case "3":
                    PortfolioManager.deleteAsset(scanner, username);
                    break;
                case "4":
                    PortfolioManager.viewAssets(username);
                    break;
                case "5":
                    ZakatPage.main(new String[]{username});
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
