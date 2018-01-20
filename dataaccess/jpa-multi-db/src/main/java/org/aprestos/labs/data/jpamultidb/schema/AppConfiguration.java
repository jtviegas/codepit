package org.aprestos.labs.data.jpamultidb.schema;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories({ "org.aprestos.labs.data.jpamultidb.schema.special.repository",
    "org.aprestos.labs.data.jpamultidb.schema.common.repository" })
@EntityScan({ "org.aprestos.labs.data.jpamultidb.schema.special.model",
    "org.aprestos.labs.data.jpamultidb.schema.common.model" })
@SpringBootApplication
public class AppConfiguration {

}
