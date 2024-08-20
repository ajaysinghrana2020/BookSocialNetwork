package com.ajay.bookNetwork.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("""
Select f from Feedback f
where f.book.id=:bookId
""")
    Page<Feedback> findAllBookId(Integer bookId, Pageable pageable);
}
