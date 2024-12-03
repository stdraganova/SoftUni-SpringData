package bg.softUni.BookshopSystem.data.entities;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    //creates the id column of all entities
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //if is String
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private Long id;

    protected BaseEntity() {

    }

    public Long getId() {
        return id;
    }
}
