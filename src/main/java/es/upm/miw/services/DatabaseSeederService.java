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

import es.upm.miw.documents.users.Role;
import es.upm.miw.documents.users.User;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.ProviderRepository;
import es.upm.miw.repositories.core.TicketRepository;
import es.upm.miw.repositories.core.VoucherRepository;
import es.upm.miw.repositories.users.UserRepository;

@Service
public class DatabaseSeederService {

    @Value("${miw.admin.mobile}")
    private long mobile;

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
    private ProviderRepository providerRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    public TicketRepository ticketRepository;

    @PostConstruct
    public void seedDatabase() {
        if (ymlFileName.isPresent()) {
            this.deleteAllAndCreateAdmin();
            Logger.getLogger(this.getClass()).warn("------------------------- Seed: tpv-bd-test.yml-----------");
            this.seedDatabase(ymlFileName.get());
        } else {
            this.createAdmin();
        }
    }

    public void seedDatabase(String ymlFileName) {
        assert ymlFileName != null && !ymlFileName.isEmpty();
        Yaml yamlParser = new Yaml(new Constructor(TpvGraph.class));
        try {
            InputStream input = new ClassPathResource(ymlFileName).getInputStream();
            TpvGraph tpvGraph = (TpvGraph) yamlParser.load(input);

            // Save Repositories -----------------------------------------------------
            userRepository.save(tpvGraph.getUserList());
            voucherRepository.save(tpvGraph.getVoucherList());
            providerRepository.save(tpvGraph.getProviderList());
            articleRepository.save(tpvGraph.getArticleList());
            ticketRepository.save(tpvGraph.getTicketList());

            // -----------------------------------------------------------------------

        } catch (IOException e) {
            Logger.getLogger(this.getClass()).error("File " + ymlFileName + " doesn't exist or can't be opened");
        }
    }

    public void deleteAllAndCreateAdmin() {
        Logger.getLogger(this.getClass()).error("------------------------- delete All And Create Admin-----------");
        this.userRepository.deleteAll();
        this.ticketRepository.deleteAll();
        this.articleRepository.deleteAll();
        this.voucherRepository.deleteAll();
        this.providerRepository.deleteAll();
        this.createAdmin();
    }

    public void createAdmin() {
        if (this.userRepository.findByMobile(this.mobile) == null) {
            User user = new User(this.mobile, this.username, this.password);
            user.setRoles(new Role[] {Role.ADMIN});
            this.userRepository.save(user);
        }
    }

}
