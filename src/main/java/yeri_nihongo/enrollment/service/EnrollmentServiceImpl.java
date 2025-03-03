package yeri_nihongo.enrollment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeri_nihongo.common.service.CommonService;
import yeri_nihongo.config.auth.PrincipalDetailsService;
import yeri_nihongo.course.dto.response.CourseInfoProjection;
import yeri_nihongo.enrollment.converter.EnrollmentConverter;
import yeri_nihongo.enrollment.domain.Enrollment;
import yeri_nihongo.enrollment.dto.request.CustomEnrollmentRequest;
import yeri_nihongo.enrollment.dto.response.EnrollmentListResponse;
import yeri_nihongo.enrollment.repository.EnrollmentRepository;
import yeri_nihongo.member.domain.Member;
import yeri_nihongo.time.domain.TimeTable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final CommonService commonService;

    private final EnrollmentRepository enrollmentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentListResponse> getEnrollmentsForUser() {
        Long memberId = PrincipalDetailsService.getCurrentMemberId();
        List<Enrollment> enrollments = enrollmentRepository.findEnrollmentByMemberId(memberId);
        List<EnrollmentListResponse> responses = enrollments.stream()
                .map(enrollment -> {
                    CourseInfoProjection courseInfo = enrollmentRepository.findCourseInfoByEnrollmentId(enrollment.getId());
                    return EnrollmentConverter.toEnrollmentListResponse(enrollment, courseInfo.getTitle(), courseInfo.getMainImageUrl());
                }).toList();

        return responses;
    }

    @Override
    @Transactional
    public void createEnrollmentByAdmin(CustomEnrollmentRequest request) {
        Member member = commonService.getMemberByMemberId(request.getMemberId());
        TimeTable timeTable = commonService.getTimeTableByTimeTableId(request.getTimeTableId());

        Enrollment enrollment = EnrollmentConverter
                .toEntity(member, timeTable, request.getCategory(), request.getPaymentAmount(), request.getPaymentDate());

        enrollmentRepository.save(enrollment);
    }
}
