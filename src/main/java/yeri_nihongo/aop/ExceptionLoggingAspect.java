package yeri_nihongo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExceptionLoggingAspect {

    @AfterThrowing(pointcut = "execution(* com.academy.backend.service..*(..))", throwing = "ex")
    public void logException(Exception ex) {
        log.error("예외 발생: {}", ex.getMessage(), ex);
    }
}
