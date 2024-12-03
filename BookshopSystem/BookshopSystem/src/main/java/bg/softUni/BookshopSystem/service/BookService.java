package bg.softUni.BookshopSystem.service;

import java.io.IOException;

public interface BookService {

    void seedBooks() throws IOException;

    boolean areBooksImported();

    void printALlBooksAfter2000();

    void printAllBooksFromGeorge();
}
