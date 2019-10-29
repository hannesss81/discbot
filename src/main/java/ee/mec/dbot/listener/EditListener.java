package ee.mec.dbot.listener;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.tinylog.Logger;

import javax.annotation.Nonnull;

public class EditListener implements EventListener {

    @Override
    public void onEvent(@Nonnull GenericEvent genericEvent) {
        Logger.info("Caught event - {}.", genericEvent);
    }
}
