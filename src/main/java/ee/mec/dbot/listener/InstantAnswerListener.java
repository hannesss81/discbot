package ee.mec.dbot.listener;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.tinylog.Logger;

public class InstantAnswerListener extends ListenerAdapter {

  @Override
  public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    Logger.info("Message received event. {}", event);
    Message receivedMessage = event.getMessage();
    if (!isQuestionCommand(receivedMessage)) return;
    // TODO
  }

  private boolean isQuestionCommand(Message receivedMessage) { // TODO
    String messageContent = receivedMessage.getContentStripped();
    return false;
  }
}
