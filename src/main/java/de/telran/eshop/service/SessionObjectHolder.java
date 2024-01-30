package de.telran.eshop.service;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/*
Класс аннотирован как компонент Spring (@Component) с областью
видимости сеанса (@Scope(value = WebApplicationContext.SCOPE_SESSION)).
Это означает, что каждый пользователь будет иметь свой экземпляр этого объекта в рамках
своей сессии, и его состояние будет сохраняться между запросами.
 */
@Getter
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionObjectHolder {

    private long amountClicks = 0;

    public SessionObjectHolder() {
        System.out.println("Session object created. ");  // выведет в консоль информацию
    }

    public void addClicks() {
        amountClicks++;
    }
}
