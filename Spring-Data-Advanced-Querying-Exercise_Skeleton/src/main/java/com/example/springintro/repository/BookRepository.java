package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.model.entity.dtos.AuthorDTO;
import org.aspectj.weaver.ast.And;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    @Query("SELECT b FROM Book AS b WHERE b.price NOT BETWEEN :lowerBoundPrice AND :upperBoundPrice")
    List<Book> findAllByPriceNotIn(BigDecimal lowerBoundPrice, BigDecimal upperBoundPrice);

    @Query("SELECT b FROM Book AS b WHERE YEAR(b.releaseDate) <> :year")
    List<Book> findAllByReleaseDateNot(int year);

    List<Book> findAllBooksByReleaseDateBefore(LocalDate localDate);

    List<Book> findAllByTitleContaining(String contained);

    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :length")
    int findAllByTitleWithLength(int length);
}
