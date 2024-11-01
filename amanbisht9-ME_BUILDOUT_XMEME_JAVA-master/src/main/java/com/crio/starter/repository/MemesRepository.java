package com.crio.starter.repository;

import com.crio.starter.data.MemesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemesRepository extends MongoRepository<MemesEntity,Integer>{

    MemesEntity findByNameAndUrlAndCaption(String name, String url, String caption);

}
