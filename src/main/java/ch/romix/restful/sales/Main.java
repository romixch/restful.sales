package ch.romix.restful.sales;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import ch.romix.restful.sales.bootstrap.CLIConfiguration;

public class Main {
  private static final String WEBAPP_DIR_LOCATION = "src/main/webapp/";

  public static void main(String[] args) throws Exception {
    CLIConfiguration cliConf = new CLIConfiguration(args);

    setJDBCConnectionFromCLI(cliConf);


    Server server = new Server(cliConf.getPort());
    WebAppContext root = new WebAppContext();

    root.setContextPath("/");
    root.setDescriptor(WEBAPP_DIR_LOCATION + "/WEB-INF/web.xml");
    root.setResourceBase(WEBAPP_DIR_LOCATION);

    root.setParentLoaderPriority(true);

    server.setHandler(root);

    server.start();

    if (cliConf.useZookeeper()) {
      String zookeeper = cliConf.getZookeeperLocation();
      registerAtZooKeeper(cliConf.getPort(), zookeeper);
    }
    server.join();
  }

  private static void setJDBCConnectionFromCLI(CLIConfiguration cliConfig) {
    System.setProperty("jdbc.url", cliConfig.getJDBCUrl());
    System.setProperty("jdbc.driver", cliConfig.getJDBCDriver());
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
