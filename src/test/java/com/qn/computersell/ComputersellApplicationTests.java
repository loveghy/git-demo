package com.qn.computersell;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.sql.DataSource;


@SpringBootTest
class ComputersellApplicationTests {
    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() {

    }

    /*
     *  Hikari号称连接最快
     * */
    @Test
    void getConnection() throws Exception {
        System.out.println(dataSource.getConnection());
    }
}
