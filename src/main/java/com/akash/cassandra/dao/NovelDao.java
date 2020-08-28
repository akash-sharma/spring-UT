package com.akash.cassandra.dao;

import com.akash.cassandra.entity.Novel;

import java.util.List;

public interface NovelDao {

  List<Novel> findAllByCategoryAndAuthor(String category, String author);

  List<Novel> findGroupedGenreByCategoryAndAuthor(String category, String author);

  void upsert(Novel novel);
}
