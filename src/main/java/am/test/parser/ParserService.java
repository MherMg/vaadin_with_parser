package am.test.parser;

import am.test.service.CompareService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by
 * Mher Petrosyan
 * Email mher13.02.94@gmail.ru
 */
@Service
public class ParserService {

    private final CompareService compareService;
    private WebDriver driver;

    @Autowired
    public ParserService(
            CompareService compareService
    ) {
        this.compareService = compareService;
    }


    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new ChromeDriver(chromeOptions);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://megagenerator.ru/namefio/");
        driver.manage().window().maximize();
    }

    public void saveNames() throws InterruptedException {
        driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[3]/p/input")).clear();
        driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[3]/p/input")).sendKeys("1000");
        driver.findElement(By.className("select-dropdown")).click();

        driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[3]/div[1]/div/ul/li[2]")).click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, 250);");


        WebElement webElement = driver.findElement(By.cssSelector("#generate"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", webElement);
        Thread.sleep(5000);
        String[] maleNames = getResult().split("\n");

        compareService.saveMaleName(maleNames);
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("#surname > label")).click();
        driver.findElement(By.cssSelector("#name > label")).click();

        generate();
        Thread.sleep(3000);
        String[] maleSurnames = getResult().split("\n");
        compareService.saveMaleSurname(maleSurnames);
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);

        driver.findElement(By.cssSelector("#checkarray > label")).click();

        generate();
        Thread.sleep(3000);
        String[] femaleSurnames = getResult().split("\n");
        compareService.saveFemaleSurname(femaleSurnames);
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);

        driver.findElement(By.cssSelector("#surname > label")).click();
        driver.findElement(By.cssSelector("#name > label")).click();

        generate();
        Thread.sleep(3000);
        String[] femaleNames = getResult().split("\n");
        compareService.saveFemaleName(femaleNames);
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        driver.close();
    }


    private void generate() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#generate")));
        driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[5]/input")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"result\"]")));
    }

    private String getResult() {
        return driver.findElement(By.xpath("//*[@id=\"result\"]")).getText();
    }

}
