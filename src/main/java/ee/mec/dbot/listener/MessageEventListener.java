package ee.mec.dbot.listener;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

public class MessageEventListener extends ListenerAdapter {

    private final Map<Long, Message> latestMessages = new HashMap<>();

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        Long authorId = event.getAuthor().getIdLong();
        Message receivedMessage = event.getMessage();

        latestMessages.put(authorId, receivedMessage);
    }

    @Override
    public void onMessageUpdate(@Nonnull MessageUpdateEvent event) {
        Logger.info("Message update event. {}", event);

        Long authorId = event.getAuthor().getIdLong();
        Message updatedMessage = event.getMessage();
        Message oldMessage = latestMessages.get(authorId);

        event.getChannel()
                .sendMessageFormat("`%s`'s old message:\n", event.getAuthor().getName())
                .appendFormat("`%s`", oldMessage.getContentStripped())
                .queue();

        latestMessages.put(authorId, updatedMessage);
    }

    @Override
    public void onMessageDelete(@Nonnull MessageDeleteEvent event) {
        Logger.info("Message delete event. {}", event);
    }
}
