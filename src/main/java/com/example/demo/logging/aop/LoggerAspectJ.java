package com.example.demo.logging.aop;

import com.example.demo.exception.NotFoundException;
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
                e.printStackTrace();
            }
        } else {
            log.error("Request Attributes are null!");
        }

    }

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


    private HttpStatus getHttpStatusFromException(Exception ex) {
        if (ex instanceof NotFoundException) {
            return NotFoundException.STATUS;
            //} else if (ex instanceof UnauthorizedException) {
            //    return "401 Unauthorized";
            //} else if (ex instanceof ForbiddenException) {
            //    return "403 Forbidden";
            //} else if (ex instanceof InternalServerErrorException) {
            //    return "500 Internal Server Error";
        } else {
            return null; // Handle other exceptions as needed
        }
    }
}
