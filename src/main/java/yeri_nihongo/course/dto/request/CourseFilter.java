package yeri_nihongo.course.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CourseFilter {

    LocalDate date;
}
