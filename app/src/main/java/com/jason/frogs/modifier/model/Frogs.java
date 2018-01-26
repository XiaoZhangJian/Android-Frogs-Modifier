package com.jason.frogs.modifier.model;

/**
 * @author Jason
 * @time 2018/1/25
 */

public class Frogs {

    // 坐标
    public int offset;
    // 值
    public int value;


    public Frogs(int offset) {
        this.offset = offset;
    }

    public Frogs(int offset, int value) {
        this.offset = offset;
        this.value = value;
    }

}
