package ir.ac.kntu;

import java.util.ArrayList;

public class Repository {
    private final MainAdmin admin;
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Admin> adminList = new ArrayList<>();
    private ArrayList<Buyer> buyerList = new ArrayList<>();
    private ArrayList<Seller> sellerList = new ArrayList<>();
    private ArrayList<Ad> ads = new ArrayList<>();//confirmed by admin
    private ArrayList<Ad> newAds = new ArrayList<>();//not confirmed
    private ArrayList<Peik> peikList = new ArrayList<>();
    private boolean map[][] = new boolean[100][100];

    public Repository() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                map[i][j] = false;
            }
        }
        this.admin = new MainAdmin("parsa", "parhami");
        Buyer temp = new Buyer("temp", "temp", "temp", "temp", 1, 1);
        Seller tempSeller = new Seller("temp1", "temp", "temp", "temp", 10, 5);
        buyerList.add(temp);
        sellerList.add(tempSeller);
        Ad tempAd = new Ad(5, "none", tempSeller, Category.CAR);
        tempAd.setStatus(Status.READY_TO_SALE);
        ads.add(tempAd);
        tempSeller.getAvailableAds().add(tempAd);
        Ad newTempAd = new Ad(10, "noneConfirm", tempSeller, Category.PHONE);
        newAds.add(newTempAd);
        tempSeller.getAvailableAds().add(newTempAd);
        userList.addAll(buyerList);
        userList.addAll(sellerList);
        userList.add(admin);
        Peik peik = new Peik("temp", "temp", 99, 99, "motor");
        peikList.add(peik);
        map[99][99] = true;
        Peik peik2 = new Peik("temp", "temp", 2, 2, "motor");
        peikList.add(peik2);
        map[2][2] = true;
        tempAd = new Ad(49, "lebas", tempSeller, Category.CLOTHES);
        newAds.add(tempAd);
        tempSeller.getAvailableAds().add(tempAd);
        tempAd = new Ad(30, "lebas2", tempSeller, Category.CLOTHES);
        newAds.add(tempAd);
        tempSeller.getAvailableAds().add(tempAd);
        Admin helperAdmin = new Admin("tempAdmin", "temp");
        adminList.add(helperAdmin);
        userList.add(helperAdmin);
        tempSeller.getReviews().add("nice_dude");

    }

    public ArrayList<Admin> getAdminList() {
        return adminList;
    }

    public void setAdminList(ArrayList<Admin> adminList) {
        this.adminList = adminList;
    }

    public MainAdmin getAdmin() {
        return admin;
    }

    public ArrayList<Peik> getPeikList() {
        return peikList;
    }

    public void setPeikList(ArrayList<Peik> peikList) {
        this.peikList = peikList;
    }

    public boolean[][] getMap() {
        return map;
    }

    public void setMap(boolean[][] map) {
        this.map = map;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public ArrayList<Buyer> getBuyerList() {
        return buyerList;
    }

    public void setBuyerList(ArrayList<Buyer> buyerList) {
        this.buyerList = buyerList;
    }

    public ArrayList<Seller> getSellerList() {
        return sellerList;
    }

    public void setSellerList(ArrayList<Seller> sellerList) {
        this.sellerList = sellerList;
    }

    public ArrayList<Ad> getAds() {
        return ads;
    }

    public void setAds(ArrayList<Ad> ads) {
        this.ads = ads;
    }

    public ArrayList<Ad> getNewAds() {
        return newAds;
    }

    public void setNewAds(ArrayList<Ad> newAds) {
        this.newAds = newAds;
    }
}
