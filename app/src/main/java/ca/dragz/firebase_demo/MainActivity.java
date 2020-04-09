package ca.dragz.firebase_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private Book book;

    private RecyclerView rvBooks;

    private EditText txtTitle;
    private EditText txtAuthor;
    private EditText txtISBN;
    private EditText txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        rvBooks = findViewById(R.id.rvBooks);

        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtISBN = findViewById(R.id.txtISBN);
        txtDescription = findViewById(R.id.txtDescription);
        Button btnAddBook = findViewById(R.id.btnAddBook);
        btnAddBook.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnAddBook) {
                    saveBook();
                }
            }
        });

        new FirebaseDatabaseHelper().readBooks(new FirebaseDatabaseHelper.BookDataStatus() {
            @Override
            public void BookDataIsLoaded(List<Book> books, List<String> keys) {
                new BookListView().setConfig(rvBooks, MainActivity.this, books, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    private void saveBook() {
        String title = txtTitle.getText().toString();
        String author = txtAuthor.getText().toString();
        String isbn = txtISBN.getText().toString();
        String description = txtDescription.getText().toString();

        if (title.trim().length() > 0 && author.trim().length() > 0 && isbn.trim().length() > 0 && description.trim().length() > 0) {
            book = new Book(title, author, isbn, description);
            String bookId = mDatabase.push().getKey();
            mDatabase.child("books").child(bookId).setValue(book);
            Toast.makeText(MainActivity.this, "Book creation successful!", Toast.LENGTH_LONG).show();
            clearInputs();
        } else {
            Toast.makeText(MainActivity.this, "All fields must be filled before submitting...", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputs() {
        txtTitle.setText("");
        txtAuthor.setText("");
        txtISBN.setText("");
        txtDescription.setText("");
    }
}
