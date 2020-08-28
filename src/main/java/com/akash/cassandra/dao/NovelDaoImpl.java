package com.akash.cassandra.dao;

import com.akash.cassandra.entity.Novel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.cassandra.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NovelDaoImpl implements NovelDao {

  @Autowired private CassandraOperations cassandraTemplate;

  @Autowired private CqlSession cqlSession;

  // query via spring template
  // select * from novel where category=?1 and author=?2
  @Override
  public List<Novel> findAllByCategoryAndAuthor(String category, String author) {
    return cassandraTemplate.select(
        Query.query(Criteria.where("category").is(category))
            .and(Criteria.where("author").is(author)),
        Novel.class);
  }

  // Native query via session
  // Select genre,count(*) as count from Novel where category=?1 and author=?2 group by genre
  @Override
  public List<Novel> findGroupedGenreByCategoryAndAuthor(String category, String author) {
    String query =
        "Select genre,count(*) as count from Novel "
            + " where category = '"
            + category
            + "' and author = '"
            + author
            + "' group by genre";
    ResultSet rs = cqlSession.execute(query);
    List<Novel> novels = new ArrayList<Novel>();
    for (Row row : rs) {
      Novel data = new Novel(row.getString("genre"), row.getLong("count"));
      novels.add(data);
    }
    return novels;
  }

  // update novel set count=count+1 where category=? and author=? and genre=? and name=?
  @Override
  public void upsert(Novel novel) {
    cassandraTemplate.update(
        Query.query(Criteria.where("category").is(novel.getCategory()))
            .and(Criteria.where("author").is(novel.getAuthor()))
            .and(Criteria.where("genre").is(novel.getGenre()))
            .and(Criteria.where("name").is(novel.getName())),
        Update.empty().increment("salecount"),
        Novel.class);
  }

  /*
  INSERT statements are not allowed on counter tables, use UPDATE instead
  public void insert(Novel novel) {
      cassandraTemplate.insert(novel);
  }*/
}
