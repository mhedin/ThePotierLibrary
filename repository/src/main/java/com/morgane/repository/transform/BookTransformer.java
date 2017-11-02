package com.morgane.repository.transform;

import com.morgane.repository.database.DatabaseBook;
import com.morgane.repository.network.JsonBook;

/**
 * This class is used to transform Json objects into Database objects.
 */
public class BookTransformer {

    public DatabaseBook toBook(JsonBook jsonBook) {
        final StringBuilder synopsis = new StringBuilder();
        for (String synopsisLine : jsonBook.getSynopsis()) {
            synopsis.append(synopsisLine);
        }
        return new DatabaseBook(jsonBook.getIsbn(), jsonBook.getTitle(), jsonBook.getPrice(), jsonBook.getCover(), synopsis.toString());
    }
}
