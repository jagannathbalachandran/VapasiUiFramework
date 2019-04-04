package tests;



import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SimpleLoginTestWithSnapshotOfTestFailure {

    ChromeDriver driver;

    @FindBy(id= "spree_user_emal")
    WebElement user;

    @FindBy(id="spree_user_password")
    WebElement password;

    @FindBy(name = "commit")
    WebElement submit;

    @FindBy(id = "link-to-login")
    WebElement login_link;

    String currentUsersWorkingDir = System.getProperty("user.dir");
    private String resultsDir;

    @BeforeTest
    public void setUp(ITestContext context){


        System.out.println("Dir is " + currentUsersWorkingDir);
        System.setProperty("webdriver.chrome.driver",currentUsersWorkingDir+"/src/test/resources/chromedriver");
        driver = new ChromeDriver();
        resultsDir = currentUsersWorkingDir+ "/src/test/snapshot";
        context.setAttribute("driver", driver);
        context.setAttribute("resultsDir", resultsDir);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);

    }

    @Test
    public void testLogin(){
        driver.navigate().to("https://spree-vapasi.herokuapp.com");
        login_link.click();
        user.sendKeys("spree@example.com");
        password.sendKeys("spree123");
        submit.click();
    }


    @AfterMethod
    public void tearDown(ITestResult result){

        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result);
        }

        driver.close();
        driver.quit();

    }


    private void takeScreenshot(ITestResult result) {

        System.out.println("Taking screenshot");
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

    public WebDriver getDriver()
    {
        return driver;
    }

}
