package geektime.spring.springbucks.controller;

import com.alibaba.fastjson.JSONObject;
import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.model.mybatisModle.CoffeeS;
import geektime.spring.springbucks.repository.mybatis.CoffeeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    CoffeeInterface coffeeInterface;

    @GetMapping(path = "/coffee/test/json",produces = {"application/json"})
    public Object getJsonCoffee(){
        CoffeeS coffee = coffeeInterface.selectByName("espresso");
        System.out.println(coffee);
        return JSONObject.toJSON(coffee);
    }

    @GetMapping(path = "/coffee/test/xml",produces = {"application/xml"})
    public Object getXmlCoffee(){
        CoffeeS coffee = coffeeInterface.selectByName("espresso");
        return coffee;
    }
}
