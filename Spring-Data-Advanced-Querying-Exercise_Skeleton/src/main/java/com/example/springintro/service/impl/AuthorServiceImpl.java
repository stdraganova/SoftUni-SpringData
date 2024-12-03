package com.example.springintro.service.impl;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.dtos.AuthorDTO;
import com.example.springintro.model.entity.dtos.BookDTO;
import com.example.springintro.repository.AuthorRepository;
import com.example.springintro.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {

        this.authorRepository = authorRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(AUTHORS_FILE_PATH))
                .forEach(row -> {
                    String[] fullName = row.split("\\s+");
                    Author author = new Author(fullName[0], fullName[1]);

                    authorRepository.save(author);
                });
    }

    @Override
    public Author getRandomAuthor() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1,
                        authorRepository.count() + 1);

        return authorRepository
                .findById(randomId)
                .orElse(null);
    }

    @Override
    public List<String> getAllAuthorsOrderByCountOfTheirBooks() {
        return authorRepository
                .findAllByBooksSizeDESC()
                .stream()
                .map(author -> String.format("%s %s %d",
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBooks().size()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAuthorsWithNameStartWith(String s) {
        List<Author> authors = authorRepository.findAllByLastNameStartingWith(s);

        List<String> result = new ArrayList<>();

        for (Author author : authors) {
            Set<Book> books = author.getBooks();

            result = books
                    .stream()
                    .map(book -> String.format("%s (%s %s)", book.getTitle(), author.getFirstName(), author.getLastName()))
                    .collect(Collectors.toList());
        }

        Collections.sort(result);

        return result;
    }

    @Override
    public List<String> findAuthorsWithNameEndsWith(String s) {
        return List.of();
    }

    @Override
    public List<String> getBookCopiesCount() {

        List<AuthorDTO> list = authorRepository.findAllByFirstNameAndLastName()
                .stream().toList();

        List<String> result = new ArrayList<>();

        for (AuthorDTO authorDTO : list) {
            authorDTO = modelMapper.map(Author.class, AuthorDTO.class);
            String firstName = authorDTO.getFirstName();
            String lastName = authorDTO.getLastName();
            long count = authorDTO.getBooks().stream().map(BookDTO::getCopies).count();

            result.add(String.format("%s %s %d", firstName, lastName, count));
        }

        return result;
    }
}
