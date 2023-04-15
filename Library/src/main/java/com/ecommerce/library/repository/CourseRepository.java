package com.ecommerce.library.repository;

import com.ecommerce.library.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select c from Course c where c.category.id = ?1")
    List<Course> getByCategoryId(long id);

    @Query("select c from Course c where c.name like %:name%")
    List<Course> getByName(@Param("name") String name);

    @Query("select c from Course c where c.name like %:kw% or c.category.name like %:kw%")
    List<Course> getByKeyword(@Param("kw") String kw);
}
