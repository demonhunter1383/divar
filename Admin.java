package ir.ac.kntu;

public class Admin extends User {
    private int wallet;

    public Admin() {

    }

    public Admin(String username, String password) {
        super(username, password);
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

    @Override
    public String toString() {
        return "Admin{" +
                "wallet=" + wallet +
                '}';
    }
}
