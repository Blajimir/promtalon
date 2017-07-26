import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestRun {
    private String chars = "0123456789ABCHKMPTX";
    private Random random = new Random();
    private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
    @Test
    public void test(){
        System.out.println("path "+ TestRun.class.getProtectionDomain().getCodeSource().
                getLocation().getPath());
    }
    @Test
    public void testCodegen(){
        List<String> tokens = new ArrayList<>();
        int collision = 0;
        for (int i = 0; i < 1000; i++) {
            String token = getToken(8);
            if(tokens.contains(token)) collision++;
            tokens.add(token);
            System.out.println(token);
            System.out.println(passwordEncoder.encodePassword(token,i));
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

}
