
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class Regression_Test_With_Chrome {

    WebDriver driver;

    private WebElement waitFind(By xpath, int waitSeconds) throws Exception {
        for (int i = 0; i < waitSeconds; i++) {
            Thread.sleep(1000);
            if (!driver.findElements(xpath).isEmpty()) {
                Thread.sleep(1000);
                return driver.findElement(xpath);
            }
        }
        throw new Exception("Could not find element at path: " + xpath.toString());
    }

    @BeforeMethod
	public void startBrowser() {
        //Setting up
        System.setProperty("webdriver.chrome.driver","./chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    public void Creation_Test() throws Exception {
        driver.get("https://responsivefight.herokuapp.com/");
        waitFind(By.xpath("//input[@class='center']"),20).sendKeys("test");
        waitFind(By.xpath("//a[@id='warrior'][text()=\"Create warrior\"]"),20).click();

        WebElement res1 = waitFind(By.xpath("//a[@id=\"start\"][text()=\"Start your journey test\"]"),20);
        String expectedRes1 = "Start your journey test";
        Assert.assertEquals(res1.getText(), expectedRes1);
    }

    @Test
    public void Challenge_Test() throws Exception {
        driver.get("https://responsivefight.herokuapp.com/");
        waitFind(By.xpath("//input[@class='center']"),20).sendKeys("test");
        waitFind(By.xpath("//a[@id='warrior'][text()=\"Create warrior\"]"),20).click();
        waitFind(By.xpath("//a[@id=\"start\"][text()=\"Start your journey test\"]"),20).click();
        waitFind(By.xpath("//a[@id=\"news\"][text()=\"Are you game?\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"start\"][text()=\"Start\"]"),20).click();
        //1.answer
        waitFind(By.xpath("//a[@id=\"answer_1\"][text()=\"Continue reading\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"continue\"][text()=\"Continue...\"]"),20).click();
        //2.answer
        waitFind(By.xpath("//a[@id=\"answer_2\"][text()=\"Research into personal protective equipment\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"continue\"][text()=\"Continue...\"]"),20).click();
        //3.answer
        waitFind(By.xpath("//a[@id=\"answer_1\"][text()=\"Use your super hero sanitizer, keep a safe distance and advise they should keep 1.5 metres away from others.\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"continue\"][text()=\"Continue...\"]"),20).click();
        //4.answer
        waitFind(By.xpath("//a[@id=\"answer_1\"][text()=\"Use your superhero Social Distancing, notify your Manager and recommend she should be working from home.\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"continue\"][text()=\"Continue...\"]"),20).click();
        //5.answer
        waitFind(By.xpath("//a[@id=\"answer_2\"][text()=\"Use your superhero Social Distance, advise your Manager of the risk and that his partner should be seeking medical advice.\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"continue\"][text()=\"Continue...\"]"),20).click();
        //6
        waitFind(By.xpath("//a[@id=\"answer_2\"][text()=\"Move to another seat immediately and report it to the driver!\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"continue\"][text()=\"Continue...\"]"),20).click();
        //7
        waitFind(By.xpath("//a[@id=\"answer_1\"][text()=\"Cover any sneezes or coughs with your superhero inner elbow or with a tissue that is then discarded.\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"continue\"][text()=\"Continue...\"]"),20).click();
        //8
        waitFind(By.xpath("//a[@id=\"answer_2\"][text()=\"Do not ignore the sign of possible contamination and report it to Management.\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"continue\"][text()=\"Continue...\"]"),20).click();
        //9
        waitFind(By.xpath("//a[@id=\"answer_2\"][text()=\"Use reasonable logic, keep distance from the waiter and report it to management!\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"continue\"][text()=\"Continue...\"]"),20).click();
        //10
        waitFind(By.xpath("//a[@id=\"answer_1\"][text()=\"Yes I know\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"continue\"][text()=\"Continue...\"]"),20).click();


        //Testing Keyboard
        /*
        new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
        new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
        new Actions(driver).sendKeys(Keys.ENTER).perform();
        */
        //Verifying
        WebElement res2 = waitFind(By.xpath("//*[text()=\"test\"]"),20);
        String expectedRes2 = "test";
        Assert.assertEquals(res2.getText(), expectedRes2);
    }

    @Test
    public void Bus_Challenge_Negative_Test() throws Exception {
        driver.get("https://responsivefight.herokuapp.com/");
        waitFind(By.xpath("//input[@class='center']"),20).sendKeys("test");
        waitFind(By.xpath("//a[@id='warrior'][text()=\"Create warrior\"]"),20).click();
        waitFind(By.xpath("//a[@id=\"start\"][text()=\"Start your journey test\"]"),20).click();

        waitFind(By.xpath("//a[@id='bus'][text()=\"Take the bus\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"bus_timer_start\"][text()=\"Start\"]"), 20).click();
        waitFind(By.xpath("//a[@id=\"bus_answer_2\"][text()=\"Please consider travelling during peak times, when more help is available to you\"]"), 20).click();
        WebElement res1 = waitFind(By.xpath("//button[@class=\"btn btn-secondary\"][text()=\"Try again\"]"),20);
        String expected = "Try again";
        Assert.assertEquals(res1.getText(), expected);
    }

    @Test
    public void Public_Place_Challenge_Negative_Test() throws Exception {
        driver.get("https://responsivefight.herokuapp.com/");
        waitFind(By.xpath("//input[@class='center']"),20).sendKeys("test");
        waitFind(By.xpath("//a[@id='warrior'][text()=\"Create warrior\"]"),20).click();
        waitFind(By.xpath("//a[@id=\"start\"][text()=\"Start your journey test\"]"),20).click();
        waitFind(By.xpath("//a[@id='restaurant'][text()=\"Go to a public place\"]"),20).click();
        waitFind(By.xpath("//button[@id=\"restaurant_timer_start\"][text()=\"Start\"]"), 20).click();
        waitFind(By.xpath("//a[@id=\"restaurant_answer_2\"][text()=\"move away immediately call the police!\"]"), 20).click();
        WebElement res1 = waitFind(By.xpath("//button[@class=\"btn btn-secondary\"][text()=\"Try again\"]"),20);
        String expected = "Try again";
        Assert.assertEquals(res1.getText(), expected);
    }

    @Test
    public void Office_Place_Challenge_Negative_Test() throws Exception {
        driver.get("https://responsivefight.herokuapp.com/");
        waitFind(By.xpath("//input[@class='center']"),20).sendKeys("test");
        waitFind(By.xpath("//a[@id='warrior'][text()=\"Create warrior\"]"),20).click();
        waitFind(By.xpath("//a[@id=\"start\"][text()=\"Start your journey test\"]"),20).click();

        waitFind(By.xpath("//a[@id='office'][text()=\"Go to the office\"]"),20).click();
        waitFind(By.xpath("//button[@class=\"btn btn-primary\"][text()=\"Start\"]"), 20).click();
        waitFind(By.xpath("//a[@id=\"office_answer_2\"][text()=\"Keep it to yourself, do not draw attention, and pretend you did not notice!\"]"), 20).click();
        WebElement res1 = waitFind(By.xpath("//button[@class=\"btn btn-secondary\"][text()=\"Try again\"]"),20);
        String expected = "Try again";
        Assert.assertEquals(res1.getText(), expected);
    }

    @AfterMethod
	public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}

