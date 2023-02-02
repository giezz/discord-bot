package giezz.core;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public interface BotCommand {

    String getName();

    String getDescription();

    default List<OptionData> createOptions() {
        return List.of();
    }
}
