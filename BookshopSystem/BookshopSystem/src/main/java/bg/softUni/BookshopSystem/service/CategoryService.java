package bg.softUni.BookshopSystem.service;

import java.io.IOException;

public interface CategoryService {

    void seedCategories() throws IOException;

    boolean isImported();
}
