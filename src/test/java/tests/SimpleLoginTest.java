package tests;



import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


public class SimpleLoginTest {

    ChromeDriver driver;

    @FindBy(id= "spree_user_email")
    WebElement user;

    @FindBy(id="spree_user_password")
    WebElement password;

    @FindBy(name = "commit")
    WebElement submit;

    @FindBy(id = "link-to-login")
    WebElement login_link;

    @BeforeTest
    public void setUp(){

        String currentUsersWorkingDir = System.getProperty("user.dir");
        System.out.println("Dir is " + currentUsersWorkingDir);
        System.setProperty("webdriver.chrome.driver",currentUsersWorkingDir+"/src/test/resources/chromedriver");
        driver = new ChromeDriver();
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



    @AfterTest
    public void tearDown(){

        driver.close();
        driver.quit();

    }


}
