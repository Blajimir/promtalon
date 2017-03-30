import org.junit.Test;

/**
 * Created by blajimir on 09.02.2017.
 */
public class TestClassLoader extends ClassLoader{
    //private ClassLoader parent = TestClassLoader.class.getClassLoader();

    public TestClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class<?> regClass(String name, byte[] b, int off, int len){

        return this.defineClass(name, b, off, len);
    }
    @Test
    public void test(){
        System.out.println("path "+ TestClassLoader.class.getProtectionDomain().getCodeSource().
                getLocation().getPath());

    }
}
