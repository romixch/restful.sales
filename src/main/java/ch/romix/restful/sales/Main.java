package ch.romix.restful.sales;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {
  private static final String WEBAPP_DIR_LOCATION = "src/main/webapp/";
  private static final String DEFAULT_PORT = "8080";
  private static final Option PORT_OPTION = new Option("p", true, "The port where the rest interface is bound to.\nDefault: "
      + DEFAULT_PORT + ".");
  private static final String DEFAULT_ZOOKEEPER_DESTINATION = "localhost:2181";
  private static final Option USE_ZOOKEEPER_OPTION = new Option("u",
      "Use a ZooKeeper to register the service.\nDefault: Do not use ZooKeeper.");
  private static final Option ZOOKEEPER_OPTION = new Option("z", true, "ZooKeeper server and port separated by colon (:).\nDefault: "
      + DEFAULT_ZOOKEEPER_DESTINATION + ".");
  private static final Option HELP_OPTION = new Option("h", false, "Prints this help.");
  private static final Option JDBC_URL_OPTION = new Option("j", true, "JDBC url for database.\nDefault: jdbc:h2:./db/db");
  private static final Option JDBC_DRIVER_OPTION = new Option("d", true, "JDBC driver class.\nDefault: org.h2.Driver");

  public static void main(String[] args) throws Exception {
    CommandLine cliArgs = getCLIArgs(args);

    readDBConnection(cliArgs);

    int port = Integer.parseInt(cliArgs.getOptionValue(PORT_OPTION.getOpt(), DEFAULT_PORT));

    Server server = new Server(port);
    WebAppContext root = new WebAppContext();

    root.setContextPath("/");
    root.setDescriptor(WEBAPP_DIR_LOCATION + "/WEB-INF/web.xml");
    root.setResourceBase(WEBAPP_DIR_LOCATION);

    root.setParentLoaderPriority(true);

    server.setHandler(root);

    server.start();

    if (cliArgs.hasOption(USE_ZOOKEEPER_OPTION.getOpt())) {
      String zookeeper = cliArgs.getOptionValue(ZOOKEEPER_OPTION.getOpt(), DEFAULT_ZOOKEEPER_DESTINATION);
      registerAtZooKeeper(port, zookeeper);
    }
    server.join();
  }

  private static void readDBConnection(CommandLine cliArgs) {
    System.setProperty("jdbc.url", cliArgs.getOptionValue(JDBC_URL_OPTION.getOpt(), "jdbc:h2:./db/db"));
    System.setProperty("jdbc.driver", cliArgs.getOptionValue(JDBC_DRIVER_OPTION.getOpt(), "org.h2.Driver"));
  }

  private static CommandLine getCLIArgs(String[] args) {
    Options options = new Options();
    options.addOption(HELP_OPTION);
    options.addOption(PORT_OPTION);
    options.addOption(ZOOKEEPER_OPTION);
    options.addOption(USE_ZOOKEEPER_OPTION);
    options.addOption(JDBC_URL_OPTION);
    options.addOption(JDBC_DRIVER_OPTION);

    CommandLineParser parser = new BasicParser();
    CommandLine cmdLine;
    try {
      cmdLine = parser.parse(options, args);
      if (cmdLine.hasOption(HELP_OPTION.getOpt())) {
        throw new ParseException("Help needed");
      }
      return cmdLine;
    } catch (ParseException e) {
      new HelpFormatter().printHelp("restful.sales", options);
      System.exit(1);
      throw new RuntimeException(e);
    }
  }

  private static void registerAtZooKeeper(int localPort, String zookeeperHostAndPort) throws Exception {
    CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zookeeperHostAndPort, new RetryNTimes(5, 1000));
    curatorFramework.start();
    ServiceInstance<Object> serviceInstance =
        ServiceInstance.builder().uriSpec(new UriSpec("{scheme}://{address}:{port}/api")).address("localhost").port(localPort)
            .name("sales.api").build();

    ServiceDiscoveryBuilder.builder(Object.class).basePath("load-balancing-example").client(curatorFramework).thisInstance(serviceInstance)
        .build().start();
  }
}
