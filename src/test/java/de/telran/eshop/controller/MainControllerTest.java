package de.telran.eshop.controller;

import de.telran.eshop.service.SessionObjectHolder;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Класс, содержащий тесты для проверки функциональности класса MainController.
 */
@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    @Mock
    SessionObjectHolder sessionObjectHolder;

    @Mock
    Model model;

    @Mock
    HttpSession httpSession;

    /**
     * Проверяет корректность отображения главной страницы приложения.
     */
    @Test
    void indexTest() {
        MainController controller = new MainController(sessionObjectHolder);

        // Mocking sessionObjectHolder behavior
        when(sessionObjectHolder.getAmountClicks()).thenReturn(10L);

        String viewName = controller.index(model, httpSession);

        // Verifying method return value
        assertEquals("index", viewName);

        // Verifying method calls
        verify(sessionObjectHolder).getAmountClicks();
        verify(httpSession, atLeastOnce()).getAttribute("myID");
        verify(httpSession).setAttribute(eq("myID"), anyString());
        verify(model).addAttribute("amountClicks", 10L);
        verify(model).addAttribute("uuid", httpSession.getAttribute("myID"));
    }

    /**
     * Проверяет корректность отображения страницы входа в систему.
     */
    @Test
    void loginTest() {
        MainController controller = new MainController(sessionObjectHolder);
        String viewName = controller.login();
        assertEquals("login", viewName);
    }

    /**
     * Проверяет обработку ошибки входа в систему и отображение соответствующей страницы.
     */
    @Test
    void loginErrorTest() {
        MainController controller = new MainController(sessionObjectHolder);
        Model model = Mockito.mock(Model.class);
        String viewName = controller.loginError(model);
        assertEquals("login", viewName);
        verify(model).addAttribute("loginError", true);
    }
}
