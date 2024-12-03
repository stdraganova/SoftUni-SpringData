package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<Book> findeAllBooksByAgeRestriction(AgeRestriction s);

    List<String> findeAllGoldEditionBooksWithCopiesLessThan5000();

    List<String> findeAllBooksInPriceRange();

    List<String> findeAllBooksNotReleasedInYear(int year);

    List<String> findeAllBooksReleasedBeforDate(LocalDate localDate);

    List<String> findeAllBooksWithNameContainingString(String contained);

    String findeAllBooksWithTitleLength(int length);
}
