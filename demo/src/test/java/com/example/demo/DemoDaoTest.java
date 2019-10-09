package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoDaoTest {
    @Autowired
    private DemoDao demoDao;

    @Test
    public void test() {
        DemoEntity entity = new DemoEntity();
        entity.setUuid(3);

        demoDao.save(entity);
    }
}
