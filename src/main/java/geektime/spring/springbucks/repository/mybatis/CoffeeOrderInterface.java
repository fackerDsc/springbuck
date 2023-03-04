package geektime.spring.springbucks.repository.mybatis;

import geektime.spring.springbucks.model.CoffeeOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CoffeeOrderInterface  {

    @Insert("insert into t_order_coffee values (#{id},#{state})")
    int insert(Long id,int state);


    @Update(value = "update t_order_coffee set items_id =#{state} where coffee_order_id=#{id}  ")
    int update(int state ,Long id);
}
