package bg.softUni.BookshopSystem.controller;

import bg.softUni.BookshopSystem.service.AuthorsService;
import bg.softUni.BookshopSystem.service.BookService;
import bg.softUni.BookshopSystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsoleLineRunner implements CommandLineRunner {

    private final BookService bookService;
    private final AuthorsService authorsService;
    private final CategoryService categoryService;

    @Autowired
    public ConsoleLineRunner(BookService bookService, AuthorsService authorsService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorsService = authorsService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
         //logic UI
        //seed methods -> services
        seedData();

        //SQL requests
        this.bookService.printALlBooksAfter2000();
        this.authorsService.printAllAuthorsWithOneBookBefore1990();
        this.authorsService.printAllAuthorsWithDescCount();
        this.bookService.printAllBooksFromGeorge();
    }

    private void seedData() throws IOException {
        if (!this.categoryService.isImported()) {
            this.categoryService.seedCategories();
        }

        if (!this.authorsService.areAuthorsImported()) {
            this.authorsService.seedAuthors();
        }

        if (!this.bookService.areBooksImported()) {
            this.bookService.seedBooks();
        }
    }
}
