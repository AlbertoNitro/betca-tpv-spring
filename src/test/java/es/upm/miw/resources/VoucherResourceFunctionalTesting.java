package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import es.upm.miw.dtos.VoucherDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class VoucherResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    @Test
    public void testReadVoucherAll() {
        List<VoucherDto> voucherDtoList = Arrays.asList(restService.loginAdmin().restBuilder(new RestBuilder<VoucherDto[]>())
                .clazz(VoucherDto[].class).path(VoucherResource.VOUCHERS).get().build());
        assertEquals(3, voucherDtoList.size());
    }

}
