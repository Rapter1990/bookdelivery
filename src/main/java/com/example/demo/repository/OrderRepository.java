package com.example.demo.repository;

import com.example.demo.dto.OrderReportDTO;
import com.example.demo.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

/**
 * Repository interface for accessing and managing Order entities in the database.
 * This interface extends JpaRepository to provide basic CRUD operations for Order entities.
 * It also extends JpaSpecificationExecutor to support dynamic query creation using specifications.
 */
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    /**
     * Retrieves a page of orders associated with a specific user.
     *
     * @param userId        The unique identifier of the user.
     * @param orderPageable Pageable object specifying the page and sorting options.
     * @return A Page containing the orders associated with the user.
     */
    Page<Order> findAllByUserId(Long userId, Pageable orderPageable);

    /**
     * Retrieves a page of orders created within a specified time range.
     *
     * @param startDate     The start date of the time range.
     * @param endTime       The end date of the time range.
     * @param orderPageable Pageable object specifying the page and sorting options.
     * @return A Page containing the orders created within the specified time range.
     */
    Page<Order> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endTime, Pageable orderPageable);

    /**
     * <p>Retrieves a paginated list of order statistics for a specific customer.</p>
     * <p>
     * This method retrieves a paginated list of order statistics, including the following items:
     * <p>- Total count of orders</p>
     * <p>- Total count of unique books</p>
     * <p>- Total price</p>
     *
     * <p>The statistics are grouped by the month and year of order creation date, and are ordered by
     * year in descending order.</p>
     *
     * @param customerId The ID of the customer for whom the order statistics are to be retrieved.
     * @param pageable   The pagination information.
     * @return A {@link Page} of {@link OrderReportDTO} objects containing the order statistics.
     */
    @Query("""
        SELECT NEW com.example.demo.dto.OrderReportDTO
        (FUNCTION('MONTHNAME',o.createdAt),FUNCTION('YEAR',o.createdAt),COUNT(o.id),COUNT(b.id),SUM(b.price))
        FROM Order o INNER JOIN o.orderItems items INNER JOIN items.book b 
        WHERE (o.user.id = :customerId) 
        GROUP BY FUNCTION('MONTHNAME',o.createdAt),FUNCTION('YEAR',o.createdAt) 
        ORDER BY FUNCTION('YEAR', o.createdAt) DESC
    """)
    Page<OrderReportDTO> findOrderStatisticsByCustomerId(@Param("customerId") Long customerId, Pageable pageable);

    /**
     * <p>Retrieves a paginated list of order statistics for all customers.</p>
     * <p>
     * This method retrieves a paginated list of order statistics, including the following items:
     * <p>- Total count of orders</p>
     * <p>- Total count of unique books</p>
     * <p>- Total price</p>
     *
     * <p>The statistics are grouped by the month and year of order creation date, and are ordered by
     * year in descending order.</p>
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of {@link OrderReportDTO} objects containing the order statistics.
     */
    @Query("""
        SELECT NEW com.example.demo.dto.OrderReportDTO
        (FUNCTION('MONTHNAME',o.createdAt),FUNCTION('YEAR',o.createdAt),COUNT(o.id),COUNT(b.id),SUM(b.price))
        FROM Order o INNER JOIN o.orderItems items INNER JOIN items.book b 
        GROUP BY FUNCTION('MONTHNAME',o.createdAt),FUNCTION('YEAR',o.createdAt) 
        ORDER BY FUNCTION('YEAR', o.createdAt) DESC
    """
    )
    Page<OrderReportDTO> findAllOrderStatistics(Pageable pageable);

}
