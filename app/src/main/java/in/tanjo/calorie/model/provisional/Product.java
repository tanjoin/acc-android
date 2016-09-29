package in.tanjo.calorie.model.provisional;

import com.j256.ormlite.dao.ForeignCollection;

import java.util.Date;

public class Product {

    private String id;

    private String name;

    private int price;

    private Date updatedAt;

    private ForeignCollection nutritionFacts;
}
