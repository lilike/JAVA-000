package annotation;

import java.lang.annotation.*;

/**
 * @Author llk
 * @Date 2020/12/19 10:09
 * @Version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyFeignClient {
}
