package ca.dragz.firebase_demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.dragz.firebase_demo.Book;
import ca.dragz.firebase_demo.R;

public class BookListView {
    private Context mContext;
    private BooksAdapter mBooksAdapter;
    public void setConfig(RecyclerView rvBooks, Context context, List<Book> books, List<String> keys) {
        mContext = context;
        mBooksAdapter = new BooksAdapter(books, keys);
        rvBooks.setLayoutManager(new LinearLayoutManager(context));
        rvBooks.setAdapter(mBooksAdapter);
    }

    class BookItemView extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvAuthor;
        private TextView tvISBN;
        private TextView tvDescription;

        private String key;

        public BookItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.book_list_item, parent,false));

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvISBN = itemView.findViewById(R.id.tvISBN);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void bind(Book book, String key) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            tvISBN.setText(book.getIsbn());
            tvDescription.setText(book.getDescription());

            this.key = key;
        }
    }

    class BooksAdapter extends RecyclerView.Adapter<BookItemView> {
        private List<Book> mBookList;
        private List<String> mKeys;

        public BooksAdapter(List<Book> mBookList, List<String> mKeys) {
            this.mBookList = mBookList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public BookItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BookItemView holder, int position) {
            holder.bind(mBookList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }
    }
}
