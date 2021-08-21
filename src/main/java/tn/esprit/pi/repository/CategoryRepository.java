package tn.esprit.pi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.pi.entities.Category;
import tn.esprit.pi.entities.CategoryEnum;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>{

	public Category findByName(String name);
	Page<Category> findAll(Pageable pageable);
    Category findByCategoryType(CategoryEnum categoryType);
}
