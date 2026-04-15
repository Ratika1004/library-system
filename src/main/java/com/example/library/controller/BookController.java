package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")

public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String q){
        return bookService.searchBooks(q);
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book,
                                     @RequestHeader(value = "X-User-Role", defaultValue = "") String role) {
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Access denied");
        }
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id,
                                        @RequestHeader(value = "X-User-Role", defaultValue = "") String role) {
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Access denied");
        }
        String result = bookService.deleteBook(id);
        if (result.equals("Book deleted successfully")) return ResponseEntity.ok(result);
        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id, @RequestBody Book book,
                                        @RequestHeader(value = "X-User-Role", defaultValue = "") String role) {
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Access denied");
        }
        String result = bookService.updateBook(id, book);
        if (result.equals("Book updated successfully")) return ResponseEntity.ok(result);
        return ResponseEntity.badRequest().body(result);
    }

}
