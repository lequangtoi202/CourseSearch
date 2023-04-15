package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Course;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
public class CategoryServiceImpl  implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getByName(String name) {
        return categoryRepository.getByName(name);
    }

    @Override
    public Category save(MultipartFile image, CategoryDto categoryDto) {
        try{
            Category category1 = new Category();
            if (image == null){
                category1.setImage(null);
            }else {
                imageUpload.uploadImage(image);
                category1.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            category1.setName(categoryDto.getName());
            category1.setDescription(categoryDto.getDescription());

            return categoryRepository.save(category1);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category update(MultipartFile image, CategoryDto categoryDto) {
        try{
            Category category = categoryRepository.getById(categoryDto.getId());
            if (image == null){
                category.setImage(null);
            }else {
                imageUpload.uploadImage(image);
                category.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());

            return categoryRepository.save(category);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CategoryDto getById(long id) {
        Category category = categoryRepository.getById(id);
        if (category != null){
            CategoryDto  categoryDto = new CategoryDto();
            categoryDto.setName(category.getName());
            categoryDto.setImage(category.getImage());
            categoryDto.setDescription(category.getDescription());
            categoryDto.setId(category.getId());
            return categoryDto;
        }
        return null;
    }

    @Override
    public Category getByCateId(long id) {
        return categoryRepository.getById(id);
    }

    @Override
    public boolean delete(Category category) {
        try{
            categoryRepository.delete(category);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
