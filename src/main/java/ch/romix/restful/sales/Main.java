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
  private static final Option PORT_OPTION = new Option("p", true,
      "The port where the rest interface is bound to. Defaults to " + DEFAULT_PORT + ".");
  private static final String DEFAULT_ZOOKEEPER_DESTINATION = "localhost:2181";
  private static final Option USE_ZOOKEEPER_OPTION = new Option("u",
      "Use a ZooKeeper to register the service. Default is not to use ZooKeeper.");
  private static final Option ZOOKEEPER_OPTION = new Option("z", true,
      "ZooKeeper server and port separated by colon (:). Defaults to "
          + DEFAULT_ZOOKEEPER_DESTINATION + ".");

  public static void main(String[] args) throws Exception {
    CommandLine cliArgs = getCLIArgs(args);

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
      String zookeeper =
          cliArgs.getOptionValue(ZOOKEEPER_OPTION.getOpt(), DEFAULT_ZOOKEEPER_DESTINATION);
      registerAtZooKeeper(port, zookeeper);
    }
    server.join();
  }

  private static CommandLine getCLIArgs(String[] args) {
    Options options = new Options();
    options.addOption(PORT_OPTION);
    options.addOption(ZOOKEEPER_OPTION);
    options.addOption(USE_ZOOKEEPER_OPTION);

    CommandLineParser parser = new BasicParser();
    CommandLine cmdLine;
    try {
      cmdLine = parser.parse(options, args);
      return cmdLine;
    } catch (ParseException e) {
      new HelpFormatter().printHelp("Help to sales api", options);
      System.exit(1);
      throw new RuntimeException(e);
    }
  }

  private static void registerAtZooKeeper(int localPort, String zookeeperHostAndPort)
      throws Exception {
    CuratorFramework curatorFramework =
        CuratorFrameworkFactory.newClient(zookeeperHostAndPort, new RetryNTimes(5, 1000));
    curatorFramework.start();
    ServiceInstance<Object> serviceInstance =
        ServiceInstance.builder().uriSpec(new UriSpec("{scheme}://{address}:{port}/api"))
            .address("localhost").port(localPort).name("sales.api").build();

    ServiceDiscoveryBuilder.builder(Object.class).basePath("load-balancing-example")
        .client(curatorFramework).thisInstance(serviceInstance).build().start();
  }
}
