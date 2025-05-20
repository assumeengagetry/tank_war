/**
 * Direction枚举定义了游戏中物体可以移动的四个基本方向
 * 
 * 这个枚举展示了以下Java概念：
 * 
 * 1. 枚举（Enum）：
 *    - 是Java中特殊的类型，用于定义一组固定的常量
 *    - 每个枚举常量本质上是该枚举类型的一个实例
 *    - 枚举类型是类型安全的，编译器会检查类型匹配
 * 
 * 2. 常量定义：
 *    - UP, DOWN, LEFT, RIGHT 是四个枚举常量
 *    - 这些常量是公共的、静态的、最终的（public static final）
 * 
 * 3. 类型安全：
 *    - 使用枚举可以避免使用魔法数字（如 0,1,2,3 表示方向）
 *    - 可以在switch语句中使用，编译器会检查所有情况是否都已处理
 * 
 * 4. 最佳实践：
 *    - 当需要表示一组固定的值时，应该使用枚举而不是常量
 *    - 枚举提供了类型安全和更好的代码可读性
 */
public enum Direction {
    /** 向上移动 */
    UP,
    /** 向下移动 */
    DOWN,
    /** 向左移动 */
    LEFT,
    /** 向右移动 */
    RIGHT;
    
    // 注意：枚举可以有方法和构造器
    // 例如，我们可以添加一个方法来获取相反的方向：
    /*
    public Direction getOpposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
    */
}
