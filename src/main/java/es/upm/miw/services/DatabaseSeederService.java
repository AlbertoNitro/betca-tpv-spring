package es.upm.miw.services;

import java.io.IOException;
import java.io.InputStream;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoucherRepository voucherRepository;
    
    @PostConstruct
    public void seedDatabase() {
        //TODO solo en desarrollo
        //TODO llevar el nombre de test a propiedades de test
        this.deleteAllAndCreateAdmin();
        Logger.getLogger(this.getClass()).error("------------------------- Seed: tpv-bd-test.yml-----------");
        this.seedDatabase("tpv-bd-test.yml");
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
            // -----------------------------------------------------------------------

        } catch (IOException e) {
            Logger.getLogger(this.getClass()).error("File " + ymlFileName + " doesn't exist or can't be opened");
        }
    }

    public void deleteAllAndCreateAdmin() {
        Logger.getLogger(this.getClass()).error("------------------------- delete All And Create Admin-----------");
        this.userRepository.deleteAll();
        this.createAdmin();
    }

    public void createAdmin() {
        User user = new User(this.mobile, this.username, this.password);
        user.setRoles(new Role[] {Role.ADMIN});
        this.userRepository.save(user);
    }

}
