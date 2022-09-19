package com.gilbert.app.common.Telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class TelegramMessageSender {
    @Value("${telegram.profile}")
    private String PROFILE = "";

    @Value("${telegram.api.url}")
    private String TELEGRAM_URL = "";

    @Value("${telegram.chat.id}")
    private Long CHAT_ID;

    @Value("${telegram.chat.exception.token}")
    private String EXCEPTION_TOKEN = "";

    @Value("${telegram.chat.payment.token}")
    private String PAYMENT_TOKEN = "";

    @Value("${telegram.api.readTimeout:5000}")
    private int READ_TIMEOUT;

    @Value("${telegram.api.connectTimeout:3000}")
    private int CONNECT_TIMEOUT;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Async
    public void sendMessage(TelegramMessage message, TOKEN_TYPE tokenType) {
        if (message == null) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("----- ");
        builder.append(PROFILE);
        builder.append(" -----\n");
        builder.append(message.getMessage());

        message.setChatId(CHAT_ID);
        message.setMessage(builder.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            String token = null;
            switch (tokenType) {
                case PAYMENT:
                    token = PAYMENT_TOKEN;
                    break;
                case EXCEPTION:
                default:
                    token = EXCEPTION_TOKEN;
                    break;
            }

        } catch (Exception e) {
            log.error("[Telegram Sender Exception]", e);
        }
    }
}
