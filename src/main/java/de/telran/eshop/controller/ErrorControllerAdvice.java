package de.telran.eshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Этот совет контроллера обрабатывает исключения в приложении.
 * Он предоставляет централизованный способ обработки исключений и возврата страницы ошибки с сообщением об ошибке.
 */
@ControllerAdvice
public class ErrorControllerAdvice {

    /**
     * Обрабатывает исключения типа Exception.
     * Устанавливает код состояния HTTP 500 (Внутренняя ошибка сервера) и добавляет сообщение об ошибке в модель.
     * @param exception Исключение, которое произошло
     * @param model Spring модель, используемая для передачи данных в представление
     * @return Имя представления ошибки
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(Exception exception, Model model){

        String errorMessage =(exception != null ? exception.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
