package br.com.zallpy.selenium.tests;

import br.com.zallpy.selenium.core.BasicConfiguration;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SearchWikipediaTest extends BasicConfiguration {


  @Test
  public void searchWikipedia() {
    driver.get("https://www.wikipedia.org//");
    driver.findElement(By.cssSelector("#js-link-box-pt > strong")).click();
    assertThat(driver.findElement(By.cssSelector("table:nth-child(2) td:nth-child(1) > span")).getText(), is("Artigo em destaque"));
  }
}
