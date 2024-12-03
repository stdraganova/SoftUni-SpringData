package bg.softUni.BookshopSystem.data.repositories;

import bg.softUni.BookshopSystem.data.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    //JpaRepository<Entity, Id.class>
}

/*
    Анотации над класовете
    @Controller - ще кореспондира с клиента (Мейн)
    @Service - Бизнес логика - Core Implementation
    @Repository - Persistence Layer -> DB logic
    @Component - Utils classes
    @Bean - Джава обект, който се контролира от спринг
    @MappedSuperclass - няма да му бъде създадена таблица, но ще му се ползват полетата в другите ентитита
    @Entity - Клас, който ще бъде създаден на таблица
 */