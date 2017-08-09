package ru.promtalon.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.promtalon.entity.Client;
import ru.promtalon.service.ClientService;
import ru.promtalon.service.MyUserDetailsService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@Log
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    MyUserDetailsService userDetailsService;

    private Client getCurrentClient(){
        return clientService.getClientByUsername(userDetailsService.getCurrentUsername());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/api/client/{id}")
    @ResponseBody
    public Client getClient(@PathVariable("id") long id){
        return clientService.getClient(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/api/client")
    @ResponseBody
    public List<Client> getClient(){
        return clientService.getAllClients();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/api/client/block/{id}")
    @ResponseBody
    public Client blockClient(@PathVariable long id){
        return clientService.blockClient(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/api/client/unblock/{id}")
    @ResponseBody
    public Client unblockClient(@PathVariable long id){
        return clientService.unblockClient(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path="/api/client/me")
    @ResponseBody
    public Client getMyClient(){
        return getCurrentClient();
    }

    //TODO: Добавить валидаторы для пользовательских методов
    @PreAuthorize("isAuthenticated()")
    @PostMapping(path="/api/client/me")
    @ResponseBody
    public Client updateMyClient(@Valid @RequestBody Client client){
        return clientService.updateClientIgnoreFields(getCurrentClient(),client,
                Stream.of("User","regDate","contact").collect(Collectors.toList()));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(path="/api/client/mail/me")
    @ResponseBody
    public Client updateMailMyClient(@RequestBody @NotNull Client client){
        //TODO: добавить в сервис ф-цию обновления почты
        return null;
    }

}
