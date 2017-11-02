package com.morgane.thepotierlibrary;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.morgane.presentation.BookViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * This adapter displays each book of the library.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private List<BookViewModel> bookViewModelList;

    private BookListActivity activity;

    public BookListAdapter(BookListActivity activity, List<BookViewModel> bookViewModelList) {
        this.activity = activity;
        this.bookViewModelList = bookViewModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_book_in_list, parent, false);
        return new ViewHolder(view, (ImageView)view.findViewById(R.id.cover_imageView));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BookViewModel book = bookViewModelList.get(position);
        final ViewDataBinding binding = holder.getBinding();

        binding.setVariable(BR.position, position);
        binding.setVariable(BR.activity, activity);
        binding.setVariable(BR.book, book);
        binding.executePendingBindings();

        Picasso.with(activity).load(book.getCover()).resize(250, 600).centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bookViewModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        private ViewDataBinding binding;

        public ViewHolder(View itemView, ImageView imageView) {
            super(itemView);

            this.imageView = imageView;
            binding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
