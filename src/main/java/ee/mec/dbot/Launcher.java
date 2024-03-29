package ee.mec.dbot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import ee.mec.dbot.command.InstantAnswerCommand;
import ee.mec.dbot.command.WordCountCommand;
import ee.mec.dbot.listener.MessageEditDeleteListener;
import ee.mec.dbot.listener.MessageStatsListener;
import ee.mec.dbot.listener.MessageStorageListener;
import ee.mec.dbot.properties.DBotProperties;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.tinylog.Logger;

import javax.security.auth.login.LoginException;

public class Launcher {

  public static void main(String[] args) {
    var hasStarted = false;
    Logger.info("Starting DBot.");
    try {
      JDA jda =
          new JDABuilder(DBotProperties.DISCORD_APP_TOKEN)
              .addEventListeners(new MessageStatsListener())
              .addEventListeners(new MessageEditDeleteListener())
              .addEventListeners(new MessageStorageListener())
              .addEventListeners(commandListener())
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

  private static CommandClient commandListener() {
    return new CommandClientBuilder()
        .setOwnerId(DBotProperties.BOT_OWNER_ID)
        .setPrefix("!")
        .addCommand(new InstantAnswerCommand())
        .addCommand(new WordCountCommand())
        .build();
  }
}
