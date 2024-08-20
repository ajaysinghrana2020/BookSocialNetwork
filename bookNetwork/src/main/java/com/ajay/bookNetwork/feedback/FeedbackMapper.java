package com.ajay.bookNetwork.feedback;

import com.ajay.bookNetwork.book.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {

    public Feedback toFeedback(FeedbackRequest feedbackRequest) {
        return  Feedback.builder()
                .note(feedbackRequest.note())
                .comment(feedbackRequest.comment())
                .book(Book.builder()
                        .id(feedbackRequest.bookId())
                        .archived(false)
                        .shareable(false)
                        .build())
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback f, Integer id) {
        return FeedbackResponse.builder()
                .note(f.getNote())
                .comment(f.getComment())
                .ownFeedback(Objects.equals(f.getCreatedBy(),id))
                .build();
    }
}
