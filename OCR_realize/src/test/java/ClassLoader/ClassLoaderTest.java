package ClassLoader;

import org.junit.jupiter.api.Test;

/**
 * 类装载测试学习<br/>
 * @author 王保卫
 */
public class ClassLoaderTest {
    @Test
    public void loaderTest(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println();
        System.out.println(classLoader.getParent().getParent());
    }
}
