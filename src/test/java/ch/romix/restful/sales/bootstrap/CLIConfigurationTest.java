package ch.romix.restful.sales.bootstrap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CLIConfigurationTest {

  @Test
  public void testPortDefault() {
    CLIConfiguration cliConf = new CLIConfiguration(new String[] {});
    assertEquals(8080, cliConf.getPort());
  }

  @Test
  public void testPortOverride() throws Exception {
    CLIConfiguration cliConf = new CLIConfiguration(new String[] {"-p", "8081"});
    assertEquals(8081, cliConf.getPort());
  }

  @Test
  public void testDontUseZookeeper() {
    CLIConfiguration cliConf = new CLIConfiguration(new String[] {});
    assertFalse(cliConf.useZookeeper());
  }

  @Test
  public void testUseZookeeperWithDefaults() throws Exception {
    CLIConfiguration cliConf = new CLIConfiguration(new String[] {"-u"});
    assertTrue(cliConf.useZookeeper());
    assertEquals("localhost:2181", cliConf.getZookeeperLocation());
  }

  @Test
  public void testUseZookeeperWithCustomLocation() throws Exception {
    CLIConfiguration cliConf = new CLIConfiguration(new String[] {"-u", "-z", "zooserver:1234"});
    assertTrue(cliConf.useZookeeper());
    assertEquals("zooserver:1234", cliConf.getZookeeperLocation());
  }

  @Test
  public void testDatabaseConnectionWithDefaults() throws Exception {
    CLIConfiguration cliConf = new CLIConfiguration(new String[] {});
    assertEquals("jdbc:h2:./db/db", cliConf.getJDBCUrl());
    assertEquals("org.h2.Driver", cliConf.getJDBCDriver());
  }

}
