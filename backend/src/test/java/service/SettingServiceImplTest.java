package service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.promtalon.config.WebConfig;
import ru.promtalon.entity.Setting;
import ru.promtalon.service.SettingService;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class SettingServiceImplTest {

    @Autowired
    SettingService settingService;

    @Test
    @Rollback(false)
    public void crudTest(){
        Setting setting1 = new Setting();
        setting1.setName("setting1");
        HashMap<String,String> setts = new HashMap<>();
        setts.put("s1","str1");
        setts.put("s2","str2");
        setts.put("s3","str3");
        setting1.setProps(setts);
        setting1 = settingService.createSetting(setting1);

        setts.put("s2","2str");

        setting1 = settingService.getSettingByName(setting1.getName());

        Assert.assertTrue("props not equals", setting1.getProps().get("s1").equals(setts.get("s1")));
        Assert.assertFalse("props not equals", setting1.getProps().get("s2").equals(setts.get("s2")));
        Assert.assertTrue("props not equals", setting1.getProps().get("s3").equals(setts.get("s3")));

        Setting setting2 = new Setting();

        setting2.setName("setting2");
        setting2.setProps(new HashMap<>());
        setting2.getProps().put("st0","1qaz");
        setting2.getProps().put("s1","2wsx");
        setting2.getProps().put("s2","3edc");
        setting2.getProps().put("st3","5tgb");
        setting2.getProps().put("st4","4frv");
        setting2.getProps().put("st5","6yyhn");
        setting2.getProps().put("st6","7uum");
        setting2.getProps().put("st7","8iki,");
        setting2.getProps().put("st8","9ol.");

        settingService.createSetting(setting2);
        setting2 = settingService.getSettingByName(setting2.getName());

        System.out.println(setting2.getProps());
    }
}
