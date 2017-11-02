package com.morgane.thepotierlibrary;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import com.morgane.presentation.BookListPresenter;
import com.morgane.presentation.BookListView;
import com.morgane.presentation.BookViewModel;
import com.morgane.repository.network.BookListNetworkRepository;
import com.morgane.repository.network.NetworkModule;
import com.morgane.thepotierlibrary.databinding.ActivityBookListBinding;
import com.morgane.usecases.BookListInteractor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;

/**
 * This activity displays the content of the library, as a list of books.
 */
public class BookListActivity extends AppCompatActivity implements BookListView {

    /**
     * The URL used to download the content of the library.
     */
    private static final String BOOK_LIST_URL = "http://henri-potier.xebia.fr/";

    /**
     * Position of the view when the library is loading.
     */
    private static final int VIEW_CHILD_LOADING = 0;

    /**
     * Position of the view when the library is displayed.
     */
    private static final int VIEW_CHILD_BOOK_LIST = 1;

    /**
     * Position of the view when there is an error in the downloading of the library.
     */
    private static final int VIEW_CHILD_ERROR = 2;

    private ActivityBookListBinding binding;

    private ViewFlipper viewFlipper;

    private List<BookViewModel> bookInLibraryList;

    private List<BookViewModel> bookAddedToCartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_list);

        viewFlipper = findViewById(R.id.viewFlipper);

        bookAddedToCartList = new ArrayList<>();

        binding.setActivity(this);

        final NetworkModule networkModule = new NetworkModule();
        final HttpUrl httpUrl = HttpUrl.parse(BOOK_LIST_URL);
        final Retrofit retrofit = networkModule.getRetrofit(httpUrl);
        final BookListNetworkRepository repository = new BookListNetworkRepository(retrofit);
        final BookListPresenter presenter = new BookListPresenter(this, getResources());
        final BookListInteractor interactor = new BookListInteractor(presenter, repository);
        new Thread() {
            @Override
            public void run() {
                interactor.loadBookList();
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.default_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_see_cart:
                Intent intent = new Intent(this, CartActivity.class);
                intent.putExtra(CartActivity.EXTRA_BOOKS_ADDED_TO_CART, bookAddedToCartList.toArray());
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void displayLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewFlipper.setDisplayedChild(VIEW_CHILD_LOADING);
            }
        });
    }

    @Override
    public void displayErrorMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.setErrorMessage(message);
                viewFlipper.setDisplayedChild(VIEW_CHILD_ERROR);
            }
        });
    }

    @Override
    public void displayBookList(final List<BookViewModel> viewModel) {
        bookInLibraryList = viewModel;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final BookListAdapter adapter = new BookListAdapter(BookListActivity.this, viewModel);
                final RecyclerView recyclerView = findViewById(R.id.book_recyclerView);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(BookListActivity.this));
                recyclerView.addItemDecoration(new DividerItemDecoration(BookListActivity.this,
                        DividerItemDecoration.VERTICAL));
                viewFlipper.setDisplayedChild(VIEW_CHILD_BOOK_LIST);
            }
        });
    }

    public void onAddToCartClick(View view, int position) {

        // If the book is already in the cart, remove it, else add it
        if (bookAddedToCartList.contains(bookInLibraryList.get(position))) {
            bookAddedToCartList.remove(bookInLibraryList.get(position));
            ((ImageButton) view).setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);

        } else {
            bookAddedToCartList.add(bookInLibraryList.get(position));
            ((ImageButton) view).setImageResource(R.drawable.ic_checked);
        }
    }
}
