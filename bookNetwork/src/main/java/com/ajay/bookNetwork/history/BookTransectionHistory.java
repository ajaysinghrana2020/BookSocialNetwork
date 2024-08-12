package com.ajay.bookNetwork.history;

import com.ajay.bookNetwork.book.Book;
import com.ajay.bookNetwork.common.BaseEntity;
import com.ajay.bookNetwork.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookTransectionHistory extends BaseEntity {

    // user relationships &&
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    // book relationships
    private boolean returned ;
    private boolean returnApproved;

}
