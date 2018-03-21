package es.upm.miw.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import es.upm.miw.documents.core.Role;
import es.upm.miw.documents.core.User;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.CashMovementRepository;
import es.upm.miw.repositories.core.CashierClosureRepository;
import es.upm.miw.repositories.core.InvoiceRepository;
import es.upm.miw.repositories.core.ProviderRepository;
import es.upm.miw.repositories.core.TicketRepository;
import es.upm.miw.repositories.core.UserRepository;
import es.upm.miw.repositories.core.VoucherRepository;

@Service
public class DatabaseSeederService {

    @Value("${miw.admin.mobile}")
    private String mobile;

    @Value("${miw.admin.username}")
    private String username;

    @Value("${miw.admin.password}")
    private String password;

    @Value("${miw.databaseSeeder.ymlFileName:#{null}}")
    private Optional<String> ymlFileName;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoucherRepository voucherRepository;
    
    @Autowired
    private CashMovementRepository cashMovementRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    public TicketRepository ticketRepository;

    @Autowired
    public InvoiceRepository invoiceRepository;

    @Autowired
    public CashierClosureRepository cashierClosureRepository;

    @PostConstruct
    public void seedDatabase() {
        if (ymlFileName.isPresent()) {
            this.deleteAllAndCreateAdmin();
            try {
                this.seedDatabase(ymlFileName.get());
            } catch (IOException e) {
                Logger.getLogger(this.getClass()).error("File " + ymlFileName + " doesn't exist or can't be opened");
            }
        } else {
            this.createAdminIfNotExist();
        }
    }

    public void seedDatabase(String ymlFileName) throws IOException {
        assert ymlFileName != null && !ymlFileName.isEmpty();
        Yaml yamlParser = new Yaml(new Constructor(DatabaseGraph.class));
        InputStream input = new ClassPathResource(ymlFileName).getInputStream();
        DatabaseGraph tpvGraph = (DatabaseGraph) yamlParser.load(input);

        // Save Repositories -----------------------------------------------------
        if (tpvGraph.getUserList() != null) {
            this.userRepository.save(tpvGraph.getUserList());
        }
        if (tpvGraph.getVoucherList() != null) {
            this.voucherRepository.save(tpvGraph.getVoucherList());
        }
        if (tpvGraph.getCashMovementList() != null) {
            this.cashMovementRepository.save(tpvGraph.getCashMovementList());
        }
        if (tpvGraph.getProviderList() != null) {
            this.providerRepository.save(tpvGraph.getProviderList());
        }
        if (tpvGraph.getArticleList() != null) {
            this.articleRepository.save(tpvGraph.getArticleList());
        }
        if (tpvGraph.getTicketList() != null) {
            this.ticketRepository.save(tpvGraph.getTicketList());
        }
        if (tpvGraph.getInvoiceList() != null) {
            this.invoiceRepository.save(tpvGraph.getInvoiceList());
        }
        // -----------------------------------------------------------------------

        Logger.getLogger(this.getClass()).warn("------------------------- Seed: " + ymlFileName + "-----------");
    }

    public void deleteAllAndCreateAdmin() {
        Logger.getLogger(this.getClass()).warn("------------------------- delete All And Create Admin-----------");
        // Delete Repositories -----------------------------------------------------
        this.userRepository.deleteAll();
        this.ticketRepository.deleteAll();
        this.articleRepository.deleteAll();
        this.voucherRepository.deleteAll();
        this.cashMovementRepository.deleteAll();
        this.providerRepository.deleteAll();
        this.invoiceRepository.deleteAll();
        this.cashierClosureRepository.deleteAll();
        this.createAdminIfNotExist();
        // -----------------------------------------------------------------------
    }

    public void createAdminIfNotExist() {
        if (this.userRepository.findByMobile(this.mobile) == null) {
            User user = new User(this.mobile, this.username, this.password);
            user.setRoles(new Role[] {Role.ADMIN});
            this.userRepository.save(user);
        }
    }

}
