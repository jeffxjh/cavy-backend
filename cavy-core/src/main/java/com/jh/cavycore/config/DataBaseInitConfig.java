package com.jh.cavycore.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

@Configuration
public class DataBaseInitConfig {
    private static final Logger LOG = LoggerFactory.getLogger(DataBaseInitConfig.class);

    private static final String SCHEMA_NAME = "schema_name";

    /**
     * com.mysql.cj.jdbc.Driver
     */
    //@Value("${spring.datasource.druid.driverClassName}")
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    /**
     * jdbc_url
     */
    //@Value("${spring.datasource.druid.url}")
    @Value("${spring.datasource.url}")
    private String url;
    /**
     * 账号名称
     */
    //@Value("${spring.datasource.druid.username}")
    @Value("${spring.datasource.username}")
    private String username;
    /**
     * 账号密码
     */
    //@Value("${spring.datasource.druid.password}")
    @Value("${spring.datasource.password}")
    private String password;
    /**
     * 需要创建的数据名称
     */
    @Value("${cavy.init-database}")
    private String initDatabase;
    /**
     * 创建的数据库文件名
     */
    @Value("${cavy.init-file}")
    private String initFile;

    @PostConstruct
    public void init() {
        URI uri = null;
        try {
            Class.forName(driver);
            uri = new URI(url.replace("jdbc:", ""));
        } catch (ClassNotFoundException | URISyntaxException e) {
            LOG.error("JDBC URL解析错误", e);
        }
        String host = uri.getHost();
        int port = uri.getPort();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port +
                                                                         "?useUnicode=true&characterEncoding=UTF-8&useSSL=false", username, password);
             Statement statement = connection.createStatement()) {
            String sal = "select schema_name from information_schema.schemata where schema_name = " + "'" + initDatabase + "'";

            //查询返回的结果集
            ResultSet resultSet = statement.executeQuery(sal);
            if (!resultSet.next()) {
                //查不到数据库，执行数据库初始化脚本
                LOG.info("未初始化数据库({})", initDatabase);
                InputStream inputStream = this.getClass().getResourceAsStream("/db/" + initFile + ".sql");
                if (inputStream == null) {
                    LOG.error("数据库初始化文件不存在,/db/{}.sql 请检查该目录文件", initFile);
                } else {
                    ByteArrayOutputStream result = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;
                    String createDb = "CREATE DATABASE IF NOT EXISTS " + initDatabase;
                    connection.setAutoCommit(false);
                    statement.execute(createDb);
                    connection.commit();
                    LOG.info("创建数据库（{}）成功", initDatabase);
                    try (
                            Connection connection2 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + initDatabase +
                                                                                         "?useUnicode=true&characterEncoding=UTF-8&useSSL=false", username, password);
                            Statement statement2 = connection2.createStatement();
                    ) {
                        while ((length = inputStream.read(buffer)) != -1) {
                            result.write(buffer, 0, length);
                        }
                        String initSql = result.toString("UTF-8");
                        String[] sqlSplit = initSql.split(";");
                        for (String sql : sqlSplit) {
                            if (StringUtils.isNotBlank(sql)) {
                                statement2.execute(sql);
                            }
                        }
                        LOG.info("执行数据库初始化脚本（{}.sql）成功", initFile);
                    } catch (Exception e) {
                        LOG.error("初始化系统数据库失败", e);
                    }
                }

            } else {
                String databaseName = resultSet.getString(SCHEMA_NAME);
                LOG.info("已初始化数据库({})", databaseName);
            }
            if (resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOG.error("启动项目检查数据库是否创建", e);
        }

    }
}