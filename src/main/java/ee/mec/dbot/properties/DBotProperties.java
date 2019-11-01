package ee.mec.dbot.properties;

import ee.mec.dbot.Launcher;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import org.tinylog.Logger;

public class DBotProperties {

  private static final String PROPERTIES_FILE = "dbot.properties";

  public static final String BOT_OWNER_ID;
  public static final String DISCORD_APP_TOKEN;
  public static final String MS_INSTANT_API_TOKEN;

  static {
    var props = loadProperties(PROPERTIES_FILE);
    BOT_OWNER_ID = props.getProperty("bot-owner-id");
    DISCORD_APP_TOKEN = props.getProperty("discord-app-token");
    MS_INSTANT_API_TOKEN = props.getProperty("ms-instant-api-token");
  }

  private DBotProperties() {}

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
