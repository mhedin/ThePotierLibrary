package com.morgane.usecases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class CommercialOffersInteractorTest {

    @InjectMocks
    private CommercialOffersInteractor interactor;

    @Mock
    private CommercialOffersOutputPort outputPort;

    @Mock
    private CommercialOffersRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(outputPort);
    }

    @Test
    public void loadCommercialOffers_WithGenericException() throws CommercialOffersRepository.GenericException {
        doThrow(CommercialOffersRepository.GenericException.class).when(repository).loadCommercialOffers(Arrays.asList("1234", "5678"));

        interactor.loadCommercialOffers(Arrays.asList(getMockedBook("1234", "titre", 2,  "http"),
                getMockedBook("5678", "titre", 2,  "http")));

        then(outputPort).should().onPendingRequest();
        then(outputPort).should().onGenericException();
    }

    @Test
    public void loadCommercialOffers_WithListReceived() throws CommercialOffersRepository.GenericException {
        CommercialOffer commercialOffer = getMockedCommercialOffer(1, 2, 3, 4);
        given(repository.loadCommercialOffers(Arrays.asList("1234", "5678"))).willReturn(commercialOffer);

        interactor.loadCommercialOffers(Arrays.asList(getMockedBook("1234", "titre", 2,  "http"),
                getMockedBook("5678", "titre", 2,  "http")));

        then(outputPort).should().onPendingRequest();
        then(outputPort).should().onCommercialOffersReceived(commercialOffer, 4);
    }

    private Book getMockedBook(String isbn, String title, int price, String cover) {
        final Book book = mock(Book.class);
        given(book.getIsbn()).willReturn(isbn);
        given(book.getTitle()).willReturn(title);
        given(book.getPrice()).willReturn(price);
        given(book.getCover()).willReturn(cover);
        return book;
    }

    private CommercialOffer getMockedCommercialOffer(int percentage, int minus, int sliceValue, int value) {
        final CommercialOffer commercialOffer = mock(CommercialOffer.class);
        given(commercialOffer.getPercentage()).willReturn(percentage);
        given(commercialOffer.getMinus()).willReturn(minus);
        given(commercialOffer.getSliceValue()).willReturn(sliceValue);
        given(commercialOffer.getValue()).willReturn(value);
        return commercialOffer;
    }
}
