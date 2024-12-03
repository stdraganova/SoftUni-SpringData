package bg.softUni.BookshopSystem.service.implementations;

import bg.softUni.BookshopSystem.data.entities.Author;
import bg.softUni.BookshopSystem.data.repositories.AuthorRepository;
import bg.softUni.BookshopSystem.service.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorsService {

    private static final String AUTHORS_PATH = "src/main/resources/authors.txt";
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {

        Set<Author> authors = new HashSet<>();

        Files.readAllLines(Path.of(AUTHORS_PATH))
                .stream()
                .filter(line -> !line.trim().isEmpty())
                .forEach(line -> {
                    String[] split = line.split("\\s+");
                    Author author = new Author(split[0], split[1]);
                    authors.add(author);
                });

        authorRepository.saveAllAndFlush(authors);
        System.out.printf("Count of imported authors %d%n", this.authorRepository.count());
    }

    @Override
    public boolean areAuthorsImported() {
        return this.authorRepository.count() > 0;
    }

    @Override
    public void printAllAuthorsWithOneBookBefore1990() {
        //With request JPQL
//        this.authorRepository.findAllByBooksReleaseDateBefore(LocalDate.of(1990,1,1))
//                .forEach(author -> System.out.printf("%s %s - %d%n", author.getFirstName(), author.getLastName(), author.getBooks().size()));


        //With Java Code
        this.authorRepository.findAll()
                .stream()
                .filter(author -> author.getBooks()
                            .stream()
                            .anyMatch(book -> book.getReleaseDate().isBefore(LocalDate.of(1990, 1, 1))))
                .forEach(author -> System.out.printf("%s %s%n", author.getFirstName(), author.getLastName()));
    }

    @Override
    public void printAllAuthorsWithDescCount() {
        this.authorRepository.findAll()
                .stream()
                .sorted((a, b) -> {
                   return b.getBooks().size() > a.getBooks().size() ? 1 : -1;
                })
                .forEach(author -> System.out.printf("%s %s - %d%n",author.getFirstName(), author.getLastName(), author.getBooks().size()));
    }
}
