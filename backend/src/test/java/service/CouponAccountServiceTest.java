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
import ru.promtalon.entity.Client;
import ru.promtalon.entity.Contact;
import ru.promtalon.entity.CouponAccount;
import ru.promtalon.entity.User;
import ru.promtalon.service.ClientService;
import ru.promtalon.service.CouponAccountService;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class CouponAccountServiceTest {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CouponAccountService accountService;

    @Test
    @Transactional
    @Rollback
    public void testFindMethods(){
        Client client = new Client();
        client.setUser(new User());
        client.setContact(new Contact());
        client.getUser().setUsername("Vasia");
        client.getUser().setPassword("12345");
        client.getContact().setPhone("5552255");
        client.getContact().setEmail("aa@bb.cc");
        client.setFirstName("Vasiliy");
        client.setLastName("Vasiliev");
        client = clientService.regNewClient(client);
        Assert.assertNotNull("client did'n create",clientService.getClient(client.getId()));
        CouponAccount account = accountService.createAccount(client, BigDecimal.valueOf(0));
        Assert.assertNotNull("account did'n create",clientService.getClient(client.getId()));
    }
}
