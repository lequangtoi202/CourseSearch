package com.ecommerce.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_id")
    private Long id;
    private String name;
    private String description;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @OneToMany(mappedBy = "category")
    List<Course> courses;
}
