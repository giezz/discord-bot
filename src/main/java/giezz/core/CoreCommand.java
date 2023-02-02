package giezz.core;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class CoreCommand extends ListenerAdapter implements BotCommand {
    @Override
    public abstract String getName();

    @Override
    public abstract String getDescription();

    @Override
    public final void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase(getName()))
            return;

        runSlash(event);
    }

    public void runSlash(SlashCommandInteractionEvent event) {
    }

}
