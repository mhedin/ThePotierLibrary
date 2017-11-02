package com.morgane.usecases;

import java.util.List;

/**
 * This class is used to manage the book list.
 */
public class BookListInteractor {

    private final BookListOutputPort outputPort;
    private BookListRepository repository;

    public BookListInteractor(BookListOutputPort outputPort, BookListRepository repository) {
        this.outputPort = outputPort;
        this.repository = repository;
    }

    public void loadBookList() {
        outputPort.onPendingRequest();

        retrieveBookList();
    }

    private void retrieveBookList() {
        try {
            List<Book> bookList = repository.loadBookList();

            if (bookList.size() > 0) {
                outputPort.onBookListReceived(bookList);
            } else {
                outputPort.onGenericException();
            }
        } catch (BookListRepository.GenericException e) {
            outputPort.onGenericException();
        }
    }
}
