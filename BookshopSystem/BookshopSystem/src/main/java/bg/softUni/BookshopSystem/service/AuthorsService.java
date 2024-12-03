package bg.softUni.BookshopSystem.service;

import java.io.IOException;

public interface AuthorsService {

    void seedAuthors() throws IOException;
    boolean areAuthorsImported();
    void printAllAuthorsWithOneBookBefore1990();

    void printAllAuthorsWithDescCount();
}
