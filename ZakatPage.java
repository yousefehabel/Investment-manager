import java.io.*;
import java.util.*;

public class ZakatPage {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username to calculate Zakat: ");
        String username = scanner.nextLine().trim();

        SystemLayer system = new SystemLayer();

        try {
            double zakatAmount = system.calculateZakat(username);
            System.out.printf("Your Zakat amount is: %.2f%n", zakatAmount);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        
    }
}

class SystemLayer {
    private final ZakatEngine zakatEngine = new ZakatEngine();
    private final AssetRepository assetRepo = new AssetRepository();

    public double calculateZakat(String username) throws Exception {
        List<PortfolioManager.Asset> portfolio = assetRepo.loadPortfolio(username);

        if (portfolio == null || portfolio.isEmpty()) {
            throw new Exception("No portfolio data");
        }

        return zakatEngine.calcZakat(portfolio);
    }
}


class ZakatEngine {
    private static final double ZAKAT_RATE = 0.025;

    public double calcZakat(List<PortfolioManager.Asset> assets) {
        double total = 0.0;
        for (PortfolioManager.Asset asset : assets) {
            total += asset.value;
        }
        return total * ZAKAT_RATE;
    }
}


class AssetRepository {
    private static final String ASSET_FILE = "user_assets.ser";

    @SuppressWarnings("unchecked")
    public List<PortfolioManager.Asset> loadPortfolio(String username) {
        File file = new File(ASSET_FILE);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Map<String, List<PortfolioManager.Asset>> allAssets =
                    (Map<String, List<PortfolioManager.Asset>>) ois.readObject();

            return allAssets.getOrDefault(username, null);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to read assets: " + e.getMessage());
            return null;
        }
    }
}
