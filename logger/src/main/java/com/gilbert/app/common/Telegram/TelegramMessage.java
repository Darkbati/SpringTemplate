package com.gilbert.app.common.Telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TelegramMessage {
    @JsonProperty("chat_id")
    private Long chatId;

    @JsonProperty("text")
    private String message;
}
