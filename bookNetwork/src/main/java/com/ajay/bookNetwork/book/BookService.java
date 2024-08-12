package com.ajay.bookNetwork.book;
import com.ajay.bookNetwork.exception.OperationNotPermittedException;
import com.ajay.bookNetwork.history.BookTransactionHistoryRepository;
import com.ajay.bookNetwork.history.BookTransectionHistory;
import com.ajay.bookNetwork.user.User;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Server
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookTransectionHistory bookTransectionHistory;
    private final BookTransactionHistoryRepository bookTransactionHistoryRepository;

    public Integer save(BookRequest request, Authentication connectedUser) {
        User user= (User) connectedUser.getPrincipal();
        Book book = bookMapper.toBook(request);
        book.setOwner((user));
        return bookRepository.save(book).getId();
    }
    public BookResponse findByid(Integer bookId){
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(()-> new EntityNotFoundException("Book Not Found With Id"+bookId));
    }
    public PageResponse<BookResponse> findAllBooks(int page, int size ,Authentication auth){
        User user= (User) auth.getPrincipal();
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Book> books= bookRepository.findAllDisplayableBooks(pageable,user.getId());
        List<BookResponse> bookResponses =books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isEmpty()
        );
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user= (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
        //we are using specificaton but we can do by query also for learning
        Page<Book> books= bookRepository.findAll(BookSpecification.withOwnerId(user.getId()),pageable);
        List<BookResponse> bookResponses =books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isEmpty()
        );

    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransectionHistory> allBorrowedBooks = bookTransactionHistoryRepository.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> booksResponse = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user= (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
        Page<BookTransectionHistory> books= bookTransactionHistoryRepository.findAllReturnedBooks(pageable,user.getId());
        List<BorrowedBookResponse> bookResponses =books.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isEmpty()
        );
    }

    public Integer updateShareableStatus(Integer bookId, Authentication connectedUser) {
        Book book =bookRepository.findById(bookId)
                .orElseThrow(()->new EntityNotFoundException("Book Not Found With Id"+bookId));
        User user= (User) connectedUser.getPrincipal();
        if(!Objects.equals(book.getOwner().getId(),user.getId())){
            //throw an excption is that the operation is not permited
            throw new OperationNotPermittedException("Book Not belong to the user"+user.fullName());
        }
        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;
    }

    public Integer updateArchiveStatus(Integer bookId, Authentication connectedUser) {
        Book book =bookRepository.findById(bookId)
                .orElseThrow(()->new EntityNotFoundException("Book Not Found With Id"+bookId));
        User user= (User) connectedUser.getPrincipal();
        if(!Objects.equals(book.getOwner().getId(),user.getId())){
            //throw an excption is that the operation is not permited
            throw new OperationNotPermittedException("Book Not belong to the user"+user.fullName());
        }
        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        return bookId;
    }

    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        User user= (User) connectedUser.getPrincipal();
        Book book =bookRepository.findById(bookId)
                .orElseThrow(()->new EntityNotFoundException("Book Not Found With Id"+bookId));
        if(book.isArchived() || !book.isShareable() ){
            throw new OperationNotPermittedException("Requested Book can not be borrowed book is archived or not shareable");
        }
        if(!Objects.equals(book.getOwner().getId(),user.getId())){
            //throw an excption is that the operation is not permited
            throw new OperationNotPermittedException("You Can not borrow Your Own Book");
        }
        final boolean isAlroeadyBorrowed =bookTransactionHistoryRepository
                .isAllReadyBorrowedByUser(bookId,user.getId());
        if(isAlroeadyBorrowed){
            throw new OperationNotPermittedException("The requested Book is already borrowed");
        }
        BookTransectionHistory bookTransectionHistory =  BookTransectionHistory.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        return bookTransactionHistoryRepository.save(bookTransectionHistory).getId();


    }

    public Integer returnBorrowedBook(Integer bookId, Authentication connectedUser) {
        Book book =bookRepository.findById(bookId)
                .orElseThrow(()->new EntityNotFoundException("Book Not Found With Id"+bookId));
        if(book.isArchived() || !book.isShareable() ){
            throw new OperationNotPermittedException("Requested Book can not be borrowed book is archived or not shareable");
        }
        User user= (User) connectedUser.getPrincipal();
        if(!Objects.equals(book.getOwner().getId(),user.getId())){
            //throw an excption is that the operation is not permited
            throw new OperationNotPermittedException("You Can not borrow Your Own Book");
        }
        BookTransectionHistory bookTransectionHistory= bookTransactionHistoryRepository.findByBookIdAndUserId(bookId,user.getId())
                .orElseThrow(()->new OperationNotPermittedException("You Did Not Borrow This Book"));
        bookTransectionHistory.setReturned(true);
        return bookTransactionHistoryRepository
                .save(bookTransectionHistory)
                .getId();
    }
}
