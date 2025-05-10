package ir.ac.kntu;

public class Peik extends User {
    private String type;
    private int wallet;
    private int height;
    private int width;
    private boolean available = true;

    public Peik(String username, String password, int height, int width, String type) {
        super(username, password);
        this.type = type;
        this.height = height;
        this.width = width;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available, int time) {
        this.available = available;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time * 60 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        this.available = true;
    }
}
