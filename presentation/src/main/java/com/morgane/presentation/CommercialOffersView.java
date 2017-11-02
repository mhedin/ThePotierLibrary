package com.morgane.presentation;

/**
 * Interface of the view which will display the commercial offer.
 */
public interface CommercialOffersView {

    void displayLoading();

    void displayErrorMessage(String message);

    void displayCommercialOffer(CommercialOffersViewModel viewModel);
}
