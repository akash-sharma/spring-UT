package com.akash.controller;

import com.akash.AbstractApplicationTest;
import com.akash.cassandra.dao.NovelDao;
import com.akash.cassandra.entity.Novel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class NovelControllerTest extends AbstractApplicationTest {

  @Autowired private NovelController novelController;

  @Autowired private NovelDao novelDao;

  @Test
  public void findNovelTest() {

    final String author = "A1";
    final String category = "C1";
    novelController.save(author, category);
    final List<Novel> allByCategoryAndAuthor =
        novelDao.findAllByCategoryAndAuthor(category, author);
    Assertions.assertFalse(CollectionUtils.isEmpty(allByCategoryAndAuthor));
    final Novel novel = allByCategoryAndAuthor.get(0);
    Assertions.assertNotNull(novel);
    Assertions.assertEquals(novel.getAuthor(), author);
    Assertions.assertEquals(novel.getCategory(), category);
  }

  @Test
  public void saveNovelTest() {

    final String author = "A2";
    final String category = "C2";
    final String genre = "fun";

    final Novel novel = new Novel();
    novel.setAuthor(author);
    novel.setCategory(category);
    novel.setGenre(genre);
    novel.setName("mecbeth");
    novelDao.upsert(novel);

    final String allByCategoryAndAuthor =
        novelController.findAllByCategoryAndAuthor(author, category);

    Assertions.assertNotNull(allByCategoryAndAuthor);
    Assertions.assertTrue(allByCategoryAndAuthor.contains(author));
    Assertions.assertTrue(allByCategoryAndAuthor.contains(category));
    Assertions.assertTrue(allByCategoryAndAuthor.contains(genre));
    Assertions.assertTrue(allByCategoryAndAuthor.contains("saleCount=1"));
    System.out.println("allByCategoryAndAuthor : " + allByCategoryAndAuthor);
  }
}
