package com.example.library.service;

import com.example.library.repository.BorrowHistoryRepository;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public String updateBook(int id, Book updatedBook) {
        Optional<Book> existing = bookRepository.findById(id);
        if (existing.isEmpty()) return "Book not found";

        Book book = existing.get();
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setCategory(updatedBook.getCategory());
        book.setTotalCopies(updatedBook.getTotalCopies());
        book.setAvailableCopies(updatedBook.getAvailableCopies());
        bookRepository.save(book);
        return "Book updated successfully";
    }
}