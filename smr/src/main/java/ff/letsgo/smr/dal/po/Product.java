package ff.letsgo.smr.dal.po;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = -408893384953490358L;
    private long id;
    private String name;
    private double price;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
