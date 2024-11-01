package com.crio.starter.controller;

import java.util.List;
import com.crio.starter.data.MemesEntity;
import com.crio.starter.exception.EmptyDataFieldsException;
import com.crio.starter.exception.GlobalExceptionHandler;
import com.crio.starter.exception.MemesAlreadyExistException;
import com.crio.starter.exception.MemesNotFoundException;
import com.crio.starter.exchange.Memes;
import com.crio.starter.service.MemesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memes")
public class MemesController {
    
  @Autowired
  MemesService memesService;

  @Autowired/////////////////////////////////////
  GlobalExceptionHandler globalExceptionHandler;

  @PostMapping
  public ResponseEntity<String> addMeme(@RequestBody Memes memes) {
    try { 
      int createdMemeId = memesService.addMemes(memes.getName(),memes.getUrl(),memes.getCaption());
      return ResponseEntity.ok().body("{\"id\": \"" + createdMemeId + "\"}");
    }
    catch (EmptyDataFieldsException ex){
      return globalExceptionHandler.handleEmptyDataFieldsException(ex);
    } 
    catch (MemesAlreadyExistException ex) {
      return globalExceptionHandler.handleMemeAlreadyExistsException(ex);
    }
    catch(Exception ex){
      return globalExceptionHandler.handleException(ex);
    }

  }

  @GetMapping
  public ResponseEntity<?> showMemes() {
      try {
          List<Memes> memes = memesService.showMemes();
          return ResponseEntity.ok().body(memes);
      }catch (Exception ex) {
          return globalExceptionHandler.handleException(ex);
      }
  }



  @GetMapping("/{id}")
  public ResponseEntity<?> getMemeById(@PathVariable Integer id) {
      try {
          MemesEntity meme = memesService.getMemeById(id);
          return ResponseEntity.ok().body(meme);
      } catch (MemesNotFoundException ex) {
          return globalExceptionHandler.handleMemesNotFoundException(ex);
      } catch (Exception ex) {
          return globalExceptionHandler.handleException(ex);
      }
  }
  
}