package ee.mec.dbot.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import net.dv8tion.jda.api.entities.Message;

import java.util.concurrent.TimeUnit;


public class MessageCache {

    private static MessageCache cacheInstance = new MessageCache();

    private Cache<Long, Message> messageCache;

    public static MessageCache getMessageCache() {
        return cacheInstance;
    }

    private MessageCache() {
        messageCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.DAYS)
                .build();
    }

    public void putMessage(Long messageId, Message message) {
        messageCache.put(messageId, message);
    }

    public Message getMessage(Long messageId) {
        return messageCache.getIfPresent(messageId);
    }

}
