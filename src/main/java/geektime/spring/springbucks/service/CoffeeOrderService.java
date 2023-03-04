package geektime.spring.springbucks.service;

import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.model.CoffeeOrder;
import geektime.spring.springbucks.model.OrderState;
import geektime.spring.springbucks.repository.CoffeeOrderRepository;
import geektime.spring.springbucks.repository.mybatis.CoffeeOrderInterface;
import geektime.spring.springbucks.repository.mybatis.OrderInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Service
@Transactional
public class CoffeeOrderService {
    @Autowired
    private CoffeeOrderRepository orderRepository;
    @Autowired
    CoffeeOrderInterface createOrder;

    @Autowired
    OrderInterface orderInterface;

    public CoffeeOrder createOrder(String customer, Coffee...coffee) {
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(new ArrayList<>(Arrays.asList(coffee)))
                .state(OrderState.INIT.ordinal())
                .build();
        CoffeeOrder saved = orderRepository.save(order);
        log.info("New Order: {}", saved);
        return saved;
    }

    @Transactional
    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (state.ordinal()<order.getState()) {
            log.warn("Wrong State order: {}, {}", state, order.getState());
            return false;
        }
        order.setState(state.ordinal());
        orderInterface.update(order.getState(),order.getId());

       // orderRepository.save(order);

        createOrder.update(order.getState(),order.getId());

        log.info("Updated Order: {}", order);
        return true;
    }
}
