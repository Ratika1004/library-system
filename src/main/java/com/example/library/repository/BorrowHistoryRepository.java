package com.example.library.repository;

import com.example.library.model.BorrowHistory;
import com.example.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Integer> {

    List<BorrowHistory> findByUser(User user);

    List<BorrowHistory> findByStatus(BorrowHistory.Status status);
    void deleteByBookId(int bookId);

    @Query("SELECT b FROM BorrowHistory b WHERE b.user = :user AND b.book.id = :bookId AND b.status = :status")
    Optional<BorrowHistory> findActiveRecord(
            @Param("user") User user,
            @Param("bookId") int bookId,
            @Param("status") BorrowHistory.Status status
    );
}