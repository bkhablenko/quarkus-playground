package com.github.bkhablenko;

import java.util.List;

import com.github.bkhablenko.discord.DiscordCustomEmoji;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusTest
public class QuteTemplateTest {

    private static final Logger logger = LoggerFactory.getLogger(QuteTemplateTest.class);

    @Location("webhook_message.json")
    Template webhookMessage;

    @Test
    public void shouldRenderTemplate() {
        final var teamName = "Team Astral Bunnies";
        final var description = """
                LF people to dick around with. We have absolutely no idea what we're doing.

                Discord: Johnny#4269
                """;

        final var roster = List.of(
                new TeamMember(new DiscordCustomEmoji("val_bronze_1", 1123118451729375403L), "ShadowStrike92"),
                new TeamMember(new DiscordCustomEmoji("val_bronze_2", 1123118453977534505L), "BlazeFury77"),
                new TeamMember(new DiscordCustomEmoji("val_bronze_3", 1123118457861447690L), "NovaSpectre123")
        );

        final var json =
                new WebhookMessageTemplateData(teamName, roster, description)
                        .applyTo(webhookMessage)
                        .render();

        logger.debug("Rendered JSON: {}", json);
    }

    public record WebhookMessageTemplateData(String teamName, List<TeamMember> roster, String description) {

        public TemplateInstance applyTo(Template webhookMessage) {
            return webhookMessage
                    .data("description", description)
                    .data("roster", roster)
                    .data("teamName", teamName);
        }
    }

    public record TeamMember(DiscordCustomEmoji rankEmoji, String playerName) {
    }
}
