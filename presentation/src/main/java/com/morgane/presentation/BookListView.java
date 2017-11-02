package com.morgane.presentation;

import java.util.List;

/**
 * Interface of the view which will display the list of books.
 */
public interface BookListView {

    void displayLoading();

    void displayErrorMessage(String message);

    void displayBookList(List<BookViewModel> viewModel);
}
