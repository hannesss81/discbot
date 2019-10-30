package ee.mec.dbot.listener;

import ee.mec.dbot.cache.MessageCache;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;


public class MessageCachingListener extends ListenerAdapter {

    private MessageCache messageCache = MessageCache.getMessageCache();

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        Message receivedMessage = event.getMessage();
        messageCache.putMessage(receivedMessage.getIdLong(), receivedMessage);
    }

    @Override
    public void onMessageUpdate(@Nonnull MessageUpdateEvent event) {
        Message updatedMessage = event.getMessage();
        messageCache.putMessage(updatedMessage.getIdLong(), updatedMessage);
    }

}
