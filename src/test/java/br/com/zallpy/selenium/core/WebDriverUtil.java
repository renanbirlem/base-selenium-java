package br.com.zallpy.selenium.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class WebDriverUtil {

  private static final Logger LOGGER = Logger.getGlobal();
  private static final String S = File.separator; /* S stands for separator */

  private static Properties properties;

  private static String driverBaseUrl;
  private static String driverVersion;
  private static String driverName;
  private static String seleniumFolder;

  public static void check() throws IOException {
    loadProperties();

    final String tmp = System.getProperty("java.io.tmpdir");
    final String path = tmp + S + seleniumFolder;

    final File bin = new File(path + S + driverName);

    if (!bin.exists()) {
      LOGGER.info("WebDriver not found");
      final File folder = new File(path);

      if(!folder.exists()) {
        LOGGER.info("Creating folder " + folder.getAbsolutePath());
        folder.mkdirs();
      }

      final File destination = new File(path + S + driverName + getExtension());
      final URL url = new URL(formatUrl());

      LOGGER.info("Downloading WebDriver");
      FileUtils.copyURLToFile(url, destination);
      LOGGER.info("Download done");

      if (destination.exists()) {
        extract(folder, destination);
      }

      Files.deleteIfExists(destination.toPath());
      LOGGER.info("Zip file removed");
    }

    LOGGER.info("Adding WebDriver to path");

    if (isChrome()) {
      System.setProperty("webdriver.chrome.driver", path + S + driverName);

    } else {
      System.setProperty("webdriver.gecko.driver", path + S + driverName);
    }
  }

  private static boolean isChrome() {
    return driverName.toLowerCase().contains("chrome");
  }

  private static void extract(File folder, File compressed) throws IOException {
    LOGGER.info("Extracting file");
    if (isZip()) {
      final ZipFile zip = new ZipFile(compressed);
      zip.extractAll(folder.getAbsolutePath());
    } else {
      Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");
      archiver.extract(compressed, folder);
    }
    LOGGER.info("Extraction done");
  }

  private static boolean isZip() {
    return driverBaseUrl.endsWith("zip");
  }

  private static String getExtension() {
    if (isZip()) {
      return ".zip";

    } else {
      return ".tar.gz";
    }
  }

  private static String formatUrl() {
    final String suffix = OSValidator.getSuffix();

    return driverBaseUrl
        .replace("${selenium.web-driver.version}", driverVersion)
        .replace("$os",suffix);
  }

  private static void loadProperties() throws IOException {
    if (Objects.isNull(properties)) {
      InputStream inputStream = WebDriverUtil.class
          .getClassLoader()
          .getResourceAsStream("application.properties");

      properties = new Properties();
      properties.load(inputStream);
    }

    driverName = get(properties, "selenium.web-driver.name");
    driverBaseUrl = get(properties, "selenium.web-driver.base-url");
    driverVersion = get(properties, "selenium.web-driver.version");
    seleniumFolder = get(properties,"selenium.folder");
  }

  private static String get(Properties props, String key) {
    final String value = props.getProperty(key);

    if (value.startsWith("${")) {
      final String[] parts = value.split(":", 2);
      final String defaultValue = parts[1].substring(0, parts[1].length() - 1);
      final String replaceValue = System.getenv(parts[0].replaceFirst("\\$\\{", ""));

      if(Objects.nonNull(replaceValue)) {
        return replaceValue;
      }
      return defaultValue;
      
    } else {
      return value;
    }
  }

  public static WebDriver driver() {
    if (isChrome()) {
      return new ChromeDriver();

    } else {
      return new FirefoxDriver();
    }
  }
}
