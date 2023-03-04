package geektime.spring.springbucks;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.model.CoffeeOrder;
import geektime.spring.springbucks.model.OrderState;
import geektime.spring.springbucks.repository.CoffeeRepository;
import geektime.spring.springbucks.repository.mybatis.CoffeeInterface;
import geektime.spring.springbucks.repository.mybatis.CoffeeOrderInterface;
import geektime.spring.springbucks.repository.mybatis.OrderInterface;
import geektime.spring.springbucks.service.CoffeeOrderService;
import geektime.spring.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
//@EnableJpaRepositories
public class SpringBucksApplication implements ApplicationRunner {
	@Autowired
	private CoffeeRepository coffeeRepository;
	@Autowired
	private CoffeeService coffeeService;
	@Autowired
	private CoffeeOrderService orderService;

	@Autowired
	CoffeeInterface coffeeInterface;

	@Autowired
	OrderInterface orderInterface;

	@Autowired
	CoffeeOrderInterface coffeeOrderInterface;

	@Autowired
	RedisTemplate redisTemplate;
	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void run(ApplicationArguments args) throws Exception {
		Page<Coffee> coffes =PageHelper.startPage(1,2).doSelectPage(()->coffeeInterface.findAll());
		log.info("All Coffee: {}", coffes);
	    Coffee coffee = 	coffeeInterface.selectOne("Latte");
		redisTemplate.opsForValue().set("Latte", JSONObject.toJSON(coffee));
	    Object cc =redisTemplate.opsForValue().get("Latte");
		log.info("Latte Coffee: {}", cc);
		if(Optional.of(coffee).isPresent()){
			CoffeeOrder order = CoffeeOrder.builder()
					.customer("Li Lei")
					.items(new ArrayList<>(Arrays.asList(coffee)))
					.state(OrderState.INIT.ordinal())
					.build();
		 int id=	orderInterface.orderInsert(order);
			order.setId(Long.valueOf(id));
			coffeeOrderInterface.insert(Long.valueOf(id),order.getState());
			log.info("Update INIT to PAID: {}", orderService.updateState(order, OrderState.PAID));
			log.info("Update PAID to INIT: {}", orderService.updateState(order, OrderState.INIT));
		}
//		if (latte.isPresent()) {
//			//insert into t_order values(sysdate,sysdate,'LiLei',1);
//			CoffeeOrder order = orderService.createOrder("Li Lei", latte.get());
//			//
//			log.info("Update INIT to PAID: {}", orderService.updateState(order, OrderState.PAID));
//			log.info("Update PAID to INIT: {}", orderService.updateState(order, OrderState.BREWING));
//		}
	}


}

