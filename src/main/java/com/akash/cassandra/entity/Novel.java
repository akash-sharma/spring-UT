package com.akash.cassandra.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Novel {

  @PrimaryKeyColumn(name = "category", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
  private String category;

  @PrimaryKeyColumn(name = "author", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
  private String author;

  @PrimaryKeyColumn(name = "genre", type = PrimaryKeyType.CLUSTERED, ordinal = 2)
  private String genre;

  @PrimaryKeyColumn(name = "name", type = PrimaryKeyType.CLUSTERED, ordinal = 3)
  private String name;

  @CassandraType(type = CassandraType.Name.COUNTER)
  @Column("salecount")
  private Long saleCount;

  @Transient private long groupCount;

  public Novel() {}

  public Novel(String genre, long groupCount) {
    this.genre = genre;
    this.groupCount = groupCount;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public Long getSaleCount() {
    return saleCount;
  }

  public void setSaleCount(Long saleCount) {
    this.saleCount = saleCount;
  }

  public long getGroupCount() {
    return groupCount;
  }

  @Override
  public String toString() {
    return "Novel{"
        + "category='"
        + category
        + '\''
        + ", author='"
        + author
        + '\''
        + ", genre='"
        + genre
        + '\''
        + ", name='"
        + name
        + '\''
        + ", saleCount="
        + saleCount
        + '}';
  }
}
