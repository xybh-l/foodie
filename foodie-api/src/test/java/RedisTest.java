import com.xybh.Application;
import com.xybh.utils.RedisOperator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 14:43 2021/2/2
 * @Modified:
 */
@SpringBootTest(classes = Application.class)
public class RedisTest {
    @Autowired
    private RedisOperator redisOperator;


    @Test
    public void testGet(){
        redisOperator.set("name", "lanwang");
        System.out.println(redisOperator.get("name"));
    }
}
