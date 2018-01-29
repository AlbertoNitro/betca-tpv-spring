package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.users.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class TicketRepositoryIT {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void testFindTicketMobile() {
        assertEquals(666666004, ticketRepository.findByReference("t2j_u9M9CisFmYGRFs1Uulgn7hI").getUser().getMobile());
    }

    @Test
    public void testFindTicketTotal() {
        assertEquals(151, ticketRepository.findByReference("t2j_u9M9CisFmYGRFs1Uulgn7hI").getTicketTotal().doubleValue(), 10E-5);
    }

    @Test
    public void testFindByUserOrderByCreationDateDesc() {
        User user = ticketRepository.findByReference("t2j_u9M9CisFmYGRFs1Uulgn7hI").getUser();
        assertTrue(ticketRepository.findByUserOrderByCreationDateDesc(user).get(0).getCreationDate()
                .after(ticketRepository.findByUserOrderByCreationDateDesc(user).get(1).getCreationDate()));
    }

}
