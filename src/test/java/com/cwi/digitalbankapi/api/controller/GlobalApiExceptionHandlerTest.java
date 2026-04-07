package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.domain.transfer.exception.TransferAmountMustBePositiveException;
import com.cwi.digitalbankapi.shared.response.ApiErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.mock.web.MockHttpServletRequest;

import java.lang.reflect.Method;

class GlobalApiExceptionHandlerTest {

    private final GlobalApiExceptionHandler globalApiExceptionHandler = new GlobalApiExceptionHandler();

    @Test
    @DisplayName("Deve retornar key e value quando a excecao de negocio for tratada")
    void shouldReturnKeyAndValueWhenBusinessExceptionIsHandled() {
        // GIVEN
        TransferAmountMustBePositiveException transferAmountMustBePositiveException = new TransferAmountMustBePositiveException();

        // WHEN
        ApiErrorResponse apiErrorResponse = globalApiExceptionHandler
            .handleBusinessException(transferAmountMustBePositiveException)
            .getBody();

        // THEN
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, globalApiExceptionHandler
            .handleBusinessException(transferAmountMustBePositiveException)
            .getStatusCode());
        Assertions.assertEquals("TRANSFER_AMOUNT_MUST_BE_POSITIVE", apiErrorResponse.key());
        Assertions.assertEquals("O valor da transferencia deve ser maior que zero.", apiErrorResponse.value());
    }

    @Test
    @DisplayName("Deve retornar key e value padronizados quando a validacao do payload falhar")
    void shouldReturnStandardizedKeyAndValueWhenPayloadValidationFails() throws Exception {
        // GIVEN
        BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(new Object(), "transferRequest");
        beanPropertyBindingResult.addError(new FieldError(
            "transferRequest",
            "sourceAccountId",
            "O campo sourceAccountId e obrigatorio."
        ));
        Method transferMethod = TransferController.class.getDeclaredMethod(
            "transfer",
            com.cwi.digitalbankapi.application.dto.TransferRequest.class
        );
        MethodParameter methodParameter = new MethodParameter(transferMethod, 0);
        MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(
            methodParameter,
            beanPropertyBindingResult
        );

        // WHEN
        ApiErrorResponse apiErrorResponse = (ApiErrorResponse) globalApiExceptionHandler
            .handleMethodArgumentNotValid(
                methodArgumentNotValidException,
                HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST,
                new ServletWebRequest(new MockHttpServletRequest())
            )
            .getBody();

        // THEN
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, globalApiExceptionHandler
            .handleMethodArgumentNotValid(
                methodArgumentNotValidException,
                HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST,
                new ServletWebRequest(new MockHttpServletRequest())
            )
            .getStatusCode());
        Assertions.assertEquals("INVALID_REQUEST_DATA", apiErrorResponse.key());
        Assertions.assertEquals("O campo sourceAccountId e obrigatorio.", apiErrorResponse.value());
    }

    @Test
    @DisplayName("Deve retornar key e value padronizados quando o corpo da requisicao estiver invalido")
    void shouldReturnStandardizedKeyAndValueWhenRequestBodyIsInvalid() {
        // GIVEN
        HttpMessageNotReadableException httpMessageNotReadableException = new HttpMessageNotReadableException(
            "Corpo invalido"
        );

        // WHEN
        ApiErrorResponse apiErrorResponse = (ApiErrorResponse) globalApiExceptionHandler
            .handleHttpMessageNotReadable(
                httpMessageNotReadableException,
                HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST,
                new ServletWebRequest(new MockHttpServletRequest())
            )
            .getBody();

        // THEN
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, globalApiExceptionHandler
            .handleHttpMessageNotReadable(
                httpMessageNotReadableException,
                HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST,
                new ServletWebRequest(new MockHttpServletRequest())
            )
            .getStatusCode());
        Assertions.assertEquals("INVALID_REQUEST_DATA", apiErrorResponse.key());
        Assertions.assertEquals("O corpo da requisicao esta invalido.", apiErrorResponse.value());
    }

    @Test
    @DisplayName("Deve retornar key e value padronizados quando o parametro possuir formato invalido")
    void shouldReturnStandardizedKeyAndValueWhenParameterHasInvalidFormat() {
        // GIVEN
        MethodArgumentTypeMismatchException methodArgumentTypeMismatchException = new MethodArgumentTypeMismatchException(
            "abc",
            Long.class,
            "accountId",
            null,
            new NumberFormatException("For input string: abc")
        );

        // WHEN
        ApiErrorResponse apiErrorResponse = globalApiExceptionHandler
            .handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException)
            .getBody();

        // THEN
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, globalApiExceptionHandler
            .handleMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException)
            .getStatusCode());
        Assertions.assertEquals("INVALID_REQUEST_DATA", apiErrorResponse.key());
        Assertions.assertEquals("O parametro informado possui formato invalido.", apiErrorResponse.value());
    }
}
