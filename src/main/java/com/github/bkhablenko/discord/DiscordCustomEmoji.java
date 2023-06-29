package com.github.bkhablenko.discord;

/**
 * Custom emoji uploaded to a Discord server.
 */
public record DiscordCustomEmoji(String name, long id) {

    /**
     * @return an embed-compatible string representation.
     */
    @Override
    public String toString() {
        return String.format("<:%s:%d>", name, id);
    }
}
