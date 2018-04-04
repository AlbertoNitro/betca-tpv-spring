package es.upm.miw.controllers;

import es.upm.miw.dtos.SchedulerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class SchedulerControllerIT {

    @Autowired
    private SchedulerController schedulerController;

    private SchedulerDto schedulerDto;

    @Before
    public void before(){
        this.schedulerDto = new SchedulerDto();
    }

    @Test
    public void createProvider(){
        //fail();

    }
}
