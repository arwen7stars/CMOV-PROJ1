package org.feup.cmov.validationcafeteria.dataStructures;

import org.feup.cmov.validationcafeteria.Constants;

import java.io.Serializable;

public class Voucher implements Serializable {
    private String id;
    private String type;
    private double discount;
    private boolean used;

    public Voucher(String id, String type, double discount) {
        this.id = id;
        this.type = type;
        this.discount = discount;
    }

    public String getName(String type) {
        if (type.equals(Constants.FREE_COFFEE)) {
            return Constants.COFFEE_NAME;
        } else if (type.equals(Constants.FREE_POPCORN)) {
            return Constants.POPCORN_NAME;
        } else {
            return Constants.DISCOUNT_NAME;
        }
    }

    public String getDescription(String type) {
        if (type.equals(Constants.FREE_COFFEE)) {
            return Constants.COFFEE_DESCRIPTION;
        } else if (type.equals(Constants.FREE_POPCORN)) {
            return Constants.POPCORN_DESCRIPTION;
        } else {
            return Constants.DISCOUNT_DESCRIPTION;
        }
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
