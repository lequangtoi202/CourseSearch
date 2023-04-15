package com.ecommerce.library.service;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Course;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    List<Category> getByName(String name);
    Category save(MultipartFile image, CategoryDto categoryDto);
    Category update(MultipartFile image, CategoryDto categoryDto);
    CategoryDto getById(long id);
    Category getByCateId(long id);
    boolean delete(Category category);
}
