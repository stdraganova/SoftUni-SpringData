package bg.softUni.BookshopSystem.service.implementations;

import bg.softUni.BookshopSystem.data.entities.Category;
import bg.softUni.BookshopSystem.data.repositories.CategoryRepository;
import bg.softUni.BookshopSystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    //Source Root Path of categories.txt from folder resources
    private static final String CATEGORIES_PATH = "src/main/resources/categories.txt";

    //Полето е файнал, понеже в Спринг имаме право само по една инстанция за всеки един клас
    private final CategoryRepository categoryRepository;

    //Подава се стойност на вече вдигнатият от Спринг обект, който е предварително запазем в паметта
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        Set<Category> categories = new HashSet<>();

        //read categories.txt and add them to the Set
        Files.readAllLines(Path.of(CATEGORIES_PATH))
                .stream()
                .filter(line -> !line.trim().isEmpty()) // line to not be empty
                .forEach(line -> {
                    Category category = new Category(line);
                    categories.add(category);
                });

        //Saves the Set of categories to the table in the DB
        //Flush, за да се отразят по-бързо промените
        this.categoryRepository.saveAllAndFlush(categories);

        System.out.printf("Count of imported categories %d%n", this.categoryRepository.count());
    }

    @Override
    public boolean isImported() {
        //Returns the count of existing entities
        return this.categoryRepository.count() > 0;
    }
}
