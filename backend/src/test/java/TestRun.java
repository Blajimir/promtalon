import org.junit.Test;

public class TestRun {
    @Test
    public void test(){
        System.out.println("path "+ TestRun.class.getProtectionDomain().getCodeSource().
                getLocation().getPath());
    }

}
