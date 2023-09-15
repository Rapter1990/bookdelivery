package com.example.demo.model.mapper.order;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderReportDTO;
import com.example.demo.payload.response.CustomPageResponse;
import com.example.demo.payload.response.order.OrderGetByCustomerResponse;
import com.example.demo.payload.response.order.OrderReportResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

@UtilityClass
public class OrderReportMapper {

    public static OrderReportResponse toOrderReportResponse(OrderReportDTO orderReportDTO) {
        return OrderReportResponse.builder()
                .year(orderReportDTO.getYear())
                .month(orderReportDTO.getMonth())
                .totalOrderCount(orderReportDTO.getTotalOrderCount())
                .totalBookCount(orderReportDTO.getTotalBookCount())
                .totalPrice(orderReportDTO.getTotalPrice())
                .build();
    }

    public static CustomPageResponse<OrderReportResponse> toOrderReportResponseList(Page<OrderReportDTO> sources) {
        return CustomPageResponse.of(sources.map(OrderReportMapper::toOrderReportResponse));
    }

}
