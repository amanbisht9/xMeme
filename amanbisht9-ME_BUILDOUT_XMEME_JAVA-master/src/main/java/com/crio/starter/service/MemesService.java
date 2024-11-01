package com.crio.starter.service;

import java.util.List;
import com.crio.starter.data.MemesEntity;
import com.crio.starter.exchange.Memes;

public interface MemesService {

    Integer addMemes(String name, String url, String caption);

    List<Memes> showMemes();

    MemesEntity getMemeById(Integer id);
    
}
