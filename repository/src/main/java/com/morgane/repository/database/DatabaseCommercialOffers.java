package com.morgane.repository.database;

import com.morgane.usecases.CommercialOffer;

/**
 * Implementation of CommercialOffer, which can be used to store it in database.
 */
public class DatabaseCommercialOffers implements CommercialOffer {

    private int percentage;

    private int minus;

    private int sliceValue;

    private int value;

    public DatabaseCommercialOffers() {

    }

    public DatabaseCommercialOffers(int percentage, int minus, int sliceValue, int value) {
        this.percentage = percentage;
        this.minus = minus;
        this.sliceValue = sliceValue;
        this.value = value;
    }

    @Override
    public int getPercentage() {
        return percentage;
    }

    @Override
    public int getMinus() {
        return minus;
    }

    @Override
    public int getSliceValue() {
        return sliceValue;
    }

    @Override
    public int getValue() {
        return value;
    }
}
