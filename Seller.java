package ir.ac.kntu;

import java.util.ArrayList;

public class Seller extends Buyer {
    private ArrayList<Ad> availableAds = new ArrayList<>();
    private ArrayList<String> reviews = new ArrayList<>();

    public Seller() {
    }

    public Seller(String username, String password, String phoneNumber, String email, int width, int height) {
        super(username, password, phoneNumber, email, width, height);
    }

    public ArrayList<Ad> getAvailableAds() {
        return availableAds;
    }

    public void setAvailableAds(ArrayList<Ad> availableAds) {
        this.availableAds = availableAds;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "username='" + this.getUsername() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", phoneNumber='" + this.getPhoneNumber() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                '}';
    }
}
