package com.lilike.demo.homework02.ds;

import java.lang.annotation.*;

/**
 * @Author llk
 * @Date 2020/12/2 21:05
 * @Version 1.0
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReadOnly {
}
