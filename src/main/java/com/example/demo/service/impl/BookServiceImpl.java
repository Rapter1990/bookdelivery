package com.example.demo.service.impl;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.model.mapper.book.BookMapper;
import com.example.demo.payload.request.BookCreateRequest;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookService;
import com.example.demo.util.Identity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final Identity identity;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    /**
     * Finds request owner User and return.
     *
     * @return
     */
    /*
    private User getUser() {
        return userRepository.findByUsername(identity.getUsername())
                .orElseThrow(
                        ()->new RuntimeException("User not found with given username: "+identity.getUsername()));
    }

     */

    /**
     * Creates a {@link Book} entitiy from {@link BookCreateRequest}
     * and return.
     *
     * @param request
     * @return
     */
    public Book createBook(
            BookCreateRequest request
    ) {
       //final User user = getUser();

       final Book bookEntityToBeSave = BookMapper.mapForSaving(request);

       bookEntityToBeSave.setVersion(0L);

       return bookRepository.save(bookEntityToBeSave);
    }


    /**
     * Returns {@link Book} by given bookId.
     *
     * @param bookId
     * @return
     */
    public Book getBookById(
            final String bookId
    ) {
        //final User user = getUser();

        return bookRepository.findById(bookId)
                .orElseThrow(
                        ()-> new RuntimeException("Book can not be found with given id: "+bookId));
    }
}
