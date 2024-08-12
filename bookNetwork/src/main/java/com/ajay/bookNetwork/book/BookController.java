package com.ajay.bookNetwork.book;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name="Book")
public class BookController {


    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Integer> saveBook(
            @Valid @RequestBody BookRequest request, Authentication connectedUser) {
        return ResponseEntity.ok(bookService.save(request,connectedUser));
    }

    @GetMapping("{book_id}")
    public ResponseEntity<BookResponse> findBookById(
            @PathVariable("book_id") Integer bookId) {
        return ResponseEntity.ok(bookService.findByid(bookId));

    }
    @GetMapping
    public ResponseEntity<PageResponse <BookResponse>> findAllBooks(
        @RequestParam(name = "page",defaultValue = "0",required = false) int page,
        @RequestParam(name="size", defaultValue = "10", required = false) int size,
        Authentication connectedUser){

        return ResponseEntity.ok(bookService.findAllBooks(page,size,connectedUser));
    }
    @GetMapping("/user-id")
    public ResponseEntity<PageResponse <BookResponse>> findAllBookByOwner(
            @RequestParam(name ="page", defaultValue = "0",required = false)int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser){
        return ResponseEntity.ok(bookService.findAllBooksByOwner(page,size,connectedUser));
    }

    @GetMapping("/borrowdbook")
    public ResponseEntity<PageResponse <BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name ="page", defaultValue = "0",required = false)int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser){
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page,size,connectedUser));
    }

    @GetMapping("/borrowdbook")
    public ResponseEntity<PageResponse <BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name ="page", defaultValue = "0",required = false)int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser){
        return ResponseEntity.ok(bookService.findAllReturnedBooks(page,size,connectedUser));
    }

    @PatchMapping("/shareable/{book_id")
    public ResponseEntity<Integer> updateSherableStatus(
        @PathVariable("book_id") Integer bookId,
        Authentication connectedUser
       ){
        return ResponseEntity.ok(bookService.updateShareableStatus(bookId,connectedUser));
    }

    @PatchMapping("/archived/{book_id")
    public ResponseEntity<Integer> updateArchivedStatus(
            @PathVariable("book_id") Integer bookId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.updateArchiveStatus(bookId,connectedUser));
    }

    @PostMapping("borrow/{book-id}")
    public ResponseEntity<Integer> borrowBook(
            @PathVariable ("book-id") Integer bookId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.borrowBook(bookId,connectedUser));
    }

    @PatchMapping("/borrow/return/{book-id}")
    public ResponseEntity<Integer> returnBorrowedBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.returnBorrowedBook(bookId,connectedUser));
    }

}
