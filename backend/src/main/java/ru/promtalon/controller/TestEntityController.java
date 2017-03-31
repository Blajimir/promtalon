package ru.promtalon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.promtalon.entity.TestEntity;

@Controller
@RequestMapping("/api/test")
public class TestEntityController {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public TestEntity getTestEntity(){
        TestEntity entity = new TestEntity();
        entity.setId(1);
        entity.setName("SomeEntity");
        entity.setDiscription("I am some entity =)");
        return entity;
    }
}
