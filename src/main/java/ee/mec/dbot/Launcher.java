package ee.mec.dbot;

import ee.mec.dbot.listener.MessageEditDeleteListener;
import ee.mec.dbot.listener.MessageStorageListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.tinylog.Logger;

public class Launcher {

  private static final String PROPERTIES_FILE = "dbot.properties";

  public static void main(String[] args) {
    var properties = loadProperties(PROPERTIES_FILE);
    var hasStarted = false;
    Logger.info("Starting DBot.");
    try {
      JDA jda =
          new JDABuilder(properties.get("token").toString())
              .addEventListeners(new MessageEditDeleteListener())
              .addEventListeners(new MessageStorageListener())
              .build();
      jda.awaitReady();
      hasStarted = true;
    } catch (LoginException e) {
      Logger.error(e, "Authentication failure!");
    } catch (InterruptedException e) {
      Logger.error(e, "Startup interrupted!");
    }

    if (!hasStarted) {
      Logger.error("Failure during startup!");
      System.exit(1);
    }
    Logger.info("DBot is up and running!");
  }

  private static Properties loadProperties(String fileName) {
    Logger.info("Loading properties from '{}'.", fileName);
    Properties props = new Properties();
    InputStream inputStream =
        Objects.requireNonNull(
            Launcher.class.getClassLoader().getResourceAsStream(fileName),
            "Loading properties file failed!");
    try {
      props.load(inputStream);
    } catch (IOException e) {
      Logger.error("Loading properties from {} failed!", fileName);
      throw new RuntimeException(e);
    }
    return props;
  }
}
