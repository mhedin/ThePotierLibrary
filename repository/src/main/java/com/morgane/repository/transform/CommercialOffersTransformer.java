package com.morgane.repository.transform;

import com.morgane.repository.database.DatabaseCommercialOffers;
import com.morgane.repository.network.JsonCommercialOffers;
import com.morgane.repository.network.JsonOffersList;

/**
 * This class is used to transform Json objects into Database objects.
 */
public class CommercialOffersTransformer {

    public DatabaseCommercialOffers toCommercialOffers(JsonOffersList jsonOffersList) {
        int percentage = 0;
        int minus = 0;
        int sliceValue = 0;
        int value = 0;

        for (JsonCommercialOffers jsonCommercialOffers : jsonOffersList.getOffers()) {
            if (jsonCommercialOffers.getType().equalsIgnoreCase("percentage")) {
                percentage = jsonCommercialOffers.getValue();

            } else if (jsonCommercialOffers.getType().equalsIgnoreCase("minus")) {
                minus = jsonCommercialOffers.getValue();

            } else if (jsonCommercialOffers.getType().equalsIgnoreCase("slice")) {
                sliceValue = jsonCommercialOffers.getSliceValue();
                value = jsonCommercialOffers.getValue();
            }
        }

        return new DatabaseCommercialOffers(percentage, minus, sliceValue, value);
    }
}
