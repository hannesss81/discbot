package ee.mec.dbot.listener;

import ee.mec.dbot.cache.MessageCache;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.tinylog.Logger;

/** Notifies the channel of any message 'edit' or 'delete' events. */
public class MessageEditDeleteListener extends ListenerAdapter {

  private final Map<Long, Long> userPrevMessageIds = new HashMap<>();
  private MessageCache messageCache = MessageCache.getMessageCache();

  @Override
  public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    userPrevMessageIds.put(event.getAuthor().getIdLong(), event.getMessageIdLong());
  }

  @Override
  public void onMessageUpdate(@Nonnull MessageUpdateEvent event) {
    Logger.info("Message update event. {}", event);

    Long authorId = event.getAuthor().getIdLong();
    Message updatedMessage = event.getMessage();
    Message oldMessage = messageCache.getMessage(userPrevMessageIds.get(authorId));

    event
        .getChannel()
        .sendMessageFormat("`%s`'s old message:\n", event.getAuthor().getName())
        .appendFormat("`%s`", oldMessage.getContentStripped())
        .queue();

    userPrevMessageIds.put(authorId, updatedMessage.getIdLong());
  }

  @Override
  public void onMessageDelete(@Nonnull MessageDeleteEvent event) {
    Logger.info("Message delete event. {}", event);

    Message deletedMessage = messageCache.getMessage(event.getMessageIdLong());

    event
        .getChannel()
        .sendMessageFormat("`%s` deleted a message!\n", deletedMessage.getAuthor().getName())
        .appendFormat("`%s`", deletedMessage.getContentStripped())
        .queue();
  }
}
