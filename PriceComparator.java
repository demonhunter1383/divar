package ir.ac.kntu;

import java.util.Comparator;

public class PriceComparator implements Comparator<Ad> {
    @Override
    public int compare(Ad ad1, Ad ad2) {
        return ad1.getPrice() - ad2.getPrice();
    }
}
