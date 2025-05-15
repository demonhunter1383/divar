package ir.ac.kntu;

import java.util.HashMap;

public class MainAdmin extends User {
    private static int wallet;
    private static HashMap<Peik, Integer> peikMoney = new HashMap<>();

    public MainAdmin() {

    }

    public HashMap<Peik, Integer> getPeikMoney() {
        return peikMoney;
    }

    public void setPeikMoney(HashMap<Peik, Integer> peikMoney) {
        MainAdmin.peikMoney = peikMoney;
    }

    public int getWallet() {
        return wallet;
    }

    public boolean setWallet(int wallet) {
        if (wallet >= 0) {
            this.wallet = wallet;
            return true;
        } else {
            return false;
        }
    }

    public MainAdmin(String username, String password) {
        super(username, password);
    }

    public static void peikPanel(Repository repository, Buyer buyer, Ad ad) {
        double minDistance = 200;
        double distance;
        String peikType = "";
        if (ad.getCategory().equals(Category.PHONE) || ad.getCategory().equals(Category.CLOTHES) || ad.getCategory().equals(Category.STATIONERY)) {
            peikType = "motor";
        } else if (ad.getCategory().equals(Category.HOME_APPLIANCES)) {
            peikType = "vanet";
        } else {
            System.out.println("cant send cars sry");
            return;
        }
        while (minDistance == 200) {
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    if (repository.getMap()[i][j]) {
                        for (Peik peik : repository.getPeikList()) {
                            if (peik.getType().equals(peikType) && peik.getHeight() == j && peik.getWidth() == i) {
                                distance = Math.sqrt(Math.pow(i - ad.getSeller().getWidth(), 2) + Math.pow(j - ad.getSeller().getHeight(), 2));
                                if (peik.isAvailable() && minDistance > distance) {
                                    minDistance = distance;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (minDistance == 200) {
                ad.setStatus(Status.WAITIING_TO_SEND);
            }
        }
        for (Peik peik : repository.getPeikList()) {
            if (peik.isAvailable()) {
                distance = Math.sqrt(Math.pow(peik.getWidth() - ad.getSeller().getWidth(), 2) + Math.pow(peik.getHeight() - ad.getSeller().getHeight(), 2));
                if (peik.getType().equals(peikType) && distance == minDistance) {
                    distance = Math.sqrt(Math.pow(buyer.getWidth() - ad.getSeller().getWidth(), 2) + Math.pow(buyer.getHeight() - ad.getSeller().getHeight(), 2));
                    peik.setAvailable(false, 3 * (int) (minDistance + distance));
                    if (peik.getType().equals("motor")) {
                        buyer.setWallet(buyer.getWallet() - (int) (2000 * distance));
                        wallet += (int) (2000 * distance);
                        peikMoney.put(peik, (int) (2000 * distance));
                        ad.setStatus(Status.SENT);
                        repository.getMap()[peik.getWidth()][peik.getHeight()] = false;
                        peik.setHeight(buyer.getHeight());
                        peik.setWidth(buyer.getWidth());
                        repository.getMap()[peik.getWidth()][peik.getHeight()] = true;
                    } else if (peik.getType().equals("vanet")) {
                        buyer.setWallet(buyer.getWallet() - (int) (4000 * distance));
                        wallet += (int) (4000 * distance);
                        peikMoney.put(peik, (int) (4000 * distance));
                        ad.setStatus(Status.SENT);
                        repository.getMap()[peik.getWidth()][peik.getHeight()] = false;
                        peik.setHeight(buyer.getHeight());
                        peik.setWidth(buyer.getWidth());
                        repository.getMap()[peik.getWidth()][peik.getHeight()] = true;
                    }
                }
            }
        }
    }
}
