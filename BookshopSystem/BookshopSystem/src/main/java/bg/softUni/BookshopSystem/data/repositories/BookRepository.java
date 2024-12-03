package bg.softUni.BookshopSystem.data.repositories;

import bg.softUni.BookshopSystem.data.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    //= FROM Author WHERE releaseDate > :releaseDate
    Set<Book> findAllByReleaseDateAfter(LocalDate date);
    Set<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDesc(String firstName, String lastName);
}

