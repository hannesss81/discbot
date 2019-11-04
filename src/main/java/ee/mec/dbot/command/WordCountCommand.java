package ee.mec.dbot.command;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import ee.mec.dbot.cache.WordCountCache;
import net.dv8tion.jda.api.entities.User;

import java.util.Map.Entry;

public class WordCountCommand extends Command {

  private static WordCountCache wcc = WordCountCache.getWordCountCache();

  public WordCountCommand() {
    this.name = "stats";
    this.help = "Prints out the most common words used by myself.";
  }

  @Override
  protected void execute(CommandEvent event) {
    User author = event.getAuthor();
    int limit = 5;

    StringBuilder response =
        new StringBuilder()
            .append(String.format("Top %s word(s) for \"%s\": \n", limit, author.getName()));
    for (Entry<String, Integer> topWordCount :
        wcc.getUserWords(author.getIdLong()).getTopWordCounts(limit)) {
      response.append(
          String.format("\"%s\" - %s\n", topWordCount.getKey(), topWordCount.getValue()));
    }
    event.reply(response.toString());
  }
}
