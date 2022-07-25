package com.qn.computersell.mapper;

import com.qn.computersell.entity.Order;
import com.qn.computersell.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    /**
     *
     * @param order
     * @return
     */
    Integer insertOrder(Order order);

    /**
     *
     * @param orderItem
     * @return
     */
    Integer insertOrderItem(OrderItem orderItem);
}
