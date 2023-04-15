package com.ecommerce.library.service;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.CourseDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Course;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    Course save(Course course);
    Course update(Course course);
    Course getById(long id);
    List<Course> findAll();
    List<Course> getByCategoryId(long id);
    List<Course> getByName(String name);
    List<Course> getByKeyword(String kw);
    Course save(MultipartFile image, CourseDto courseDto);
    Course update(MultipartFile image, CourseDto courseDto);
    CourseDto getByCourseId(long id);
    boolean delete(Course course);
}
