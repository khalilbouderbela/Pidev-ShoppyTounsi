package tn.esprit.pi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import tn.esprit.pi.entities.Category;
import tn.esprit.pi.entities.CategoryEnum;
import tn.esprit.pi.repository.CategoryRepository;

@Service

public class CategoryServiceImpl implements ICategoryService{
	
	@Autowired
	private  CategoryRepository categoryRepository;

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Optional<Category> getById(Long id) {
        return categoryRepository.findById(id);
    }


    @Override
    public void deleteById(long categoryId) {
        this.categoryRepository.deleteById(categoryId);
    }

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }


    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findByCategoryType(CategoryEnum categoryType) {
        return categoryRepository.findByCategoryType(categoryType);
    }

	
	@Override
	public List<Category> getAll() {
		
		return (List<Category>) this.categoryRepository.findAll();
	}

	

}
