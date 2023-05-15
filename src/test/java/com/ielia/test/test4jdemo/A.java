package com.ielia.test.test4jdemo;

public class A {
    public void x(int arg) {
        System.out.println("A.x(" + arg + ")");
        if (arg > 0) {
            this.x(arg - 1);
        }
    }
    public void y() {
        System.out.println("A.y");
    }
    public void z() {
        System.out.println("A.z");
    }
}
