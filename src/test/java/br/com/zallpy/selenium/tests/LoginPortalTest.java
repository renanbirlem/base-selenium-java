package br.com.zallpy.selenium.tests;

import br.com.zallpy.selenium.core.BasicConfiguration;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPortalTest extends BasicConfiguration {

  @Test
  public void loginPortal() {
    driver.get("http://portaloficinas.qa.zallpylabs.com/pt");
    driver.manage().window().setSize(new Dimension(1079, 697));
    driver.findElement(By.cssSelector(".MuiGrid-root:nth-child(3) > .TLogLabel-sc-3et8lx-0")).click();
    driver.findElement(By.name("email")).click();
    driver.findElement(By.name("email")).sendKeys("ivanzallpy@gmail.com");
    driver.findElement(By.name("password")).sendKeys("sistemas");
    driver.findElement(By.cssSelector(".MuiButton-label")).click();
    new WebDriverWait(driver, 5)
        .until(driver -> driver.findElement(By.cssSelector("#menu-LOGOUT .MuiSvgIcon-root")));
    driver.findElement(By.cssSelector("#menu-LOGOUT .MuiSvgIcon-root")).click();
    js.executeScript("window.scrollTo(0,0)");
  }
}
