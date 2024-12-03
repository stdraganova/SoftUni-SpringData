package bg.softUni.BookshopSystem.service.implementations;

import bg.softUni.BookshopSystem.data.entities.Author;
import bg.softUni.BookshopSystem.data.entities.Book;
import bg.softUni.BookshopSystem.data.entities.Category;
import bg.softUni.BookshopSystem.data.entities.enums.AgeRestriction;
import bg.softUni.BookshopSystem.data.entities.enums.EditionType;
import bg.softUni.BookshopSystem.data.repositories.AuthorRepository;
import bg.softUni.BookshopSystem.data.repositories.BookRepository;
import bg.softUni.BookshopSystem.data.repositories.CategoryRepository;
import bg.softUni.BookshopSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_PATH = "src/main/resources/books.txt";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedBooks() throws IOException {
        Set<Book> books = new HashSet<>();

        Files.readAllLines(Path.of(BOOKS_PATH))
                .stream()
                .filter(line -> !line.isBlank())
                .forEach(line -> {
                    String[] split = line.split("\\s+");
                    Book book = new Book(
                            AgeRestriction.values()[Integer.parseInt(split[4])],
                            Integer.parseInt(split[2]),
                            EditionType.values()[Integer.parseInt(split[0])],
                            new BigDecimal(split[3]),
                            LocalDate.parse(split[1], DateTimeFormatter.ofPattern("d/M/yyyy")),
                            Arrays.stream(split).skip(5).collect(Collectors.joining(" "))
                    );

                    book.setAuthor(randomAuthor());
                    book.setCategories(randomCategories());
                    books.add(book);
                });

        bookRepository.saveAllAndFlush(books);
        System.out.printf("Count of imported books %d%n", this.bookRepository.count());
    }

    private Set<Category> randomCategories() {
        Set<Category> categories = new HashSet<>();
        long count = ThreadLocalRandom.current().nextLong(1, 4);

        for (int i = 0; i < count; i++) {
            long idCategory = ThreadLocalRandom.current().nextLong(1, this.categoryRepository.count() + 1);
            categories.add(this.categoryRepository.findById(idCategory).get());
        }

        return categories;
    }

    private Author randomAuthor() {

        Long id = ThreadLocalRandom.current().nextLong(1, this.authorRepository.count() + 1);
        Optional<Author> byId = this.authorRepository.findById(id);

        return byId.orElse(null);
    }

    @Override
    public boolean areBooksImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public void printALlBooksAfter2000() {
        this.bookRepository.findAllByReleaseDateAfter(LocalDate.of(2000,12,31))
                .forEach(book -> System.out.printf("%s%n", book.getTitle()));
    }

    @Override
    public void printAllBooksFromGeorge() {
        this.bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDesc("George", "Powell")
                .forEach(book -> System.out.printf("%s %s %d%n", book.getTitle(), book.getReleaseDate(), book.getCopies()));
    }
}
