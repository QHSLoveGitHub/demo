package org.qhs.example.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**读者**/
    @Column
    private String reader;
    /**读者**/
    @Column
    private String isbn;
    /**书名**/
    @Column
    private String title;
    /**作者**/
    @Column
    private String author;
    /**描述**/
    @Column
    private String description;

}
