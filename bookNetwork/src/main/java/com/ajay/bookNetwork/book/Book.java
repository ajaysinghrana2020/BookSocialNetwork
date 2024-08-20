package com.ajay.bookNetwork.book;

import com.ajay.bookNetwork.common.BaseEntity;
import com.ajay.bookNetwork.feedback.Feedback;
import com.ajay.bookNetwork.history.BookTransactionHistory;
import com.ajay.bookNetwork.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.util.List;

@Entity
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Book extends BaseEntity {

    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean shareable;

    // one  user / owner id can be maped to many
    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;

    // one book can have many feedbacks
    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;


    @Transient
    public double getRate() {
        if (feedbacks == null || feedbacks.isEmpty()) {
            return 0.0;
        }
        var rate = this.feedbacks.stream()
                .mapToDouble(Feedback::getNote)
                .average()
                .orElse(0.0);
        double roundedRate = Math.round(rate * 10.0) / 10.0;

        // Return 4.0 if roundedRate is  less than 4.5,otherwise return 4.5 okk
        return roundedRate;
    }

}
