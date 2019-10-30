package ee.mec.dbot.message;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.internal.entities.DataMessage;
import net.dv8tion.jda.internal.entities.UserImpl;

public class UnknownMessage extends DataMessage {

  private UserImpl unknownUser;

  public UnknownMessage() {
    super(false, null, null, null);
    unknownUser = new UserImpl(666, null);
    unknownUser.setName("N/A");
  }

  @Nonnull
  @Override
  public String getContentStripped() {
    return "N/A";
  }

  @Nonnull
  @Override
  public User getAuthor() {
    return unknownUser;
  }
}
