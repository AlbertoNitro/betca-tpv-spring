package es.upm.miw.services;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import es.upm.miw.repositories.users.UserRepository;

@Service
public class DatabaseSeederService {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private UserRepository userRepository;

    public void seedDatabase(String ymlFileName) {
        assert ymlFileName != null && !ymlFileName.isEmpty();
        Yaml yamlParser = new Yaml(new Constructor(TpvGraph.class));
        InputStream input;
        try {
            input = appContext.getResource(ymlFileName).getInputStream();
            TpvGraph tpvGraph = (TpvGraph) yamlParser.load(input);
            userRepository.save(tpvGraph.getUserList());
        } catch (IOException e) {
            Logger.getLogger(this.getClass()).error("File " + ymlFileName + " doesn't exist or can't be opened");
        }
    }

    public void deleteAllExceptAdmin() {
        userRepository.deleteAll();
        // TODO crear default admin
    }

}
