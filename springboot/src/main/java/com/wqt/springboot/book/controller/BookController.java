package com.wqt.springboot.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wqt.springboot.book.entity.Book;
import com.wqt.springboot.book.repository.BookRepository;

/**
 * @author iuShu
 * @date Mar 29, 2018 6:58:28 PM
 */
@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@RequestMapping(value = "/{reader}", method = RequestMethod.GET)
	public String getBooksByReader(@PathVariable("reader") String reader, Model model) {
		List<Book> books = bookRepository.findByReader(reader);
		if (books != null)
			model.addAttribute("books", books);
		return "book";
	}

	@RequestMapping(value = "/{reader}", method = RequestMethod.POST)
	public String addBooksToReader(@PathVariable("reader") String reader, Book book) {
		book.setReader(reader);
		bookRepository.save(book);
		return "redirect:/book/{reader}";
	}

}
