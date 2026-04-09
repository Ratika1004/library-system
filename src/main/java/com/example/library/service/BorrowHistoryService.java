package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.BorrowHistory;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowHistoryRepository;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowHistoryService {

    @Autowired
    private BorrowHistoryRepository borrowHistoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public String borrowBook(int userId, int bookId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (user.isEmpty()) return "User not found";
        if (book.isEmpty()) return "Book not found";
        if (book.get().getAvailableCopies() <= 0) return "No copies available";

        Book b = book.get();
        b.setAvailableCopies(b.getAvailableCopies() - 1);
        bookRepository.save(b);

        BorrowHistory record = new BorrowHistory();
        record.setUser(user.get());
        record.setBook(b);
        record.setBorrowDate(LocalDate.now());
        record.setStatus(BorrowHistory.Status.BORROWED);
        borrowHistoryRepository.save(record);

        return "Book borrowed successfully";
    }

    public String returnBook(int userId, int bookId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) return "User not found";

        Optional<BorrowHistory> record = borrowHistoryRepository
                .findActiveRecord(user.get(), bookId, BorrowHistory.Status.BORROWED);
        if (record.isEmpty()) return "No active borrow record found";

        BorrowHistory h = record.get();
        h.setStatus(BorrowHistory.Status.RETURNED);
        h.setReturnDate(LocalDate.now());
        borrowHistoryRepository.save(h);

        Book b = h.getBook();
        b.setAvailableCopies(b.getAvailableCopies() + 1);
        bookRepository.save(b);

        return "Book returned successfully";
    }

    public List<BorrowHistory> getUserHistory(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(borrowHistoryRepository::findByUser).orElse(List.of());
    }

    public List<BorrowHistory> getAllBorrowed() {
        return borrowHistoryRepository.findByStatus(BorrowHistory.Status.BORROWED);
    }
}