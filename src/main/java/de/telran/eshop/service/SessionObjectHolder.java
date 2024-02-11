package de.telran.eshop.service;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Компонент Spring для хранения состояния между запросами для каждого пользователя в рамках их сессии.
 * Каждый экземпляр этого класса создается для каждого пользователя и сохраняет свое состояние между запросами.
 */
@Getter
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionObjectHolder {

    private long amountClicks = 0;

    /**
     * Создает новый объект SessionObjectHolder.
     */
    public SessionObjectHolder() {
        System.out.println("Session object created. ");  // выведет в консоль информацию
    }

    /**
     * Увеличивает количество кликов на единицу.
     */
    public void addClicks() {
        amountClicks++;
    }
}
