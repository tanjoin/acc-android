package in.tanjo.calorie.model.provisional;

import java.util.Date;
import java.util.List;

public class Receipt {

    private String id;

    private Store store;

    private int total;

    private int point;

    private List<Purchase> purchases;

    private Date createdAt;
}
