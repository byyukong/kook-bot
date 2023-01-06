package com.kook.util;
 
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.datasource.DataSourceFactory;
import javax.sql.DataSource;
import java.util.Properties;
 
public class MyDruidDataSourceFactory implements DataSourceFactory {
    private DataSource dataSource;

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    @Override
    public void setProperties(final Properties props) {
        try {
            this.dataSource = DruidDataSourceFactory.createDataSource(props);
        } catch (final RuntimeException e) {
            throw e;
        } catch (final Exception e) {
            throw new RuntimeException("init datasource error", e);
        }
    }
}