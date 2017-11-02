package com.morgane.usecases;

import com.annimon.stream.Stream;

import java.util.List;

import static com.annimon.stream.Collectors.toList;

/**
 * This class is used to manage the commercial offers.
 */
public class CommercialOffersInteractor {

    private final CommercialOffersOutputPort outputPort;

    private final CommercialOffersRepository repository;

    public CommercialOffersInteractor(CommercialOffersOutputPort outputPort, CommercialOffersRepository repository) {
        this.outputPort = outputPort;
        this.repository = repository;
    }

    public void loadCommercialOffers(List<Book> bookList) {
        outputPort.onPendingRequest();

        retrieveCommercialOffers(bookList);
    }

    private void retrieveCommercialOffers(List<Book> bookList) {
        final List<String> isbnList = Stream.of(bookList).map(book -> book.getIsbn()).collect(toList());
        final int totalPrice = Stream.of(bookList).mapToInt(book -> book.getPrice()).sum();

        try {
            CommercialOffer commercialOffer = repository.loadCommercialOffers(isbnList);

            outputPort.onCommercialOffersReceived(commercialOffer, totalPrice);
        } catch (CommercialOffersRepository.GenericException e) {
            outputPort.onGenericException();
        }
    }
}
