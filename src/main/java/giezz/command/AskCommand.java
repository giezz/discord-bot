package giezz.command;

import giezz.Bot;
import giezz.core.CoreCommand;
import giezz.opanai.OpenAi;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import retrofit2.HttpException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

public class AskCommand extends CoreCommand {
    @Override
    public List<OptionData> createOptions() {
        return List.of(
                new OptionData(OptionType.STRING, "prompt", "prompt", true),
                new OptionData(OptionType.STRING, "model", "The name of the model to use. Defaults \"text-davinci-003\"")
                        .addChoice("text-davinci-003", "text-davinci-003")
                        .addChoice("text-davinci-002", "text-davinci-002")
        );
    }

    @Override
    public String getName() {
        return "ask";
    }

    @Override
    public String getDescription() {
        return "Ask OpenAI something";
    }

    @Override
    public void runSlash(SlashCommandInteractionEvent event) {
        new Thread(() -> {
            event.deferReply().queue();
            OptionMapping promptOption = event.getOption("prompt");
            OptionMapping modelOption = event.getOption("model");
            String prompt = promptOption.getAsString();
            String openAiReply;
            try {
                if (modelOption == null) {
                    openAiReply = OpenAi.getCompletion(prompt);
                } else {
                    String model = modelOption.getAsString();
                    openAiReply = OpenAi.getCompletion(prompt, model);
                }

                if (openAiReply.length() < 2000) event.getHook().sendMessage(openAiReply).queue();
                else {
                    FileUpload fileUpload = FileUpload.fromData(writeFile(openAiReply));
                    event.getHook().sendFiles(fileUpload).queue();
                    fileUpload.close();
                }

            } catch (HttpException | IOException | URISyntaxException e) {
                if (e instanceof HttpException httpException) {
                    switch (httpException.code()) {
                        case 500 -> event.getHook().sendMessage("Server returned code 500").queue();
                        case 400 -> event.getHook().sendMessage("Bad request").queue();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private File writeFile(String text) throws IOException, URISyntaxException {
        File file = new File(Objects.requireNonNull(Bot.class.getResource("/reply.txt")).toURI());
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(text);
        writer.close();
        return file;
    }
}
