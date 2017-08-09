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
import ru.promtalon.entity.*;
import ru.promtalon.service.*;
import ru.promtalon.service.exception.CouponAccountException;
import ru.promtalon.service.impl.MockSmsServiceImpl;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class CouponAccountServiceTest {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CouponAccountService accountService;
    @Autowired
    private CouponOperationService operationService;
    @Autowired
    private AcceptCouponOperationService acceptService;

    @Autowired
    private MockSmsServiceImpl smsService;

    private volatile Map<Date, String> operations = new HashMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    @Test
    @Transactional
    @Rollback(false)
    public void testFindMethods() {
        Client client = getClient("Vasia", "12345", "5552255",
                "aa@bb.cc", "Vasiliy", "Vasiliev");
        client = clientService.regNewClient(client);
        Assert.assertNotNull(String.format("client username:%s did't create", client.getUser().getUsername()),
                clientService.getClient(client.getId()));
        CouponAccount account = accountService.createAccount(client, BigDecimal.valueOf(0));
        Assert.assertNotNull("account did't create", account);
        account = accountService.getAccount(account.getId());
        Assert.assertNotNull("account ", account);
        System.out.printf("%nAcct:%s%n", account);
        long vasiaId = client.getId();

        client = getClient("Petia", "12345", "5552225",
                "aaaa@bb.cc", "Petr", "Petrov");
        client = clientService.regNewClient(client);
        Assert.assertNotNull(String.format("client id:%d did't create", client.getId()),
                clientService.getClient(client.getId()));
        account = accountService.createAccount(client, BigDecimal.valueOf(0));
        Assert.assertNotNull("account did't create", account);
        client = clientService.blockClient(client.getId());
        Assert.assertFalse(String.format("client id:%d did't block", client.getId()), client.getUser().isEnabled());
        account = accountService.getAccount(account.getId());
        Assert.assertNotNull(String.format("account id:%d did't get", account.getId()), account);
        System.out.printf("%nAcct:%s%n", account);
        long petiaId = client.getId();

        client = getClient("Vova", "54321", "5552220",
                "aab@bb.cc", "Vladimir", "Vovanov");
        client = clientService.regNewClient(client);
        Assert.assertNotNull(String.format("client id:%d did't create", client.getId()),
                clientService.getClient(client.getId()));
        account = accountService.createAccount(client, BigDecimal.valueOf(0));
        Assert.assertNotNull("account did't create", account);
        client = clientService.deactivateClient(client.getId());
        Assert.assertNull(String.format("client id:%s did't diactivate", client.getId()), client.getUser());
        account = accountService.getAccount(account.getId());
        Assert.assertNotNull(String.format("account id:%d did't get", account.getId()), account);
        System.out.printf("%nAcct:%s%n", account);
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

    @Test
    @Rollback(false)
    public void couponOperationTest() {
        CouponAccount acctVasia = getRegAcct("Vasia", "12345", "5552255",
                "aa@bb.cc", "Vasiliy", "Vasiliev", 30L);

        CouponAccount acctPetia = getRegAcct("Petia", "12345", "5552225",
                "aaaa@bb.cc", "Petr", "Petrov", 10L);

        CouponOperation operation = operationService.addTransferOperation(acctVasia.getClient(), acctPetia.getClient(), 7L);
        Assert.assertNotNull("operation don't create", operation);
        operation = operationService.completeTransformAndPaymentOperation(acctVasia.getClient(), operation.getId(),
                smsService.getCode().split("-")[0]);
        Assert.assertNotNull("operation don't complete", operation);
        System.out.println("Vasia amount: " + accountService.getAccountByClient(acctVasia.getClient().getId()).getAmount());
        System.out.println("Petia amount: " + accountService.getAccountByClient(acctPetia.getClient().getId()).getAmount());
        System.out.println("Code: " + smsService.getCode());
    }

    @Test
    @Rollback(false)
    public void couponOperationTxTest() {
        CouponAccount acctVasia = getRegAcct("Vasia", "12345", "5552255",
                "aa@bb.cc", "Vasiliy", "Vasiliev", 5L);

        CouponAccount acctPetia = getRegAcct("Petia", "12345", "5552225",
                "aaaa@bb.cc", "Petr", "Petrov", 10L);
        CouponOperation operation = null;
        try {
            operation = operationService.addTransferOperation(acctVasia.getClient(), acctPetia.getClient(), 7L);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Assert.assertNull("wrong operation create", operation);

        System.out.println("Vasia amount: " + accountService.getAccountByClient(acctVasia.getClient().getId()).getAmount());
        System.out.println("Petia amount: " + accountService.getAccountByClient(acctPetia.getClient().getId()).getAmount());
        System.out.println("Code: " + smsService.getCode());
    }

    @Test()
    @Rollback(false)
    public void couponOperationConcurentTest() {
        CouponAccount acctVu = getRegAcct("Vasia", "12345", "5552255",
                "aa@bb.cc", "Vasiliy", "Vasiliev", 100L);

        CouponAccount acctPu = getRegAcct("Petia", "12345", "5552225",
                "aaaa@bb.cc", "Petr", "Petrov", 100L);

        //noinspection Duplicates
        Thread trV = getThread(acctVu, acctPu, 50);
        //noinspection Duplicates
        Thread trP = getThread(acctPu, acctVu, 50);
        trV.start();
        trP.start();

        while (trV.isAlive() || trV.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //this.operations.forEach(System.out::println);
        TreeMap<Date, String> sortMap = new TreeMap<>(Comparator.naturalOrder());
        sortMap.putAll(operations);
        sortMap.forEach((aLong, s) -> System.out.println(sdf.format(aLong) + "  --" + s));

        System.out.println(String.format("%s amount: %s", acctVu.getClient().getUser().getUsername(),
                accountService.getAccountByClient(acctVu.getClient().getId()).getAmount()));

        System.out.println(String.format("%s amount: %s", acctPu.getClient().getUser().getUsername(),
                accountService.getAccountByClient(acctPu.getClient().getId()).getAmount()));

        System.out.println("end!");
    }


    @Ignore
    public Client getClient(String username, String pass, String phone, String mail, String firstname, String lastname) {
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

    @Ignore
    public CouponAccount getRegAcct(String username, String pass, String phone, String mail, String firstName,
                                    String lastName, Long amount) {
        Client client = getClient(username, pass, phone, mail, firstName, lastName);
        client = clientService.regNewClient(client);
        client = clientService.getClient(client.getId());
        Assert.assertNotNull(String.format("client username:%s did't create", client.getUser().getUsername()),
                client);
        client.getContact().setPhoneAccept(true);
        Assert.assertTrue(String.format("client username:%s did't update", client.getUser().getUsername()),
                clientService.updateClient(client).getContact().isPhoneAccept());
        return accountService.createAccount(client, BigDecimal.valueOf(amount));
    }

    @Ignore
    public Thread getThread(CouponAccount acctV, CouponAccount acctP, int loop) {
        return new Thread(() -> {
            int l = loop;
            Random random = new Random();
            Map<Date, String> opers = new HashMap<>();
            CouponOperation operation = null;
            int transferAmount = 2;

            while (l > 0) {
                try {
                    operation = operationService.addTransferOperation(acctV.getClient(), acctP.getClient(), transferAmount);
                    opers.put(new Date(), String.format("Время:%s Операция id: %d, перевод %dпт от %s на счету было: %d стало: %d",
                            sdf.format(operation.getRegTimestamp()), operation.getId(), operation.getAmount(),
                            operation.getSender().getClient().getUser().getUsername(),
                            operation.getSender().getAmount().intValue() + transferAmount, operation.getSender().getAmount().intValue()));
                    while (true) {
                        try {
                            Thread.sleep(random.nextInt(50)+50);
                            CouponOperation couponOperation = operationService.completeTransformAndPaymentOperation(acctV.getClient(), operation.getId(),
                                    smsService.getCodes().get(acctV.getClient().getId()).split("-")[0]);
                            opers.put(new Date(), String.format("Время:%s Операция id: %d, поступление %dпт к %s на счету: %d",
                                    sdf.format(couponOperation.getChangeTimestamp()), operation.getId(), couponOperation.getAmount(),
                                    couponOperation.getReceiver().getClient().getUser().getUsername(),
                                    couponOperation.getReceiver().getAmount().intValue()));
                            l--;
                            break;
                        } catch (Exception e) {
                            System.err.println("Rollback when completeTransformAndPaymentOperation operation details: " + e.getMessage());
                        }
                        try {
                            CouponOperation couponOperation = operationService.cancelOperation(operation);
                            opers.put(new Date(), String.format("Время:%s Операция id: %d, отмена и возврат %dпт к %s на счету: %d",
                                    sdf.format(couponOperation.getChangeTimestamp()), operation.getId(), couponOperation.getAmount(),
                                    couponOperation.getSender().getClient().getUser().getUsername(),
                                    couponOperation.getSender().getAmount().intValue()));
                            l--;
                            break;
                        } catch (Exception e) {
                            System.err.println("Rollback when cancelOperation operation details: " + e.getMessage());
                        }
                    }
                } catch (CouponAccountException e) {
                    System.err.println("CouponAccountException Rollback when addTransferOperation operation details: " + e.getMessage() + " class: " + e.getClass().getName());
                    break;
                } catch (Exception e) {
                    System.err.println("Rollback when addTransferOperation operation details: " + e.getMessage());
                }

            }
            this.operations.putAll(opers);
        });
    }

}
