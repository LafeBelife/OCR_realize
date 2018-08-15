package ClassLoader.ActualCombatClassLoader;

/**
 * java 反射 测试类
 */
public class PrivateCar {
    // private 成员变量：使用传统方式，只能在本类中访问
    private String color;

    // protected 方法: 使用传统的类实例调用方式，只能在本类和本包中访问
    protected void drive(){
        System.out.println("颜色是：" + this.color);
    }
}
