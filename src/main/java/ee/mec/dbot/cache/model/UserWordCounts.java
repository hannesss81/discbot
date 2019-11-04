package ee.mec.dbot.cache.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class UserWordCounts {

  private final Map<String, Integer> wordCounts;

  public UserWordCounts(long userId) {
    wordCounts = new HashMap<>();
  }

  public void incrementCount(String word) {
    wordCounts.putIfAbsent(word, 0);
    wordCounts.merge(word, 1, Integer::sum);
  }

  public List<Entry<String, Integer>> getTopWordCounts(int limit) {
    return wordCounts.entrySet().stream()
        .sorted((v1, v2) -> Integer.compare(v2.getValue(), v1.getValue()))
        .limit(limit)
        .collect(Collectors.toList());
  }
}
