package giezz.command;

import giezz.api.Routes;
import giezz.core.CoreCommand;
import giezz.pojos.profile.Profile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class InfoCommand extends CoreCommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Hikimori profile info";
    }

    @Override
    public void runSlash(SlashCommandInteractionEvent event) {
        OptionMapping profileName = event.getOption("profile_name");
        try {
            Profile profile = Routes.getInfo(profileName.getAsString());

            EmbedBuilder info = new EmbedBuilder();
            info.setTitle(profile.getNickname(), profile.getUrl())
                    .addField("Name", profile.getName(), true)
                    .addField("Sex", profile.getSex(), true)
                    .addField("Website", profile.getWebsite(), true)
                    .addField("Age", profile.getFullYears(), true)
                    .setImage(profile.getImage().getX160());

            event.replyEmbeds(info.build()).queue();
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
