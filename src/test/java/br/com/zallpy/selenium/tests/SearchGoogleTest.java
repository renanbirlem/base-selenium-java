package br.com.zallpy.selenium.tests;

import br.com.zallpy.selenium.core.BasicConfiguration;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
public class SearchGoogleTest extends BasicConfiguration {

  @Test
  public void searchGoogle() {
    driver.get("https://www.google.com/");
    driver.findElement(By.name("q")).click();
    driver.findElement(By.name("q")).sendKeys("selenium.dev");
    driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
    WebElement foo = new WebDriverWait(driver, 5)
        .until(driver -> driver.findElement(By.cssSelector("div:nth-child(2) > .rc:nth-child(1) .LC20lb")));
    driver.findElement(By.cssSelector("div:nth-child(2) > .rc:nth-child(1) .LC20lb")).click();
    assertThat(driver.findElement(By.cssSelector(".getting-started > h2")).getText(), is("Getting Started"));
  }
}
