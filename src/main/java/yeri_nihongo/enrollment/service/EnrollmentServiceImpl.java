package yeri_nihongo.enrollment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeri_nihongo.common.service.CommonService;
import yeri_nihongo.common.service.RedisService;
import yeri_nihongo.config.auth.PrincipalDetailsService;
import yeri_nihongo.course.domain.CourseInfo;
import yeri_nihongo.enrollment.converter.EnrollmentConverter;
import yeri_nihongo.enrollment.domain.Category;
import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.enrollment.dto.request.CreateEnrollmentRequest;
import yeri_nihongo.enrollment.dto.request.CustomEnrollmentRequest;
import yeri_nihongo.enrollment.dto.response.EnrollmentListResponse;
import yeri_nihongo.enrollment.repository.EnrollmentRepository;
import yeri_nihongo.exception.course.CourseInfoNotFoundException;
import yeri_nihongo.exception.enrollment.AmountMismatchException;
import yeri_nihongo.exception.enrollment.UnavailableCategoryException;
import yeri_nihongo.member.domain.Member;
import yeri_nihongo.payment.dto.response.TossPaymentConfirmResponse;
import yeri_nihongo.payment.service.TossService;
import yeri_nihongo.time.domain.TimeTable;
import yeri_nihongo.time.repository.TimeTableRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final CommonService commonService;
    private final RedisService redisService;
    private final TossService tossService;

    private final EnrollmentRepository enrollmentRepository;
    private final TimeTableRepository timeTableRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentListResponse> getEnrollments() {
        Long memberId = PrincipalDetailsService.getCurrentMemberId();
        List<Enrollment> enrollments = enrollmentRepository.findEnrollmentByMemberId(memberId);
        List<EnrollmentListResponse> responses = enrollments.stream()
                .map(enrollment -> {
                    CourseInfo courseInfo = getCourseInfoByEnrollmentId(enrollment.getId());
                    boolean isReviewed = enrollmentRepository.existsReviewByEnrollmentId(enrollment.getId());
                    return EnrollmentConverter.toEnrollmentListResponse(enrollment, courseInfo, isReviewed);
                }).toList();

        return responses;
    }

    @Override
    @Transactional
    public void createEnrollmentByAdmin(CustomEnrollmentRequest request) {
        Member member = commonService.getMemberByMemberId(request.getMemberId());
        TimeTable timeTable = commonService.getTimeTableByTimeTableId(request.getTimeTableId());

        Enrollment enrollment = EnrollmentConverter
                .toEntity(member, timeTable, request);

        enrollmentRepository.save(enrollment);
    }

    @Override
    public CourseInfo getCourseInfoByEnrollmentId(Long enrollmentId) {
        return enrollmentRepository.findCourseInfoByEnrollmentId(enrollmentId)
                .orElseThrow(() -> new CourseInfoNotFoundException("enrollment id: " + enrollmentId));
    }

    @Override
    @Transactional
    public void createEnrollment(CreateEnrollmentRequest request) {
        validateOrderIdAndAmount(request.getOrderId(), request.getAmount());

        TimeTable timeTable = commonService.getTimeTableByTimeTableId(request.getTimeTableId());
        validateCategory(timeTable, request.getCategory());

        Long memberId = PrincipalDetailsService.getCurrentMemberId();
        Member member = commonService.getMemberByMemberId(memberId);

        TossPaymentConfirmResponse confirmResponse = tossService.confirmPayment(request.getPaymentKey(), request.getOrderId(), request.getAmount());

        Enrollment enrollment = EnrollmentConverter.toEntity(member, timeTable, request.getCategory(), confirmResponse);
        enrollmentRepository.save(enrollment);

        timeTable.addStudentCount();
    }

    private void validateCategory(TimeTable timeTable, Category category) {
        CourseInfo courseInfo = timeTableRepository.findCourseInfoByTimeTableId(timeTable.getId());

        if (category.equals(Category.LIVE) && !courseInfo.getIsLive()
                || category.equals(Category.ONLINE) && !courseInfo.getIsOnline()
                || category.equals(Category.RECORDED) && !courseInfo.getIsRecorded()) {
            throw new UnavailableCategoryException(courseInfo.getId());
        }
    }

    private void validateOrderIdAndAmount(String orderId, int amount) {
        int savedAmount = redisService.getAmount(orderId);

        if (amount != savedAmount) {
            throw new AmountMismatchException();
        }
    }
}
