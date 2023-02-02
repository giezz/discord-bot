package giezz;

import giezz.core.CommandHook;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class Bot {
    public ShardManager getShardManager() {
        return shardManager;
    }

    private final ShardManager shardManager;

    public Bot() {
        String token = System.getenv("DISCORD_TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.streaming("OpenAI ChatGPT", "https://vk.com/id0"));
        shardManager = builder.build();

        shardManager.addEventListener(new CommandHook());
    }

    public static void main(String[] args) {
        new Bot();
    }
}
