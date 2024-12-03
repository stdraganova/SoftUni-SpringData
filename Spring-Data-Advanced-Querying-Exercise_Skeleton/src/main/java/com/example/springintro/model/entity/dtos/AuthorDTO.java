package com.example.springintro.model.entity.dtos;

import com.example.springintro.model.entity.Book;

import java.util.Set;

public class AuthorDTO {
    private String firstName;
    private String lastName;
    private Set<BookDTO> books;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(Set<BookDTO> books) {
        this.books = books;
    }
}
