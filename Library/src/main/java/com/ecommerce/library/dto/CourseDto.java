package com.ecommerce.library.dto;

import com.ecommerce.library.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String image;
    private String teacherName;
    private String linkCourse;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Category category;
}
