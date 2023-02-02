package giezz.command;

import giezz.core.CoreCommand;
import giezz.opanai.OpenAi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import retrofit2.HttpException;

import java.util.ArrayList;
import java.util.List;

public class ImageCommand extends CoreCommand {
    @Override
    public List<OptionData> createOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "prompt", "A text description of the desired image(s).", true),
                new OptionData(OptionType.INTEGER, "n", "The number of images to generate. Must be between 1 and 10. Defaults to 1.").setRequiredRange(1, 10),
                new OptionData(OptionType.STRING, "size", "Must be one of \"256x256\", \"512x512\", or \"1024x1024\". Defaults to \"1024x1024\".")
                        .addChoice("256x256", "256x256")
                        .addChoice("512x512", "512x512")
                        .addChoice("1024x1024", "1024x1024")
        );
    }

    @Override
    public String getName() {
        return "image";
    }

    @Override
    public String getDescription() {
        return "A request for OpenAi to create an image based on a prompt. All fields except prompt are optional";
    }

    @Override
    public void runSlash(SlashCommandInteractionEvent event) {
        new Thread(() -> {
            event.deferReply().queue();
            OptionMapping promptOption = event.getOption("prompt");
            OptionMapping nOption = event.getOption("n");
            OptionMapping sizeOption = event.getOption("size");
            String prompt = promptOption.getAsString();
            List<String> imageUrls;

            try {
                if (nOption == null && sizeOption == null) {
                    imageUrls = OpenAi.generateImage(prompt);
                } else if (nOption == null) {
                    String size = sizeOption.getAsString();
                    imageUrls = OpenAi.generateImage(prompt, size);
                } else if (sizeOption == null) {
                    int n = nOption.getAsInt();
                    imageUrls = OpenAi.generateImage(prompt, n);
                } else {
                    int n = nOption.getAsInt();
                    String size = sizeOption.getAsString();
                    imageUrls = OpenAi.generateImage(prompt, n, size);
                }
                List<MessageEmbed> embeds = new ArrayList<>();

                for (String imageUrl : imageUrls) {
                    embeds.add(new EmbedBuilder().setImage(imageUrl).setTitle(prompt).build());
                }
                event.getHook().sendMessageEmbeds(embeds).queue();
            } catch (HttpException e) {
                switch (e.code()) {
                    case 500 -> event.getHook().sendMessage("Server returned code 500").queue();
                    case 400 -> event.getHook().sendMessage("Bad request").queue();
                }
            }
        }).start();
    }
}
