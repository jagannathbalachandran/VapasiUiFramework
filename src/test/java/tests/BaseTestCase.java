package tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseTestCase {

    ChromeDriver driver;


    @BeforeTest
    public void setUp(){

        String currentUsersWorkingDir = System.getProperty("user.dir");
        System.out.println("Dir is " + currentUsersWorkingDir);
        System.setProperty("webdriver.chrome.driver",currentUsersWorkingDir+"/src/test/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);

    }


    @AfterMethod
    public void tearDown(ITestResult result){

        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result);
        }

    }

    private void takeScreenshot(ITestResult result) {

        System.out.println("Taking screenshot");
        String currentUsersWorkingDir = System.getProperty("user.dir");
        String resultsDir = currentUsersWorkingDir+ "/src/test/snapshot";
        File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            // now copy the  screenshot to desired location using copyFile //method
            FileUtils.copyFile(src, new File(resultsDir + "/" + result.getName() + ".png"));
        }

        catch (IOException e)
        {
            System.out.println(e.getMessage());

        }
    }

    @AfterTest
    public void tearDown(){

        driver.close();
        driver.quit();

    }


}
