package geektime.spring.springbucks.repository.mybatis;

import geektime.spring.springbucks.model.CoffeeOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderInterface {


    @Insert(value = "insert into t_order (create_time,update_time,customer,state) values (#{createTime},#{updateTime},#{customer},#{state})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int orderInsert(CoffeeOrder coffeeOrder);

    @Update(value = "update t_order set state =#{state} where id=#{id}")
    int update(int state , Long id);
}
