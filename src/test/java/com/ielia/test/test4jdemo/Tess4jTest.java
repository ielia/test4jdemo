package com.ielia.test.test4jdemo;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class Tess4jTest {
    @SuppressWarnings("ConstantConditions")
    public static BufferedImage takeScreenshot() {
        try {
            // Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            // return new Robot().createScreenCapture(screenRect);
            return ImageIO.read(ClassLoader.getSystemClassLoader().getResource("test-screenshot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Mat buffImg2Mat(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "bmp", baos);
            byte[] pixels = baos.toByteArray();
            Mat mat = Mat.zeros(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
            mat.put(0, 0, pixels);
            return mat;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static BufferedImage invertImageColors(BufferedImage image) {
        Mat mat = buffImg2Mat(image);
        Core.bitwise_not(mat, mat);
        return mat2BufferedImage(mat);
    }

    public static BufferedImage mat2BufferedImage(Mat matrix) {
        try {
            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".jpg", matrix, mob);
            return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void printOCRRegions(Tesseract tesseract, String imgType, BufferedImage image) throws TesseractException {
        List<Rectangle> regions = tesseract.getSegmentedRegions(image, ITessAPI.TessPageIteratorLevel.RIL_TEXTLINE);
        for (Rectangle region : regions) {
            double x = region.getX();
            double y = region.getY();
            System.out.printf("{%s} REGION [(% 5.0f, % 5.0f) -> (% 5.0f, % 5.0f)]: %s\n",
                    imgType,
                    x,
                    y,
                    x + region.getWidth(),
                    y + region.getHeight(),
                    tesseract.doOCR(image, region));
        }
    }

    public static void printFullOCR(Tesseract tesseract, String imgType, BufferedImage image) throws TesseractException {
        System.out.printf(
                "%s:\n----------------------------------------\n%s\n----------------------------------------\n",
                imgType,
                tesseract.doOCR(image));
    }

    public static void showImage(String name, BufferedImage image) {
        Mat mat = buffImg2Mat(image);
        HighGui.imshow(name, mat);
        HighGui.waitKey();
    }

    @Test
    public void test() throws AWTException, TesseractException, URISyntaxException { // , IOException
        OpenCV.loadLocally();

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(new File(getClass().getClassLoader().getResource(".").toURI()).getAbsolutePath());

        BufferedImage capture = takeScreenshot();
        BufferedImage invertedCapture = invertImageColors(capture);
        showImage("normal capture", capture);
        showImage("inverted capture", invertedCapture);
        printFullOCR(tesseract, "normal", capture);
        printFullOCR(tesseract, "inverted", invertedCapture);
    }
}
