package com.ielia.test.test4jdemo;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

public class ShitTest {
    protected String lang;

    @Parameters("Language")
    public ShitTest(String lang) {
        this.lang = lang;
    }

    @DataProvider(name = "fruits", parallel = true)
    public Object[][] data() {
        // String[] langs = new String[] { "English", "Latin", "Italiano" };
        List<String[]> allParams = new LinkedList<>();
        // for (String lang : langs) {
        allParams.add(new String[]{lang, "banana", "Banana"});
        allParams.add(new String[]{lang, "mango", "Mango"});
        allParams.add(new String[]{lang, "kiwi", "Kiwi"});
        allParams.add(new String[]{lang, "melon", "Melon"});
        allParams.add(new String[]{lang, "papaya", "Papaya"});
        // }
        return allParams.toArray(new Object[0][0]);
    }

    @Test(dataProvider = "fruits")
    public void test_lala(String lang, String fruit, String ucFruit) {
        System.out.println("PARAMS: " + lang + ", " + fruit + " " + ucFruit);
        Assert.assertTrue(true);
    }

    @Test
    public void testSuper() {
        new C().x(4);
    }
}
