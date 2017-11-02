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

public class BookListInteractorTest {

    @InjectMocks
    private BookListInteractor interactor;

    @Mock
    private BookListOutputPort outputPort;

    @Mock
    private BookListRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(outputPort);
    }

    @Test
    public void loadBookList_WithGenericException() throws BookListRepository.GenericException {
        doThrow(BookListRepository.GenericException.class).when(repository).loadBookList();

        interactor.loadBookList();

        then(outputPort).should().onPendingRequest();
        then(outputPort).should().onGenericException();
    }

    @Test
    public void loadBookList_WithListReceived() throws BookListRepository.GenericException {
        Book book1 = getMockedBook("azer", "titre1", 2, "http://test.fr");
        Book book2 = getMockedBook("qsdf", "titre2", 3, "http://test2.fr");
        List<Book> bookList = Arrays.asList(book1, book2);
        given(repository.loadBookList()).willReturn(bookList);

        interactor.loadBookList();

        then(outputPort).should().onPendingRequest();
        then(outputPort).should().onBookListReceived(bookList);
    }

    private Book getMockedBook(String isbn, String title, int price, String cover) {
        final Book book = mock(Book.class);
        given(book.getIsbn()).willReturn(isbn);
        given(book.getTitle()).willReturn(title);
        given(book.getPrice()).willReturn(price);
        given(book.getCover()).willReturn(cover);
        return book;
    }
}
