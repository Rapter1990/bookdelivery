package com.example.demo.model.mapper.order;


import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.model.mapper.user.UserMapper;
import com.example.demo.payload.response.CustomPageResponse;
import com.example.demo.payload.response.order.OrderCreatedResponse;
import com.example.demo.payload.response.order.OrderGetBetweenDatesResponse;
import com.example.demo.payload.response.order.OrderGetByCustomerResponse;
import com.example.demo.payload.response.order.OrderGetResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

/**
 * Utility class for mapping operations related to {@link Order} and {@link OrderDTO}.
 */
@UtilityClass
public class OrderMapper {

    /**
     * Converts an {@link Order} object to an {@link OrderDTO}.
     *
     * @param source The {@link Order} object to be converted.
     * @return An {@link OrderDTO} containing data from the source object.
     */
    public static OrderDTO toOrderDTO(Order source) {
        return OrderDTO.builder()
                .id(source.getId())
                .user(UserMapper.toDTO(source.getUser()))
                .orderItems(OrderItemMapper.toDTO(source.getOrderItems()))
                .createdAt(source.getCreatedAt())
                .build();
    }

    /**
     * Converts an {@link OrderDTO} object to an {@link OrderGetResponse}.
     *
     * @param source The {@link OrderDTO} object to be converted.
     * @return An {@link OrderGetResponse} containing data from the source DTO.
     */
    public static OrderGetResponse toGetResponse(OrderDTO source) {
        return OrderGetResponse.builder()
                .id(source.getId())
                .user(source.getUser())
                .orderItems(source.getOrderItems())
                .createdAt(source.getCreatedAt())
                .build();
    }

    /**
     * Converts an {@link OrderDTO} object to an {@link OrderGetByCustomerResponse}.
     *
     * @param source The {@link OrderDTO} object to be converted.
     * @return An {@link OrderGetByCustomerResponse} containing data from the source DTO.
     */
    public static OrderGetByCustomerResponse toGetByCustomerResponse(OrderDTO source) {
        return OrderGetByCustomerResponse.builder()
                .id(source.getId())
                .user(source.getUser())
                .orderItems(source.getOrderItems())
                .createdAt(source.getCreatedAt())
                .build();
    }

    /**
     * Converts a {@link Page<OrderDTO>} to a {@link CustomPageResponse<OrderGetByCustomerResponse>}.
     *
     * @param sources The source {@link Page<OrderDTO>} to be converted.
     * @return A {@link CustomPageResponse<OrderGetByCustomerResponse>} containing converted data.
     */
    public static CustomPageResponse<OrderGetByCustomerResponse> toGetByCustomerResponse(Page<OrderDTO> sources) {
        return CustomPageResponse.of(sources.map(OrderMapper::toGetByCustomerResponse));
    }

    /**
     * Converts an {@link OrderDTO} object to an {@link OrderGetBetweenDatesResponse}.
     *
     * @param source The {@link OrderDTO} object to be converted.
     * @return An {@link OrderGetBetweenDatesResponse} containing data from the source DTO.
     */
    public static OrderGetBetweenDatesResponse toGetBetweenDatesResponse(OrderDTO source) {
        return OrderGetBetweenDatesResponse.builder()
                .id(source.getId())
                .user(source.getUser())
                .orderItems(source.getOrderItems())
                .createdAt(source.getCreatedAt())
                .build();
    }

    /**
     * Converts a {@link Page<OrderDTO>} to a {@link CustomPageResponse<OrderGetBetweenDatesResponse>}.
     *
     * @param sources The source {@link Page<OrderDTO>} to be converted.
     * @return A {@link CustomPageResponse<OrderGetBetweenDatesResponse>} containing converted data.
     */
    public static CustomPageResponse<OrderGetBetweenDatesResponse> toGetBetweenDatesResponses(Page<OrderDTO> sources) {
        return CustomPageResponse.of(sources.map(OrderMapper::toGetBetweenDatesResponse));
    }

    /**
     * Converts an {@link OrderDTO} object to an {@link Order}.
     *
     * @param orderDTO The {@link OrderDTO} object to be converted.
     * @return An {@link Order} object containing data from the source DTO.
     */
    public static Order toOrder(OrderDTO orderDTO) {
        return Order.builder()
                .id(orderDTO.getId())
                .user(UserMapper.toUser(orderDTO.getUser()))
                .orderItems(OrderItemMapper.toOrderItemSetList(orderDTO.getOrderItems()))
                .createdAt(orderDTO.getCreatedAt())
                .build();

    }

    /**
     * Converts an {@link OrderDTO} object to an {@link OrderCreatedResponse}.
     *
     * @param orderDTO The {@link OrderDTO} object to be converted.
     * @return An {@link OrderCreatedResponse} containing data from the source DTO.
     */
    public static OrderCreatedResponse toCreatedResponse(OrderDTO orderDTO) {

        return OrderCreatedResponse.builder()
                .id(orderDTO.getId())
                .orderItems(orderDTO.getOrderItems())
                .user(orderDTO.getUser())
                .createdAt(orderDTO.getCreatedAt())
                .build();
    }

}
