package es.upm.miw.services;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import es.upm.miw.documents.users.Role;
import es.upm.miw.documents.users.User;
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

    public void seedDatabase(String ymlFileName) {
        assert ymlFileName != null && !ymlFileName.isEmpty();
        Yaml yamlParser = new Yaml(new Constructor(TpvGraph.class));
        try {
            InputStream input = new ClassPathResource(ymlFileName).getInputStream();
            TpvGraph tpvGraph = (TpvGraph) yamlParser.load(input);
            userRepository.save(tpvGraph.getUserList());
        } catch (IOException e) {
            Logger.getLogger(this.getClass()).error("File " + ymlFileName + " doesn't exist or can't be opened");
        }
    }

    public void deleteAllAndCreateAdmin() {
        this.userRepository.deleteAll();
        this.createAdmin();
    }

    public void createAdmin() {
        User user = new User(this.mobile, this.username, this.password);
        user.setRoles(new Role[] {Role.ADMIN});
        this.userRepository.save(user);
    }

}
