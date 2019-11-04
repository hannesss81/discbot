package ee.mec.dbot.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import ee.mec.dbot.cache.model.UserWordCounts;

import java.util.Objects;

/**
 * Singleton instance for caching word counts for each individual user. Uses default Caffeine cache.
 */
public class WordCountCache {

  private static WordCountCache cacheInstance = new WordCountCache();

  private Cache<Long, UserWordCounts> wordCountsByUserId;

  public static WordCountCache getWordCountCache() {
    return cacheInstance;
  }

  private WordCountCache() {
    wordCountsByUserId = Caffeine.newBuilder().build();
  }

  public void putUserWordsToCache(String[] words, long userId) {
    var wordCounters = wordCountsByUserId.get(userId, UserWordCounts::new);
    Objects.requireNonNull(wordCounters);

    for (String word : words) {
      if (!word.isBlank()) {
        wordCounters.incrementCount(word);
      }
    }
  }

  public UserWordCounts getUserWords(long userId) {
    return wordCountsByUserId.getIfPresent(userId);
  }
}
