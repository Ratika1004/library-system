package com.example.library.controller;

import com.example.library.model.BorrowHistory;
import com.example.library.service.BorrowHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrow")
@CrossOrigin(origins = "*")
public class BorrowHistoryController {

    @Autowired
    private BorrowHistoryService borrowHistoryService;

    @PostMapping
    public ResponseEntity<?> borrowBook(@RequestBody Map<String, Integer> body) {
        int userId = body.get("userId");
        int bookId = body.get("bookId");
        String result = borrowHistoryService.borrowBook(userId, bookId);
        if (result.equals("Book borrowed successfully")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestBody Map<String, Integer> body) {
        int userId = body.get("userId");
        int bookId = body.get("bookId");
        String result = borrowHistoryService.returnBook(userId, bookId);
        if (result.equals("Book returned successfully")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/history/{userId}")
    public List<BorrowHistory> getUserHistory(@PathVariable int userId) {
        return borrowHistoryService.getUserHistory(userId);
    }

    @GetMapping("/all")
    public List<BorrowHistory> getAllBorrowed() {
        return borrowHistoryService.getAllBorrowed();
    }
}