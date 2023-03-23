package geektime.spring.springbucks.model.mybatisModle;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

@Data
@XmlRootElement
public class CoffeeS {

    private int id;

    private Date create_time;

    private Date update_time;

    private String name;

    private String price;
}
