package services.home;

import java.util.Comparator;

public class SortProductComparator implements Comparator<HomeService> {
    @Override
    public int compare(HomeService m1, HomeService m2){
        return m1.getMedia().getPrice() - m2.getMedia().getPrice();
    }
}