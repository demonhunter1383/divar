package ir.ac.kntu;

public class Ad {
    private int price;
    private String name;
    private Seller seller;
    private Category category;
    private Status status = Status.WAITING_TO_CONFIRM;

    public Ad() {

    }

    public Ad(int price, String name, Seller seller, Category category) {
        this.price = price;
        this.name = name;
        this.seller = seller;
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", seller=" + seller +
                ", category=" + category +
                ", status=" + status +
                '}';
    }
}
