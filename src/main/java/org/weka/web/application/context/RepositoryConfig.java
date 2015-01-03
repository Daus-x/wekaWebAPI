package org.weka.web.application.context;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.weka.web.application.service.FileService;
import org.weka.web.application.service.impl.FileServiceImpl;

/**
 * Created by suad on 1/3/2015.
 */
@Configuration
@EnableMongoRepositories
public class RepositoryConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "mydatabase";
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        MongoClient client = new MongoClient("localhost");
        client.setWriteConcern(WriteConcern.SAFE);
        return client;
    }

    @Bean
    public FileService fileService(){
        return new FileServiceImpl();
    }


    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    @Override
    protected String getMappingBasePackage() {
        return "org.weka.web.application";
    }

}