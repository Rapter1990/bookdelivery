package com.example.demo.logging.aop;

import com.example.demo.exception.AlreadyException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.ProcessException;
import com.example.demo.logging.entity.LogEntity;
import com.example.demo.logging.service.LogService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.Optional;

/**
 * Aspect class for logging exceptions and responses in a Spring application.
 */
@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class LoggerAspectJ {

    private final LogService logService;

    /**
     * Pointcut definition for methods within Spring RestControllers.
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerPointcut() {

    }

    /**
     * Advice method to log exceptions thrown from RestControllers.
     *
     * @param joinPoint The join point where the exception occurred.
     * @param ex        The exception that was thrown.
     */
    @AfterThrowing(pointcut = "restControllerPointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {

        Optional<ServletRequestAttributes> requestAttributes = Optional.ofNullable(
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()
        );

        if (requestAttributes.isPresent()) {

            final HttpServletRequest request = requestAttributes.get().getRequest();

            LogEntity logEntity = LogEntity.builder()
                    .endpoint(request.getRequestURL().toString())
                    .method(request.getMethod())
                    .message(ex.getMessage())
                    .errorType(ex.getClass().getName())
                    .status(getHttpStatusFromException(ex))
                    .operation(joinPoint.getSignature().getName())
                    .response(ex.getMessage())
                    .build();

            // Get the username from SecurityContextHolder and set it in logEntity
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
                String username = authentication.getName();
                logEntity.setUserInfo(username);
            }

            try {
                logService.saveLogToDatabase(logEntity);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else {
            log.error("Request Attributes are null!");
        }

    }

    /**
     * Advice method to log successful responses from RestControllers.
     *
     * @param joinPoint The join point where the response was generated.
     * @param result    The result returned by the controller method.
     * @throws IOException If there is an error while converting the response to a JSON string.
     */
    @AfterReturning(value = "restControllerPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) throws IOException {

        Optional<ServletRequestAttributes> requestAttributes = Optional.ofNullable(
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()
        );

        if (requestAttributes.isPresent()) {

            final HttpServletRequest request = requestAttributes.get().getRequest();
            final HttpServletResponse response = requestAttributes.get().getResponse();

            String responseObject = "";

            LogEntity logEntity = LogEntity.builder()
                    .endpoint(request.getRequestURL().toString())
                    .method(request.getMethod())
                    .operation(joinPoint.getSignature().getName())
                    .build();

            if (result instanceof JsonNode) {
                ObjectMapper objectMapper = new ObjectMapper();
                responseObject = objectMapper.writeValueAsString(result);
            } else {
                responseObject = result.toString();
            }

            logEntity.setResponse(responseObject);
            logEntity.setMessage(responseObject);
            Optional.ofNullable(response).ifPresent(
                    httpServletResponse -> logEntity.setStatus(
                            HttpStatus.valueOf(httpServletResponse.getStatus()
                            )
                    ));

            try {
                logService.saveLogToDatabase(logEntity);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        } else {
            log.error("Request Attributes are null!");
        }
    }

    /**
     * Helper method to determine HttpStatus from an exception.
     *
     * @param ex The exception for which to determine the HttpStatus.
     * @return The HttpStatus corresponding to the exception, or null if not recognized.
     */
    private HttpStatus getHttpStatusFromException(Exception ex) {

        if (ex instanceof NotFoundException) {
            return NotFoundException.STATUS;
        } else if (ex instanceof AlreadyException) {
            return AlreadyException.STATUS;
        } else if (ex instanceof ProcessException) {
            return ProcessException.STATUS;
        }

        return null; // Handle other exceptions as needed
    }
}
