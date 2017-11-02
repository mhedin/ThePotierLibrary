package com.morgane.usecases;

import java.util.List;

/**
 * Interface of the repository which will manage the commercial offers.
 */
public interface CommercialOffersRepository {

    CommercialOffer loadCommercialOffers(List<String> isbnList) throws GenericException;

    public class GenericException extends Exception {
    }
}
