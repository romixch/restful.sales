package ch.romix.restful.sales.bootstrap;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CLIConfiguration {

  private static final String DEFAULT_PORT = "8080";
  private static final Option PORT_OPTION = new Option("p", true, "The port where the rest interface is bound to.\nDefault: "
      + DEFAULT_PORT + ".");
  private static final String DEFAULT_ZOOKEEPER_LOCATION = "localhost:2181";
  private static final Option USE_ZOOKEEPER_OPTION = new Option("u",
      "Use a ZooKeeper to register the service.\nDefault: Do not use ZooKeeper.");
  private static final Option ZOOKEEPER_OPTION = new Option("z", true, "ZooKeeper server and port separated by colon (:).\nDefault: "
      + DEFAULT_ZOOKEEPER_LOCATION + ".");
  private static final Option HELP_OPTION = new Option("h", false, "Prints this help.");
  private static final Option JDBC_URL_OPTION = new Option("j", true, "JDBC url for database.\nDefault: jdbc:h2:./db/db");
  private static final Option JDBC_DRIVER_OPTION = new Option("d", true, "JDBC driver class.\nDefault: org.h2.Driver");
  private CommandLine cmdLine;

  public CLIConfiguration(String[] args) {
    Options options = new Options();
    options.addOption(HELP_OPTION);
    options.addOption(PORT_OPTION);
    options.addOption(ZOOKEEPER_OPTION);
    options.addOption(USE_ZOOKEEPER_OPTION);
    options.addOption(JDBC_URL_OPTION);
    options.addOption(JDBC_DRIVER_OPTION);

    CommandLineParser parser = new BasicParser();
    try {
      cmdLine = parser.parse(options, args);
      if (cmdLine.hasOption(HELP_OPTION.getOpt())) {
        throw new ParseException("Help needed");
      }
    } catch (ParseException e) {
      new HelpFormatter().printHelp("restful.sales", options);
      System.exit(1);
      throw new RuntimeException(e);
    }
  }

  public int getPort() {
    return Integer.parseInt(cmdLine.getOptionValue(PORT_OPTION.getOpt(), DEFAULT_PORT));
  }

  public boolean useZookeeper() {
    return cmdLine.hasOption(USE_ZOOKEEPER_OPTION.getOpt());
  }

  public String getZookeeperLocation() {
    return cmdLine.getOptionValue(ZOOKEEPER_OPTION.getOpt(), DEFAULT_ZOOKEEPER_LOCATION);
  }

  public String getJDBCUrl() {
    return cmdLine.getOptionValue(JDBC_URL_OPTION.getOpt(), "jdbc:h2:./db/db");
  }

  public String getJDBCDriver() {
    return cmdLine.getOptionValue(JDBC_DRIVER_OPTION.getOpt(), "org.h2.Driver");
  }

}
