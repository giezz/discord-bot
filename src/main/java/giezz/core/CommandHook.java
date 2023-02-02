package giezz.core;

import giezz.command.AskCommand;
import giezz.command.ImageCommand;
import giezz.command.InfoCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandHook extends ListenerAdapter {

    private static void registerCommand(CoreCommand command, CommandListUpdateAction updates) {
        final SlashCommandData commandData = Commands.slash(command.getName(), command.getDescription());
        final List<OptionData> optionData = command.createOptions();
        if (!optionData.isEmpty()) {
            commandData.addOptions(optionData);
        }
        updates.addCommands(commandData);
    }

    private static void registerCommands(JDA jda) {
        Set<CoreCommand> commands = new HashSet<>();

        commands.add(new AskCommand());
        commands.add(new ImageCommand());
        commands.add(new InfoCommand());

        jda.getGuilds().forEach(guild -> {
            final CommandListUpdateAction updates = guild.updateCommands();
            commands.forEach(cmd -> registerCommand(cmd, updates));
            updates.queue();
        });

        commands.forEach(jda::addEventListener);
    }

    @Override
    public void onReady(ReadyEvent event) {
        registerCommands(event.getJDA());
    }
}
