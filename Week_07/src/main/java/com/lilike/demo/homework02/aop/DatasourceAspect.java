package com.lilike.demo.homework02.aop;

import com.lilike.demo.homework02.ds.DatasourceKeyHolder;
import com.lilike.demo.homework02.ds.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.naming.factory.DataSourceLinkFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * @Author llk
 * @Date 2020/12/2 21:06
 * @Version 1.0
 */
@Slf4j
@Component
@Aspect
public class DatasourceAspect {

    volatile int i = 0;

    /**
     * @param point
     * @return
     * @throws Throwable
     */
    @Before("@annotation(com.lilike.demo.homework02.ds.ReadOnly)")
    public Object getMethodExecuteTimeForLogger(JoinPoint point) throws Throwable {
        String loadblance = loadblance();

        log.info("发现这个是要只读的" + "===============" + loadblance);
        DatasourceKeyHolder.set(loadblance);
        return point;
    }

    public String loadblance() {
        List<String> slaveKeys = DatasourceKeyHolder.slaveKeys;
        synchronized (this) {
            if (i == slaveKeys.size()) {
                i = 0;
            }
            return slaveKeys.get(i++);
        }
    }

}
