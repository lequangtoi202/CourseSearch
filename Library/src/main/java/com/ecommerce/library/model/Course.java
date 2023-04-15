package com.ecommerce.library.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;
    private String name;
    private String description;
    private double price;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    private String teacherName;
    private String linkCourse;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cate_id", referencedColumnName = "cate_id")
    private Category category;

}
