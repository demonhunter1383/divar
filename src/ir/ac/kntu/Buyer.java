package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

public class Buyer extends User {
    private String phoneNumber;
    private String email;
    private int wallet = 5000;
    private int height;
    private int width;
    private ArrayList<Ad> salesAds = new ArrayList<>();
    private ArrayList<Ad> savedAds = new ArrayList<>();
    private Scanner input = new Scanner(System.in);

    public Buyer() {
    }

    public Buyer(String username, String password, String phoneNumber, String email, int width, int height) {
        super(username, password);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public ArrayList<Ad> getSalesAds() {
        return salesAds;
    }

    public void setSalesAds(ArrayList<Ad> salesAds) {
        this.salesAds = salesAds;
    }

    public ArrayList<Ad> getSavedAds() {
        return savedAds;
    }

    public void setSavedAds(ArrayList<Ad> savedAds) {
        this.savedAds = savedAds;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }


    public void setPassword(String password) {
        boolean check = false;
        while (!check) {
            if (password.length() < 8) {
                System.out.println("low characters. enter another password");
            } else if (password.matches(".*\\d+.*") && password.matches(".*[A-Z]+.*") && password.matches(".*[a-z]+.*")) {
                this.setPassword(password);
                check = true;
                System.out.println("password added");
            }
            if (!check) {
                System.out.println("enter password");
                password = input.next();
            }
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        boolean check = false;
        while (!check) {
            if (phoneNumber.charAt(0) == '0' && phoneNumber.length() == 11) {
                this.phoneNumber = phoneNumber;
                check = true;
                System.out.println("phone number added");
            } else {
                System.out.println("wrong phone number enter again");
                phoneNumber = input.next();
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        boolean check = false;
        while (!check) {
            if (email.matches(".*\\@.*\\.com")) {
                this.email = email;
                check = true;
                System.out.println("email added");
            } else {
                System.out.println("wrong email enter again");
                email = input.next();
            }
        }
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "username='" + this.getUsername() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                +wallet +
                '}';
    }

}
