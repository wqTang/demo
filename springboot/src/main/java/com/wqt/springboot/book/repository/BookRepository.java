package com.wqt.springboot.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wqt.springboot.book.entity.Book;

/**
 * @author iuShu
 * @date Mar 29, 2018 6:55:26 PM
 */
public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByReader(String reader);

}
