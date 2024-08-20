package com.ajay.bookNetwork.feedback;

import com.ajay.bookNetwork.book.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback")
public class FeedBackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Integer> saveFeedback(@RequestBody FeedbackRequest request,
                                                Authentication connectedUser) {
        return ResponseEntity.ok(feedbackService.save(request,connectedUser));
    }

    @GetMapping("/book/{book-id}")
    public  ResponseEntity<PageResponse<FeedbackResponse>> findAllFeedbackByBook(
            @PathVariable("book-id")Integer booId,
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name="size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
      return ResponseEntity.ok(feedbackService.findAllFedbackByBooks(booId,page,size,connectedUser));
    }


}
