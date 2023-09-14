package com.example.demo.service.impl;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.exception.orderitem.StockModifiedException;
import com.example.demo.payload.request.book.BookUpdateStockRequest;
import com.example.demo.payload.request.order.OrderItemRequest;
import com.example.demo.service.BookService;
import com.example.demo.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final BookService bookService;

    // Optimistic Lock @Retry or @Retryable
    @Retryable(retryFor = StockModifiedException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    @Override
    public OrderItemDTO createOrderItem(OrderItemRequest orderDetailRequest) {

        // create OrderItemDTO

        // assign values from orderDetailRequest to OrderDTO

        // get BookDTO from bookId of orderDetailRequest

        // check if the stock of book is available

        // calculate price (amount)

        // update stock value of the book from BookService -> BookService

        // return OrderItemDTO

        BookDTO bookDTO = bookService.getBookById(orderDetailRequest.getBookId());

        boolean stockAvailable = bookService.isStockAvailable(bookDTO, orderDetailRequest.getAmount());

        if(stockAvailable){

            OrderItemDTO.OrderItemBook orderItemBook= OrderItemDTO.OrderItemBook.builder()
                    .isbn(bookDTO.getIsbn())
                    .price(bookDTO.getPrice().multiply(BigDecimal.valueOf(orderDetailRequest.getAmount())))
                    .authorFullName(bookDTO.getAuthorFullName())
                    .id(bookDTO.getId())
                    .build();

            BookUpdateStockRequest bookUpdateStockRequest = BookUpdateStockRequest.builder()
                    .stock(bookDTO.getStock() - orderDetailRequest.getAmount())
                    .build();

            bookService.updateBookStockById(bookDTO.getId(), bookUpdateStockRequest);

            return OrderItemDTO.builder()
                    .book(orderItemBook)
                    .build();
        }


        return null;
    }
}
