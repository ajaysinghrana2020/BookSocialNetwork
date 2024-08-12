package com.ajay.bookNetwork.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransectionHistory,Integer> {

    @Query(""" 
select history from BookTransectionHistory history
where history.user.id=: id
""")
    Page<BookTransectionHistory> findAllBorrowedBooks(Pageable pageable, Integer id);

    @Query(""" 
select history from BookTransectionHistory history
where history.book.owner.id= :userId
""")
    Page<BookTransectionHistory> findAllReturnedBooks(Pageable pageable, Integer userId);

    @Query("""
Select
(count(*)>0) as isBorrowed
From BookTransectionHistory history
where history.user.id=:userId
and history.book.id=:bookId
and history.returnApproved=false
""")
    boolean isAllReadyBorrowedByUser(Integer bookId, Integer id);


@Query("""
select transaction from BookTransectionHistory transaction
where transaction.user.id=:userId
and transaction.book.id=:bookId
and transaction.returned=false
and transaction.returnApproved=false
""")
Optional<BookTransectionHistory> findByBookIdAndUserId(Integer bookId, Integer userId);
}
