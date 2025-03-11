package yeri_nihongo.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import yeri_nihongo.admin.dto.request.MessageRequest;
import yeri_nihongo.admin.dto.response.MessageResponse;
import yeri_nihongo.exception.message.MessageSendException;
import yeri_nihongo.member.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

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

    private final MemberService memberService;

    public MessageResponse sendMessage(MessageRequest request) {
        WebClient webClient = WebClient.builder().build();

        String rawResponse = webClient.post()
                .uri(MESSAGE_BASE_URI)
                .body(BodyInserters.fromFormData(buildFormData(request)))
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(log::info)
                .block();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(rawResponse);

            String resultCode = jsonNode.get("result_code").asText();
            String message = jsonNode.get("message").asText();

            if (!resultCode.equals("1")) {
                log.info(rawResponse);
                throw new MessageSendException(message);
            }

            return new MessageResponse(resultCode, message);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse JSON: {}", rawResponse);
            return new MessageResponse("-1", "JSON 파싱 실패");
        }
    }

    private MultiValueMap<String, String> buildFormData(MessageRequest request) {
        LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("key", messageKey);
            formData.add("user_id", userId);
            formData.add("sender", sender);
            formData.add("receiver", getReceiver(request.getReceiverIds()));
            formData.add("msg", request.getMessage());
            formData.add("testmode_yn", request.getTestMode());

        return formData;
    }

    private String getReceiver(List<Long> receiverIds) {
        return receiverIds.stream()
                .map(memberService::getPhoneByMemberId)
                .collect(Collectors.joining(","));
    }
}
