package ClassLoader.ActualCombatClassLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * java 反射测试类
 */
public class PrivateCarReflect {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Class clazz = classLoader.loadClass("ClassLoader.ActualCombatClassLoader.PrivateCar");
        PrivateCar privateCar = (PrivateCar) clazz.newInstance();


        Field colorField = clazz.getDeclaredField("color");
        colorField.setAccessible(true);
        colorField.set(privateCar, "红色");
        privateCar.drive();

//        Method method = clazz.getDeclaredMethod("drive", (Class[]) null);
//
//        method.setAccessible(true);
//        method.invoke(privateCar, (Object[]) null);
    }
}
