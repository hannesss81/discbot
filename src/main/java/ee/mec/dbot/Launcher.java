package ee.mec.dbot;

import ee.mec.dbot.listener.MessageCachingListener;
import ee.mec.dbot.listener.MessageEventListener;
import net.dv8tion.jda.api.JDABuilder;
import org.tinylog.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import javax.security.auth.login.LoginException;

public class Launcher {

    private static final String PROPERTIES_FILE = "dbot.properties";

    public static void main(String[] args) {
        var properties = loadProperties(PROPERTIES_FILE);

        try {
            new JDABuilder(properties.get("token").toString())
                    .addEventListeners(new MessageCachingListener())
                    .addEventListeners(new MessageEventListener())
                    .build();
        } catch (LoginException e) {
            Logger.error(e, "Authentication failure.");
            System.exit(1);
        }
    }

    private static Properties loadProperties(String fileName) {
        Logger.info("Loading properties from '{}'.", fileName);

        Properties props = new Properties();
        InputStream inputStream = Objects.requireNonNull(Launcher.class
                .getClassLoader()
                .getResourceAsStream(fileName), "Loading properties file failed.");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            Logger.error("Loading properties from file.");
            throw new RuntimeException(e);
        }

        return props;
    }


}
