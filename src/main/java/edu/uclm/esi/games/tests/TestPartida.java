package edu.uclm.esi.games.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestPartida {
  private WebDriver driverPepe, driverAna;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  
  public TestPartida() throws Exception {
	System.setProperty("webdriver.chrome.driver", "/Users/macario.polo/Desktop/chromedriver");
	System.setProperty("webdriver.gecko.driver", "/Users/macario.polo/Desktop/geckodriver");
  }

  @Before
  public void setUp() throws Exception {
    driverPepe = new ChromeDriver();
    driverAna = new ChromeDriver();
    driverPepe.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driverAna.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    
    driverPepe.manage().window().setPosition(new Point(0, 0));
    driverAna.manage().window().setPosition(new Point(driverPepe.manage().window().getSize().width+1, 0));
  }

  @Test
  public void testGames() throws Exception {
	  login(driverPepe, "pepe", "pepe");
	  login(driverAna, "ana", "ana");
	  
	  driverPepe.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Register'])[1]/following::select[1]")).click();
	  new Select(driverPepe.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Register'])[1]/following::select[1]"))).selectByVisibleText("tictactoe");
	  
	  driverAna.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Register'])[1]/following::select[1]")).click();
	  new Select(driverAna.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Register'])[1]/following::select[1]"))).selectByVisibleText("tictactoe");
	  
	  driverPepe.findElement(By.id("buttonJoinGame")).click();
	  driverAna.findElement(By.id("buttonJoinGame")).click();
	  
	  move(driverPepe, "11");
	  move(driverAna, "00");
	  move(driverPepe, "01");
	  move(driverAna, "10");
	  move(driverPepe, "21");
	  
	  Thread.sleep(500);
	  String textoQueTieneQueEstar="Ha ganado pepe";
	  assertTrue(driverPepe.getPageSource().contains(textoQueTieneQueEstar));
	  assertTrue(driverAna.getPageSource().contains(textoQueTieneQueEstar));
  }

  private void move(WebDriver driver, String id) {
	WebElement casilla=driver.findElement(By.id(id));
	casilla.click();
	try {
		Thread.sleep(200);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
  }

private void login(WebDriver driver, String userName, String pwd) throws Exception {
	    driver.get("http://localhost:8600/gamesMongoDB/index.html");
	    driver.findElement(By.linkText("Login")).click();
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[2]/following::input[1]")).click();
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[2]/following::input[1]")).clear();
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[2]/following::input[1]")).sendKeys(userName);
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[2]/following::input[2]")).clear();
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[2]/following::input[2]")).sendKeys(pwd);
	    driver.findElement(By.id("btnLogin")).click();
  }

  @After
  public void tearDown() throws Exception {
    
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
    
    driverPepe.quit();
    driverAna.quit();
  }

  private boolean isElementPresent(By by) {
    try {
      driverPepe.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driverPepe.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driverPepe.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
