package com.ielia.test.test4jdemo;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListener implements ITestListener {
    public void onTestStart(ITestResult result) {
        System.out.println("onTestStart");
    }

    public void onStart(ITestContext context) {
        System.out.println("onStart");
    }
}
