package ru.promtalon.controller;

import lombok.extern.java.Log;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * Контроллер получения токена для текущей сессии, необходимо указать его в заголовке пакета "X-CSRF-TOKEN"
 *
 * @author Aleksandr aka Blajimir
 * @since 2017-08-09
 * */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@Log
public class SecurityController {

    @GetMapping(value = "api/token")
    @ResponseBody
    public Object getToken(HttpServletRequest request){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return Collections.singletonMap(csrfToken.getParameterName(),csrfToken.getToken());
    }
}
