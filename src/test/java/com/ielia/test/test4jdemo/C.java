package com.ielia.test.test4jdemo;

public class C extends B {
    public void x(int arg) {
        System.out.println("C.x(" + arg + ")");
        super.x(arg - 1);
    }
    public void y() {
        System.out.println("C.y");
        super.y();
    }
    public void z() {
        System.out.println("C.y");
        super.z();
    }
}
