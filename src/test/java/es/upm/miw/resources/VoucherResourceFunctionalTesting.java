package es.upm.miw.resources;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
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
import es.upm.miw.services.DatabaseSeederService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class VoucherResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private RestService restService;

    private VoucherDto voucherDto;

    @Autowired
    private DatabaseSeederService databaseSeederService;

    @Before
    public void before() {
        this.voucherDto = new VoucherDto(new BigDecimal(65));
        this.restService.loginAdmin().restBuilder().path(VoucherResource.VOUCHERS).body(this.voucherDto).post().build();
        List<VoucherDto> voucherDtoList = readVoucherAll();
        this.voucherDto = voucherDtoList.get(voucherDtoList.size() - 1);
    }

    @After
    public void delete() {
        databaseSeederService.seedDatabase();
    }

    @Test
    public void testReadVoucherAll() {
        List<VoucherDto> voucherDtoList = readVoucherAll();
        assertEquals(5, voucherDtoList.size());
    }

    private List<VoucherDto> readVoucherAll() {
        List<VoucherDto> voucherDtoList = Arrays.asList(restService.loginAdmin().restBuilder(new RestBuilder<VoucherDto[]>())
                .clazz(VoucherDto[].class).path(VoucherResource.VOUCHERS).get().build());
        return voucherDtoList;
    }

    @Test
    public void testConsumeVoucher() {
        assertEquals(false, this.voucherDto.isUsed());

        restService.loginAdmin().restBuilder().path(VoucherResource.VOUCHERS).path(VoucherResource.REFERENCE)
                .expand(this.voucherDto.getReference()).patch().build();
        List<VoucherDto> voucherDtoList = readVoucherAll();
        VoucherDto voucherDto = voucherDtoList.get(voucherDtoList.size() - 1);
        assertEquals(true, voucherDto.isUsed());
        assertEquals(this.voucherDto.getReference(), voucherDto.getReference());
    }

}
