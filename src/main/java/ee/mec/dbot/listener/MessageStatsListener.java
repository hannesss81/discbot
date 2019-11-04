package ee.mec.dbot.listener;

import ee.mec.dbot.cache.WordCountCache;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

/** Counters the occurrences for individual words by user and stores them into cache. */
public class MessageStatsListener extends ListenerAdapter {

  private WordCountCache wcc = WordCountCache.getWordCountCache();

  @Override
  public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) return;
    long authorUserId = event.getAuthor().getIdLong();
    String[] normalizedWords =
        event.getMessage().getContentStripped().toLowerCase().split("\\P{L}+");
    addWordsToStats(normalizedWords, authorUserId);
  }

  private void addWordsToStats(String[] normalizedWords, long userId) {
    wcc.putUserWordsToCache(normalizedWords, userId);
  }
}
