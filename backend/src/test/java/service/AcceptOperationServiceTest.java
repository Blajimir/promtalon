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
import ru.promtalon.dao.AcceptOperationDao;
import ru.promtalon.entity.AcceptOperation;
import ru.promtalon.entity.Client;
import ru.promtalon.service.AcceptOperationService;
import ru.promtalon.service.ClientService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class AcceptOperationServiceTest {

    @Autowired
    ClientService clientService;

    @Autowired
    AcceptOperationService operationService;
    @Autowired
    AcceptOperationDao operationDao;

    @Test
    @Rollback(false)
    public void createTest() {
        Client client = regSimpleUser();
        AcceptOperation operation = operationService.createAcceptPassword(client, AcceptOperation.ContactType.PHONE);
        Assert.assertNotNull("operation AcceptPassword is null!", operation);
        operation.setClient(null);
        System.out.println(String.format("operation: %s", operation.toString()));
        operation = operationService.createAcceptEmail(client);
        Assert.assertNotNull("operation AcceptEmail is null!", operation);
        operation.setClient(null);
        System.out.println(String.format("operation: %s", operation.toString()));
        operation = operationService.createAcceptPhone(client);
        Assert.assertNotNull("operation AcceptPhone is null!", operation);
        operation.setClient(null);
        System.out.println(String.format("operation: %s", operation.toString()));
        operationService.createAcceptPassword(client, AcceptOperation.ContactType.EMAIL);
        Assert.assertEquals("Incorrect rows number!", 3, operationDao.findAll().size());
    }

    @Test
    @Rollback(false)
    public void acceptTest() {
        Client client = regSimpleUser();
        AcceptOperation operation1 = operationService.createAcceptPassword(client, AcceptOperation.ContactType.PHONE);
        Assert.assertNotNull("operation AcceptPassword is null!", operation1);
        operation1.setClient(null);
        System.out.println(String.format("operation: %s", operation1.toString()));
        AcceptOperation operation2 = operationService.createAcceptEmail(client);
        Assert.assertNotNull("operation AcceptEmail is null!", operation2);
        operation2.setClient(null);
        System.out.println(String.format("operation: %s", operation2.toString()));

        boolean result = operationService.acceptOperation(client, operation1.getType(), operation1.getAcceptCode());
        Assert.assertTrue("Has not accept operation!", result);
        Assert.assertEquals("Incorrect rows number!", 1, operationDao.findAll().size());
        result = operationService.acceptOperation(client, operation2.getType(), "12345678");
        Assert.assertFalse("Has accept operation!", result);
        Assert.assertEquals("Incorrect rows number!", 1, operationDao.findAll().size());
    }

    @Ignore
    private Client regSimpleUser() {
        Client client = CouponAccountServiceTest.getClient("Vasia", "12345", "5552255",
                "aa@bb.cc", "Vasiliy", "Vasiliev");
        return clientService.regNewClient(client);
    }
}
