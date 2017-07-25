package service;

import org.junit.Assert;
import org.junit.Ignore;
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
import java.util.List;

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
    @Rollback(false)
    public void testFindMethods(){
        Client client = getClient("Vasia","12345","5552255",
                "aa@bb.cc","Vasiliy","Vasiliev");
        client = clientService.regNewClient(client);
        Assert.assertNotNull(String.format("client id:%s did't create",client.getId()),
                clientService.getClient(client.getId()));
        CouponAccount account = accountService.createAccount(client, BigDecimal.valueOf(0));
        Assert.assertNotNull("account did't create",account);
        account = accountService.getAccount(account.getId());
        Assert.assertNotNull("account ",account);
        System.out.printf("%nAcct:%s%n",account);
        long vasiaId = client.getId();

        client = getClient("Petia","12345","5552225",
                "aaaa@bb.cc","Petr","Petrov");
        client = clientService.regNewClient(client);
        Assert.assertNotNull(String.format("client id:%d did't create",client.getId()),
                clientService.getClient(client.getId()));
        account = accountService.createAccount(client, BigDecimal.valueOf(0));
        Assert.assertNotNull("account did't create",account);
        client = clientService.blockClient(client.getId());
        Assert.assertFalse(String.format("client id:%d did't block",client.getId()),client.getUser().isEnabled());
        account = accountService.getAccount(account.getId());
        Assert.assertNotNull(String.format("account id:%d did't get",account.getId()),account);
        System.out.printf("%nAcct:%s%n",account);
        long petiaId = client.getId();

        client = getClient("Vova","54321","5552220",
                "aab@bb.cc","Vladimir","Vovanov");
        client = clientService.regNewClient(client);
        Assert.assertNotNull(String.format("client id:%d did't create",client.getId()),
                clientService.getClient(client.getId()));
        account = accountService.createAccount(client, BigDecimal.valueOf(0));
        Assert.assertNotNull("account did't create",account);
        client = clientService.deactivateClient(client.getId());
        Assert.assertNull(String.format("client id:%s did't diactivate",client.getId()),client.getUser());
        account = accountService.getAccount(account.getId());
        Assert.assertNotNull(String.format("account id:%d did't get",account.getId()),account);
        System.out.printf("%nAcct:%s%n",account);
        long vovaId = client.getId();

        List<CouponAccount> accts = accountService.getAllAccount();
        System.out.println(String.format("%nAccts count: %d, accts:{ %s }%n%n", accts.size(), accts));

        Assert.assertTrue("account not exist, look Query",
                accountService.hasAccountWithActiveClientByClientId(vasiaId));
        Assert.assertFalse("account exist, look Query",
                accountService.hasAccountWithActiveClientByClientId(petiaId));
        Assert.assertFalse("account exist, look Query",
                accountService.hasAccountWithActiveClientByClientId(vovaId));

    }

    @Ignore
    public Client getClient(String username, String pass, String phone, String mail, String firstname, String lastname){
        Client client = new Client();
        client.setUser(new User());
        client.setContact(new Contact());
        client.getUser().setUsername(username);
        client.getUser().setPassword(pass);
        client.getContact().setPhone(phone);
        client.getContact().setEmail(mail);
        client.setFirstName(firstname);
        client.setLastName(lastname);
        return client;
    }
}
