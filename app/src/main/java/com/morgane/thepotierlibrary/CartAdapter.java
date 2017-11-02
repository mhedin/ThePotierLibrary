package com.morgane.thepotierlibrary;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.morgane.presentation.BookViewModel;

import java.util.List;

/**
 * This adapter displays the books in the cart.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<BookViewModel> bookViewModelList;

    public CartAdapter(List<BookViewModel> bookViewModelList) {
        this.bookViewModelList = bookViewModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_book_in_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BookViewModel book = bookViewModelList.get(position);
        final ViewDataBinding binding = holder.getBinding();

        binding.setVariable(BR.book, book);
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return bookViewModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
