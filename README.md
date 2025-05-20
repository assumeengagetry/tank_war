
这个项目的具体架构如下：
    oringin_object是一切开始，是最初的类
    之后是继承自他的（playertank enermytank missile wall steelwall grass river等等）
    然后是Panel这边去给他进行每一次按键的监听维护，还有初始化位置等等
    Direction 是方向的枚举