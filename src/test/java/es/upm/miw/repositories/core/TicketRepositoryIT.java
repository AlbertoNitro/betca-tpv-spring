package es.upm.miw.repositories.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class TicketRepositoryIT {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByReference() {
        assertEquals("666666004", ticketRepository.findByReference("t2j_u9M9CisFmYGRFs1Uulgn7hI").getUser().getMobile());
        assertEquals(61.7, ticketRepository.findByReference("t2j_u9M9CisFmYGRFs1Uulgn7hI").getTotal().doubleValue(), 10E-5);
    }

    @Test
    public void testFindByCreationDateGreaterThan() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-mm-dd").parse("2018-01-06");
        List<Ticket> ticketList = ticketRepository.findByCreationDateGreaterThanOrderByCreationDateDesc(date);
        assertTrue(ticketList.contains(ticketRepository.findByReference("t2j_u9M9CisFmYGRFs1Uulgn7hI")));
        assertTrue(ticketList.contains(ticketRepository.findByReference("6P0ISee_twnGEzf8qd1Bd5sGQqE")));
    }

    @Test
    public void testFindFirstByOrderByCreationDateDescIdDesc() {
    }

    @Test
    public void findByCreationDateBetween() throws ParseException {
        Date initialDate = new SimpleDateFormat("yyyy-mm-dd").parse("2017-01-11");
        List<Ticket> ticketListByRangeDates = this.ticketRepository.findByCreationDateBetweenOrderByCreationDateDesc(initialDate,
                new Date());
        List<Ticket> ticketAllList = this.ticketRepository.findAll();
        assertTrue(ticketListByRangeDates.size() >= ticketAllList.size());
    }

    @Test
    public void testFindByIdArticleDateBetween() throws ParseException {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-01-01 00:00:00");
        List<Ticket> ticketListByIdAndRangeDates = ticketRepository.findByIdArticleDatesBetween("article1", startDate, new Date());
        assertNotNull(ticketListByIdAndRangeDates);
        assertTrue(ticketListByIdAndRangeDates.size() >= 0);
    }

    @Test
    public void testFindByUserOrderByCreationDateDesc() {
        User user = this.userRepository.findByMobile("666666004");
        List<Ticket> ticketList = this.ticketRepository.findByUserOrderByCreationDateDesc(user);
        assertTrue(ticketList.size() >= 2);
    }

    @Test
    public void testFindByShoopingListShoppingArticleNotCommited() {
        List<Ticket> ticketList = this.ticketRepository.findByShoopingListArticleIdNotCommited(new String[] {"8400000000024"});
        assertTrue(ticketList.size() >= 1);
    }
    
    @Test
    public void testFindByShoopingListShoppingArticle() {
        List<Ticket> ticketList = this.ticketRepository.findByShoopingListArticleIdNotCommited(new String[] {"8400000000017"});
        assertTrue(ticketList.size() == 0);
    }

}
