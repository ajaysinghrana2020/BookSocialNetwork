package com.ajay.bookNetwork.feedback;

import com.ajay.bookNetwork.book.Book;
import com.ajay.bookNetwork.book.BookRepository;
import com.ajay.bookNetwork.book.PageResponse;
import com.ajay.bookNetwork.exception.OperationNotPermittedException;
import com.ajay.bookNetwork.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final BookRepository bookRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    public Integer save(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(()-> new EntityNotFoundException("No Book Fount with id "+request.bookId()));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("you can not give feedback to archived or non shareable book");
        }
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            //throw an excption is that the operation is not permited
            throw new OperationNotPermittedException("You Can Not Give feedback to your Book");
        }
        Feedback feedback = feedbackMapper.toFeedback(request);
        return feedbackRepository.save(feedback).getId();
    }

    public PageResponse<FeedbackResponse> findAllFedbackByBooks(Integer bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        User user = (User) connectedUser.getPrincipal();
        Page<Feedback> feedbacks = feedbackRepository.findAllBookId(bookId,pageable);
        List<FeedbackResponse>feedbackResponses =feedbacks.stream()
                .map(f->feedbackMapper.toFeedbackResponse(f,user.getId()))
                .toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );
    }
}
