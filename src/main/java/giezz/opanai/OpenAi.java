package giezz.opanai;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;

import java.time.Duration;
import java.util.List;

public class OpenAi {

    private static final String OPENAI_TOKEN = System.getenv("OPENAI_TOKEN");
    private static final OpenAiService service = new OpenAiService(OPENAI_TOKEN, Duration.ZERO);

    public static String getCompletion(String prompt) {
        CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(prompt)
                .echo(true)
                .maxTokens(2048)
                .temperature(0.7)
                .topP(1.0)
                .frequencyPenalty(0.5)
                .presencePenalty(0.5)
                .build();
        return service.createCompletion(request).getChoices().stream().map(CompletionChoice::getText).toList().get(0);
    }

    public static String getCompletion(String prompt, String model) {
        CompletionRequest request = CompletionRequest.builder()
                .model(model)
                .prompt(prompt)
                .echo(true)
                .maxTokens(2048)
                .temperature(0.7)
                .topP(1.0)
                .frequencyPenalty(0.5)
                .presencePenalty(0.5)
                .build();
        return service.createCompletion(request).getChoices().stream().map(CompletionChoice::getText).toList().get(0);
    }

    public static List<String> generateImage(String prompt, int imagesAmount, String size) {
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(prompt)
                .n(imagesAmount)
                .size(size)
                .build();
        return service.createImage(request).getData().stream().map(Image::getUrl).toList();
    }

    public static List<String> generateImage(String prompt, int imagesAmount) {
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(prompt)
                .n(imagesAmount)
                .build();
        return service.createImage(request).getData().stream().map(Image::getUrl).toList();
    }

    public static List<String> generateImage(String prompt, String size) {
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(prompt)
                .size(size)
                .build();
        return service.createImage(request).getData().stream().map(Image::getUrl).toList();
    }

    public static List<String> generateImage(String prompt) {
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(prompt)
                .build();
        return service.createImage(request).getData().stream().map(Image::getUrl).toList();
    }

}
