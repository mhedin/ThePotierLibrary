package com.morgane.presentation;

/**
 * View Model of the commercial offers to be displayed.
 */
public class CommercialOffersViewModel {

    private final String finalPrice;

    private final String saving;

    public CommercialOffersViewModel(String finalPrice, String saving) {
        this.finalPrice = finalPrice;
        this.saving = saving;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public String getSaving() {
        return saving;
    }
}
