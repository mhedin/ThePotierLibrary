package com.morgane.usecases;

/**
 * Interface of the output port which will manage the commercial offers.
 */
public interface CommercialOffersOutputPort {

    void onPendingRequest();

    void onGenericException();

    void onCommercialOffersReceived(CommercialOffer commercialOffer, int totalPriceBeforeOffer);
}
