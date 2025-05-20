/**
 * grass接口代表游戏中的草地元素
 * 这个接口展示了面向对象编程中的以下概念：
 * 
 * 1. 接口（Interface）：
 *    - 接口是一种完全抽象的类型
 *    - 定义了一组类必须实现的方法（契约）
 *    - 可以用作标记接口，表示某种特性
 * 
 * 2. 标记接口（Marker Interface）：
 *    - 这是一个没有方法的接口
 *    - 类似于Java中的Serializable接口
 *    - 用于标识实现类具有某种特性
 * 
 * 3. 接口的作用：
 *    - 可以用于类型检查（instanceof）
 *    - 提供了一种对象分类的方式
 *    - 有助于代码的组织和维护
 */
public interface grass {
    // 建议添加的方法：
    // boolean isPassable();  // 是否可以通过
    // void onEnter(GameObject obj);  // 当对象进入草地时的行为
    // void onLeave(GameObject obj);  // 当对象离开草地时的行为
}
