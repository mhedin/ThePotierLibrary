package com.morgane.presentation;

import com.morgane.usecases.Book;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(RobolectricTestRunner.class)
public class BookListPresenterTest {

    @Mock
    private BookListView view;

    private BookListPresenter presenter;

    @Captor
    private ArgumentCaptor<List<BookViewModel>> viewModelCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new BookListPresenter(view, RuntimeEnvironment.application.getResources());
    }

    @Test
    public void onPendingRequest() {
        presenter.onPendingRequest();

        then(view).should().displayLoading();
    }

    @Test
    public void onGenericException() {
        presenter.onGenericException();

        then(view).should().displayErrorMessage("Veuillez réessayer plus tard");
    }

    @Test
    public void onBookListReceived() {
        List<Book> books = new ArrayList<>();
        books.add(getMockedBook("1234", "titre", 2, "http://test.fr"));
        books.add(getMockedBook("5678", "autre titre", 3, "http://testv2.fr"));

        presenter.onBookListReceived(books);

        then(view).should().displayBookList(viewModelCaptor.capture());
        final List<BookViewModel> viewModelList = viewModelCaptor.getValue();

        assertThat(viewModelList.get(0).getIsbn()).isEqualTo("1234");
        assertThat(viewModelList.get(0).getTitle()).isEqualTo("titre");
        assertThat(viewModelList.get(0).getPrice()).isEqualTo("2 €");
        assertThat(viewModelList.get(1).getIsbn()).isEqualTo("5678");
        assertThat(viewModelList.get(1).getTitle()).isEqualTo("autre titre");
        assertThat(viewModelList.get(1).getPrice()).isEqualTo("3 €");
    }

    private Book getMockedBook(String isbn, String title, int price, String cover) {
        Book book = Mockito.mock(Book.class);

        given(book.getIsbn()).willReturn(isbn);
        given(book.getTitle()).willReturn(title);
        given(book.getPrice()).willReturn(price);
        given(book.getCover()).willReturn(cover);

        return book;
    }
}
