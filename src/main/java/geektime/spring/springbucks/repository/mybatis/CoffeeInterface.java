package geektime.spring.springbucks.repository.mybatis;

import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.model.mybatisModle.CoffeeS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CoffeeInterface {


    @Select(value = "select * from t_coffee where name = #{name} limit 1")
    Coffee selectOne(@Param("name")String name);
    @Select(value = "select * from t_coffee where name = #{name}")
    CoffeeS selectByName(@Param("name")String name);
    @Select(value = "select * from t_coffee")
    List<Coffee> findAll();

}
