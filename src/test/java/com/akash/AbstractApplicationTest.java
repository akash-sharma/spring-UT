package com.akash;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringUtApplication.class)
@ActiveProfiles("test")
public abstract class AbstractApplicationTest {

  public static final String KEYSPACE_CREATION_QUERY =
      "CREATE KEYSPACE csd WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;";

  public static final String TABLE_CREATION_QUERY =
      "CREATE TABLE csd.novel (category text,author text,genre text,name text,salecount counter, PRIMARY KEY ((category, author), genre, name)) WITH CLUSTERING ORDER BY (genre ASC, name ASC) AND bloom_filter_fp_chance = 0.01 AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'} AND comment = ''  AND compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'} AND compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'} AND crc_check_chance = 1.0 AND dclocal_read_repair_chance = 0.1 AND default_time_to_live = 0 AND gc_grace_seconds = 864000 AND max_index_interval = 2048 AND memtable_flush_period_in_ms = 0 AND min_index_interval = 128 AND read_repair_chance = 0.0 AND speculative_retry = '99PERCENTILE';";

  private static Session session;
  private static Cluster cluster;

  private static Object LOCK = new Object();
  private static String contactPoints = "127.0.0.1";
  private static int port = 9142;
  private static String keySpace = "csd";

  @BeforeAll
  public static void startCassandraEmbedded() {

    if (session == null) {
      synchronized (LOCK) {
        if (session == null) {

          try {
            EmbeddedCassandraServerHelper.startEmbeddedCassandra();
            cluster =
                Cluster.builder()
                    .addContactPoints(contactPoints)
                    .withPort(port)
                    .withoutJMXReporting()
                    .withSocketOptions(new SocketOptions().setConnectTimeoutMillis(5000))
                    .build();
            System.out.println("Server Started at " + contactPoints + ":" + port);
            session = cluster.connect();
            session.execute(KEYSPACE_CREATION_QUERY);
            session.execute(TABLE_CREATION_QUERY);
            System.out.println("KeySpace created and activated.");
            Thread.sleep(1000);
          } catch (Exception e) {
            throw new RuntimeException("unable to start embedded cassandra : ", e);
          }
        }
      }
    }
  }

  @AfterAll
  protected static void clearAllTables() {
    Collection<TableMetadata> tables = cluster.getMetadata().getKeyspace(keySpace).getTables();
    tables.forEach(table -> session.execute(QueryBuilder.truncate(table)));
  }
}
