package yeri_nihongo.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import yeri_nihongo.admin.dto.request.MessageRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    @Value("${message.key}")
    private String messageKey;

    @Value("${message.user-id}")
    private String userId;

    @Value("${message.phone-number}")
    private String sender;

    private static final String MESSAGE_BASE_URI = "https://apis.aligo.in/send/";

    public void sendMessage(MessageRequest request) {
        WebClient webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE + "; charset=EUC-KR")
                .build();


            webClient.post()
                    .uri(MESSAGE_BASE_URI)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(buildFormData(request))
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnNext(log::info)
                    .block();


    }

    private MultiValueMap<String, String> buildFormData(MessageRequest request) {
        LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        try {
            formData.add("key", URLEncoder.encode(messageKey, "EUC-KR"));
            formData.add("user_id", URLEncoder.encode(userId, "EUC-KR"));
            formData.add("sender", URLEncoder.encode(sender, "EUC-KR"));
            formData.add("receiver", URLEncoder.encode(request.getReceiver(), "EUC-KR"));
            formData.add("msg", URLEncoder.encode(request.getMessage(), "EUC-KR"));
            formData.add("testmode_yn", URLEncoder.encode(request.getTestMode(), "EUC-KR"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("EUC-KR 인코딩 실패", e);
        }

        return formData;
    }
}
