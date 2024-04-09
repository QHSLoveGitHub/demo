package org.qhs.example.dao;

import org.qhs.example.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingListRepository extends JpaRepository<Book,Long> {

    /**增加根据读者查找书籍列表方法**/
    List<Book> findByReader(String reader);
}
