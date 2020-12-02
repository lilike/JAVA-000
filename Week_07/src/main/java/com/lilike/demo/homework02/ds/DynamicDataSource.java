package com.lilike.demo.homework02.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Author llk
 * @Date 2020/12/2 20:56
 * @Version 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DatasourceKeyHolder.get();
    }
}
