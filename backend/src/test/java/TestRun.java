import org.junit.Test;

/**
 * Created by blajimir on 09.02.2017.
 */
public class TestRun {
    @Test
    public void test(){
        System.out.println("path "+ TestRun.class.getProtectionDomain().getCodeSource().
                getLocation().getPath());
    }

}
