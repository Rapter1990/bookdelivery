package com.example.demo.logging.aop;

import com.example.demo.exception.book.BookNotFoundException;
import com.example.demo.logging.entity.LogEntity;
import com.example.demo.logging.service.LogService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class LoggerAspectJ {

    private final LogService logService;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerPointcut() {

    }
    @AfterThrowing(pointcut = "restControllerPointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) throws IOException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

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
            e.printStackTrace();
        }
    }

    @AfterReturning(value = "restControllerPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) throws IOException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String response = "";

        LogEntity logEntity = LogEntity.builder()
                .endpoint(request.getRequestURL().toString())
                .method(request.getMethod())
                .operation(joinPoint.getSignature().getName())
                .build();

        if (result instanceof JsonNode) {
            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.writeValueAsString(result);
        } else {
            response = result.toString();
        }

        logEntity.setResponse(response);
        logEntity.setMessage(response);

        try {
            logService.saveLogToDatabase(logEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getHttpStatusFromException(Exception ex) {
        if (ex instanceof BookNotFoundException) {
            return "404 Not Found";
        //} else if (ex instanceof UnauthorizedException) {
        //    return "401 Unauthorized";
        //} else if (ex instanceof ForbiddenException) {
        //    return "403 Forbidden";
        //} else if (ex instanceof InternalServerErrorException) {
        //    return "500 Internal Server Error";
        } else {
            return "Unknown"; // Handle other exceptions as needed
        }
    }
}
