package br.com.zallpy.selenium.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BasicConfiguration {

  protected WebDriver driver;
  protected Map<String, Object> vars;
  protected JavascriptExecutor js;

  @BeforeClass
  public static void init() throws IOException {
    WebDriverUtil.check();
  }

  @Before
  public void setUp() {
    driver = WebDriverUtil.driver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<>();
  }

  @After
  public void tearDown() {
    driver.quit();
  }
}
