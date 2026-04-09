package com.example.library.service;

import com.example.library.repository.BorrowHistoryRepository;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowHistoryRepository borrowHistoryRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public String deleteBook(int id) {
        if (!bookRepository.existsById(id)) {
            return "Book not found";
        }

        borrowHistoryRepository.deleteByBookId(id);
        bookRepository.deleteById(id);
        return "Book deleted successfully";
    }

    public List<Book> searchBooks(String keyword) {
        return bookRepository
                .findByTitleContainingOrAuthorContainingOrCategoryContaining(
                        keyword, keyword, keyword);
    }
}