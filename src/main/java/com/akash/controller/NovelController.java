package com.akash.controller;

import com.akash.cassandra.dao.NovelDao;
import com.akash.cassandra.entity.Novel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NovelController {

  @Autowired private NovelDao novelDao;

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @ResponseBody
  public String save(
      @RequestParam(value = "author") String author,
      @RequestParam(value = "category") String category) {
    Novel novel = new Novel();
    novel.setAuthor(author);
    novel.setCategory(category);
    novel.setGenre("fun");
    novel.setName("mecbeth");
    novelDao.upsert(novel);
    return "Novel saved : " + novel;
  }

  @RequestMapping(value = "/find", method = RequestMethod.GET)
  @ResponseBody
  public String findAllByCategoryAndAuthor(
      @RequestParam(value = "author") String author,
      @RequestParam(value = "category") String category) {
    List<Novel> novels = novelDao.findAllByCategoryAndAuthor(category, author);
    return "novels find : " + novels;
  }

  @RequestMapping(value = "/findByGroup", method = RequestMethod.GET)
  @ResponseBody
  public String findGroupedGenreByCategoryAndAuthor(
      @RequestParam(value = "author") String author,
      @RequestParam(value = "category") String category) {
    List<Novel> novels = novelDao.findGroupedGenreByCategoryAndAuthor(category, author);
    return "novels findByGroup : " + novels;
  }
}
