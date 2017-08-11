import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.util.StringUtils;
import ru.promtalon.entity.AcceptOperation;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestRun {
    private String chars = "0123456789";
    private Random random = new Random();
    private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

    @Test
    public void testStream(){
        List<AcceptOperation.OperationType> typeList = new ArrayList<>();
        typeList.add(AcceptOperation.OperationType.ACCEPT_MAIL);
        typeList.add(AcceptOperation.OperationType.ACCEPT_MAIL);
        typeList.add(AcceptOperation.OperationType.ACCEPT_PHONE);
        AcceptOperation.OperationType type = typeList.stream()
                .filter(t -> t == AcceptOperation.OperationType.RESET_PASSWORD).findFirst().orElse(null);
        System.out.println("Test stream: " + type);
        type = typeList.stream()
                .filter(t -> t == AcceptOperation.OperationType.ACCEPT_MAIL).findFirst().orElse(null);
        System.out.println("Test stream: " + type);
    }

    @Test
    public void test(){
        System.out.println("path "+ TestRun.class.getProtectionDomain().getCodeSource().
                getLocation().getPath());
    }
    @Test
    public void testCodegen(){
        List<String> tokens = new ArrayList<>();
        int collision = 0;
        for (int i = 0; i < 30000; i++) {
            String token = tryUniqCodeForSender(tokens);
            if(tokens.contains(token)) collision++;
            tokens.add(token);
            System.out.println(token);
            //System.out.println(passwordEncoder.encodePassword(token,i));
        }
        System.out.println("Collisions: "+collision);

    }

    public String getToken(int length){
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(chars.charAt(random.nextInt(chars.length())));
        }
        return token.toString();
    }

    private String tryUniqCodeForSender( List<String> acceptList) {
        String code = getToken(8);
        if (acceptList.size() > 0)
            for (int i = 0; i < 10; i++) {
                String finalCode = code;
                if (acceptList.stream().anyMatch(item -> item.equals(finalCode))) {
                    code = getToken(8);
                } else break;
            }
        return code;
    }

}
