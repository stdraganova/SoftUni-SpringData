package bg.softUni.BookshopSystem.data.entities;

import bg.softUni.BookshopSystem.data.entities.enums.AgeRestriction;
import bg.softUni.BookshopSystem.data.entities.enums.EditionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends BaseEntity{

    @Column(name = "age_restriction" ,nullable = false)
    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;

    @Column(nullable = false)
    private int copies;

    @Column(length = 1000)
    private String description;

    @Column(name = "edition_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private EditionType editionType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(length = 50, nullable = false)
    private String title;

    @ManyToMany
    //Join is created automaticly if not provided
    @JoinTable(name = "books_categories",
          joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    public Book() {
        this.categories = new HashSet<>();
    }

    public Book(AgeRestriction ageRestriction, int copies, EditionType editionType, BigDecimal price, LocalDate releaseDate, String title) {
        this.ageRestriction = ageRestriction;
        this.copies = copies;
        this.editionType = editionType;
        this.price = price;
        this.releaseDate = releaseDate;
        this.title = title;
    }

    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
