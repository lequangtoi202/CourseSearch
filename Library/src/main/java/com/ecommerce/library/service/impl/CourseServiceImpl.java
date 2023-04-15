package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CourseDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Course;
import com.ecommerce.library.repository.CourseRepository;
import com.ecommerce.library.service.CourseService;
import com.ecommerce.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ImageUpload imageUpload;
    @Override
    public Course save(Course course) {
        course.setCreated_at(LocalDateTime.now());
        course.setUpdated_at(LocalDateTime.now());
        return courseRepository.save(course);
    }

    @Override
    public Course update(Course course) {
        Course courseSaved = courseRepository.getById(course.getId());
        if (courseSaved != null){
            courseSaved.setCategory(course.getCategory());
            courseSaved.setDescription(course.getDescription());
            courseSaved.setUpdated_at(LocalDateTime.now());
            courseSaved.setPrice(course.getPrice());
            courseSaved.setEndDate(course.getEndDate());
            courseSaved.setStartDate(course.getStartDate());
            courseSaved.setLinkCourse(course.getLinkCourse());
            courseSaved.setTeacherName(course.getTeacherName());
            return courseRepository.save(courseSaved);
        }
        return null;
    }

    @Override
    public Course getById(long id) {
        return courseRepository.getById(id);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getByCategoryId(long id) {
        return courseRepository.getByCategoryId(id);
    }

    @Override
    public List<Course> getByName(String name) {
        return courseRepository.getByName(name);
    }

    @Override
    public List<Course> getByKeyword(String kw) {
        return courseRepository.getByKeyword(kw);
    }

    @Override
    public Course save(MultipartFile image, CourseDto courseDto) {
        try{
            Course course = new Course();
            if (image == null){
                course.setImage(null);
            }else {
                imageUpload.uploadImage(image);
                course.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            course.setName(courseDto.getName());
            course.setDescription(courseDto.getDescription());
            course.setLinkCourse(courseDto.getLinkCourse());
            course.setTeacherName(courseDto.getTeacherName());
            course.setPrice(courseDto.getPrice());
            course.setStartDate(courseDto.getStartDate());
            course.setEndDate(courseDto.getEndDate());
            course.setCategory(courseDto.getCategory());
            course.setUpdated_at(LocalDateTime.now());
            course.setCreated_at(LocalDateTime.now());

            return courseRepository.save(course);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Course update(MultipartFile image, CourseDto courseDto) {
        try{
            Course course = courseRepository.getById(courseDto.getId());
            if (image == null){
                course.setImage(null);
            }else {
                imageUpload.uploadImage(image);
                course.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            course.setName(courseDto.getName());
            course.setDescription(courseDto.getDescription());
            course.setLinkCourse(courseDto.getLinkCourse());
            course.setTeacherName(courseDto.getTeacherName());
            course.setPrice(courseDto.getPrice());
            course.setStartDate(courseDto.getStartDate());
            course.setEndDate(courseDto.getEndDate());
            course.setCategory(courseDto.getCategory());
            course.setUpdated_at(LocalDateTime.now());

            return courseRepository.save(course);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CourseDto getByCourseId(long id) {
        Course course = courseRepository.getById(id);
        if (course != null){
            CourseDto courseDto = new CourseDto();
            courseDto.setId(course.getId());
            courseDto.setName(course.getName());
            courseDto.setDescription(course.getDescription());
            courseDto.setLinkCourse(course.getLinkCourse());
            courseDto.setTeacherName(course.getTeacherName());
            courseDto.setPrice(course.getPrice());
            courseDto.setStartDate(course.getStartDate());
            courseDto.setEndDate(course.getEndDate());
            courseDto.setCategory(course.getCategory());
            courseDto.setUpdated_at(course.getUpdated_at());
            courseDto.setCreated_at(course.getCreated_at());
            return courseDto;
        }
        return null;
    }


    @Override
    public boolean delete(Course course) {
        try{
            courseRepository.delete(course);
        }catch (Exception e){
            return false;
        }
        return true;
    }

}
