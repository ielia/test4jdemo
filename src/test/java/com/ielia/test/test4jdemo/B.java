package com.ielia.test.test4jdemo;

public class B extends A {
    public void x(int arg) {
        System.out.println("B.x(" + arg + ")");
        super.x(arg - 1);
    }
    public void y() {
        System.out.println("B.y");
        super.z();
    }
    public void z() {
        System.out.println("B.z");
        super.z();
    }
}
