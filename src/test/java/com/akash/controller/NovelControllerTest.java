package com.akash.controller;

import com.akash.SpringUtApplication;
import com.akash.cassandra.dao.NovelDao;
import com.akash.cassandra.entity.Novel;
import org.cassandraunit.spring.CassandraUnit;
import org.cassandraunit.spring.CassandraUnitTestExecutionListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringUtApplication.class)
@ActiveProfiles("test")
@TestExecutionListeners(
    listeners = CassandraUnitTestExecutionListener.class,
    mergeMode = MERGE_WITH_DEFAULTS)
@CassandraUnit
public class NovelControllerTest {

  @Autowired private NovelController novelController;

  @Autowired private NovelDao novelDao;

  @Test
  public void findNovelTest() {

    final String author = "A1";
    final String category = "C1";
    novelController.save(author, category);
    List<Novel> allByCategoryAndAuthor = novelDao.findAllByCategoryAndAuthor(category, author);
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

    Novel novel = new Novel();
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
  }
}
