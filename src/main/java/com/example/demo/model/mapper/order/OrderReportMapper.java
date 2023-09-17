package com.example.demo.model.mapper.order;

import com.example.demo.dto.OrderReportDTO;
import com.example.demo.payload.response.CustomPageResponse;
import com.example.demo.payload.response.order.OrderReportResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

/**
 * Utility class for mapping operations related to {@link OrderReportDTO} and {@link OrderReportResponse}.
 */
@UtilityClass
public class OrderReportMapper {

    /**
     * Converts an {@link OrderReportDTO} object to an {@link OrderReportResponse}.
     *
     * @param orderReportDTO The {@link OrderReportDTO} object to be converted.
     * @return An {@link OrderReportResponse} containing data from the source DTO.
     */
    public static OrderReportResponse toOrderReportResponse(OrderReportDTO orderReportDTO) {
        return OrderReportResponse.builder()
                .year(orderReportDTO.getYear())
                .month(orderReportDTO.getMonth())
                .totalOrderCount(orderReportDTO.getTotalOrderCount())
                .totalBookCount(orderReportDTO.getTotalBookCount())
                .totalPrice(orderReportDTO.getTotalPrice())
                .build();
    }

    /**
     * Converts a {@link Page<OrderReportDTO>} to a {@link CustomPageResponse<OrderReportResponse>}.
     *
     * @param sources The source {@link Page<OrderReportDTO>} to be converted.
     * @return A {@link CustomPageResponse<OrderReportResponse>} containing converted data.
     */
    public static CustomPageResponse<OrderReportResponse> toOrderReportResponseList(Page<OrderReportDTO> sources) {
        return CustomPageResponse.of(sources.map(OrderReportMapper::toOrderReportResponse));
    }

}
