package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.dtos.AuthorDTO;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in);
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;

    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        //printAllBooksAfterYear(2000);
        //printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
        //printAllAuthorsAndNumberOfTheirBooks();
        //pritnALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");
        //printAllBooksWithMatchingAgeRestriction(scanner);
        //printAllGoldEditionBooksWIthLessThan5000Copies();
        //printAllBooksByPrice();
        //printNotReleasedBooksInGivenYear(scanner);
        //printAllBooksReleasedBeforDate(scanner);
        //printAllAuthorsWithNameEndsWith(scanner);
        //printAllBooksThatContainTheString(scanner);
        //printAllBooksWithAuthorsLastNameStartingWith(scanner);
        //printBooksByTitleWithLength(scanner);
        printTotalBookCopies();

    }

    private void printTotalBookCopies() {
        authorService.getBookCopiesCount()
                .forEach(System.out::println);
    }

    private void printBooksByTitleWithLength(Scanner scanner) {
        int length = Integer.parseInt(scanner.nextLine());
        System.out.println(bookService.findeAllBooksWithTitleLength(length));
    }

    private void printAllBooksWithAuthorsLastNameStartingWith(Scanner scanner) {
        String beginningOfLastName = scanner.nextLine();
        authorService.findAuthorsWithNameStartWith(beginningOfLastName)
                .forEach(System.out::println);
    }

    private void printAllBooksThatContainTheString(Scanner scanner) {
        String contained = scanner.nextLine();
        bookService.findeAllBooksWithNameContainingString(contained)
                .forEach(System.out::println);
    }

    private void printAllAuthorsWithNameEndsWith(Scanner scanner) {
        authorService.findAuthorsWithNameEndsWith(scanner.nextLine())
                .forEach(System.out::println);
    }

    private void printAllBooksReleasedBeforDate(Scanner scanner) {
        LocalDate localDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        bookService.findeAllBooksReleasedBeforDate(localDate)
                .forEach(System.out::println);
    }

    private void printNotReleasedBooksInGivenYear(Scanner scanner) {
        int year = Integer.parseInt(scanner.nextLine());
        bookService.findeAllBooksNotReleasedInYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksByPrice() {
        bookService.findeAllBooksInPriceRange()
                .forEach(System.out::println);
    }

    private void printAllGoldEditionBooksWIthLessThan5000Copies() {
        bookService.findeAllGoldEditionBooksWithCopiesLessThan5000()
                .forEach(System.out::println);
    }

    private void printAllBooksWithMatchingAgeRestriction(Scanner scanner) {
        //AgeRestriction ageRestriction = AgeRestriction.valueOf(scanner.nextLine().toUpperCase());
        AgeRestriction ageRestriction = AgeRestriction.valueOf(scanner.nextLine().toUpperCase());
        bookService.findeAllBooksByAgeRestriction(ageRestriction)
                .forEach(book -> System.out.printf("%s%n", book.getTitle()));
    }

    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
