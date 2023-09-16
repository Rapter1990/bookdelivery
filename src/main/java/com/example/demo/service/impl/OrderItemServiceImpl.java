package com.example.demo.service.impl;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.exception.orderitem.StockModifiedException;
import com.example.demo.model.Book;
import com.example.demo.model.OrderItem;
import com.example.demo.model.mapper.book.BookMapper;
import com.example.demo.model.mapper.order.OrderItemMapper;
import com.example.demo.payload.request.book.BookUpdateStockRequest;
import com.example.demo.payload.request.order.OrderItemRequest;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    private final BookService bookService;

    @Retryable(retryFor = StockModifiedException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    @Override
    public OrderItemDTO createOrderItem(OrderItemRequest orderDetailRequest) {

        final BookDTO bookDTO = bookService.getBookById(orderDetailRequest.getBookId());
        final Book book = BookMapper.toBook(bookDTO);

        boolean stockAvailable = bookService.isStockAvailable(bookDTO, orderDetailRequest.getAmount());

        if (stockAvailable) {

            final OrderItem orderItem = OrderItem.builder()
                    .book(book)
                    .build();

            BookUpdateStockRequest bookUpdateStockRequest = BookUpdateStockRequest.builder()
                    .stock(bookDTO.getStock() - orderDetailRequest.getAmount())
                    .build();

            bookService.updateBookStockById(bookDTO.getId(), bookUpdateStockRequest);

            return OrderItemMapper.toDTO(orderItem);
        }

        throw new StockModifiedException();
    }
}
