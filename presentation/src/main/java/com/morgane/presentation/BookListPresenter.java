package com.morgane.presentation;

import android.content.res.Resources;

import com.morgane.usecases.Book;
import com.morgane.usecases.BookListOutputPort;

import java.util.ArrayList;
import java.util.List;

/**
 * This class transform the book received to a presentable list of books.
 */
public class BookListPresenter implements BookListOutputPort {

    private final BookListView view;

    private final Resources resources;

    public BookListPresenter(BookListView view, Resources resources) {
        this.view = view;
        this.resources = resources;
    }

    @Override
    public void onGenericException() {
        view.displayErrorMessage(resources.getString(R.string.message_generic_error));
    }

    @Override
    public void onPendingRequest() {
        view.displayLoading();
    }

    @Override
    public void onBookListReceived(List<Book> bookList) {
        final List<BookViewModel> viewModelList = new ArrayList<>();
        final BookTransformer transformer = new BookTransformer();

        for (Book book : bookList) {
            viewModelList.add(transformer.toBookViewModel(book));
        }

        view.displayBookList(viewModelList);
    }
}
