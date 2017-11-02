package com.morgane.repository.network;

import java.util.List;

/**
 * Json representation of the list of CommercialOffers objects downloaded.
 */
public class JsonOffersList {

    private List<JsonCommercialOffers> offers;

    public List<JsonCommercialOffers> getOffers() {
        return offers;
    }
}
