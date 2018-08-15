package reflectPrivate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 设置私有变量，验证java 反射机制访问。
 */
public class PrivateCar {
    // private 成员变量：使用传统的类实例调用方式，只能在本类中访问
    private String color;

    // protected 方法：使用传统的类实例调用方式，只能在子类和本包中访问
    protected void drive() {
        System.out.println("java 类反射机制执行成功！" + color);
    }
}





class PrivateCarReflect{

    public static void main(String[] args) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class clazz = null;
        try {
            clazz = loader.loadClass("reflectPrivate.PrivateCar");
            PrivateCar privateCar = (PrivateCar) clazz.newInstance();

            Field field = clazz.getDeclaredField("color");

            // 取消java 访问检查以访问private
            field.setAccessible(true);
            field.set(privateCar,"红色");

            Method method = clazz.getDeclaredMethod("drive",(Class[]) null);

            // 取消java 语言访问检查以访问 protected driver方法
            method.setAccessible(true);
            method.invoke(privateCar,(Object[])null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
                e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}