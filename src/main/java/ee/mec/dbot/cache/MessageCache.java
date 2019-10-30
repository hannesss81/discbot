package ee.mec.dbot.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import ee.mec.dbot.message.UnknownMessage;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.entities.Message;

/** Singleton instance for caching Message objects. Uses Caffeine caches, default expiry 3 days. */
public class MessageCache {

  private static MessageCache cacheInstance = new MessageCache();

  private Cache<Long, Message> messageCache;

  public static MessageCache getMessageCache() {
    return cacheInstance;
  }

  private MessageCache() {
    messageCache = Caffeine.newBuilder().expireAfterWrite(3, TimeUnit.DAYS).build();
  }

  public void putMessage(Long messageId, Message message) {
    messageCache.put(messageId, message);
  }

  public Message getMessage(Long messageId) {
    if (messageId == null)
      messageId = 666L;
    return messageCache.get(messageId, key -> new UnknownMessage());
  }
}
