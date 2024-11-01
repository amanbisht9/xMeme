package com.crio.starter.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.crio.starter.data.MemesEntity;
import com.crio.starter.exception.EmptyDataFieldsException;
import com.crio.starter.exception.MemesAlreadyExistException;
import com.crio.starter.exception.MemesNotFoundException;
import com.crio.starter.exchange.Memes;
import com.crio.starter.repository.MemesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class MemesServiceImpl implements MemesService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MemesRepository memesRepository;

    // @Autowired
    // private ModelMapper modelMapper;

    @Override
    public Integer addMemes(String name, String url, String caption) throws MemesAlreadyExistException,EmptyDataFieldsException {
        // TODO Auto-generated method stub

        if(name.trim().isEmpty() == true || url.trim().isEmpty() == true || caption.trim().isEmpty() == true){
            throw new EmptyDataFieldsException("Data fields name/url/caption cannot be empty");
        }

        // Check Duplicate name / url / caption {

            MemesEntity checkMemeExist = memesRepository.findByNameAndUrlAndCaption(name, url, caption);

            if(checkMemeExist != null){
                throw new MemesAlreadyExistException("Meme with the same name, url, and caption already exists") ;
            }

        // }

        //Last inserted id {
    
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.limit(1);

        MemesEntity lastInsertedMeme = mongoTemplate.findOne(query, MemesEntity.class);
        int id = (lastInsertedMeme != null) ? lastInsertedMeme.getId() + 1 : 1;
        
        // }

        MemesEntity memesEntity = new MemesEntity(id, name, url, caption);
        memesRepository.save(memesEntity);

        return id;
    }

    @Override
    public List<Memes> showMemes() {
        
        ArrayList<Memes> memes = new ArrayList<>();
        //
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.limit(100);
        List<MemesEntity> memesEntity = mongoTemplate.find(query, MemesEntity.class);

        if(memesEntity.isEmpty() == false){
            for(MemesEntity memesE : memesEntity){
                memes.add(new Memes(memesE.getId(), memesE.getName(), memesE.getUrl(), memesE.getCaption()));
            }
        }

        return memes;
    }

    @Override
    public MemesEntity getMemeById(Integer id) throws MemesNotFoundException {

        Optional<MemesEntity> memesEntity = memesRepository.findById(id);
        
        if(memesEntity.isPresent() == false){
            throw new MemesNotFoundException("Meme not found of id: "+id);
        }

        return memesEntity.get();
        
    }
    
}
