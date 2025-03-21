package yeri_nihongo.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yeri_nihongo.enrollment.domain.Category;
import yeri_nihongo.payment.dto.response.OrderIdResponse;
import yeri_nihongo.payment.service.TossService;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final TossService tossService;

    @GetMapping("/toss")
    public ResponseEntity<OrderIdResponse> getOrderId(
            @RequestParam("timeTableId") Long timeTableId,
            @RequestParam("category") Category category
            ) {
        OrderIdResponse response = tossService.generateOrderId(timeTableId, category);

        return ResponseEntity.ok(response);
    }
}
