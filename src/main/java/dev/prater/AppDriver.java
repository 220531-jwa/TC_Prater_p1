package dev.prater;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AppDriver {
	public static void main(String[] args) throws InterruptedException {
	
	
	File chrome = new File("src/main/resources/geckodriver.exe");
	System.setProperty("webdriver.gecko.driver", chrome.getAbsolutePath());
	
	WebDriver driver = new FirefoxDriver();
	
	// load a web page in our automated browser
	driver.get("https://www.google.com");
	
	WebElement searchBar = driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input"));
	
	searchBar.sendKeys("mario" + Keys.ENTER);
	
	// if you want to visually verify yourself that it's working
	// you could use thread.sleep() but better to use a wait 
	// for DEMONSTRATION ONLY
	// Thread.sleep(3000);
	
	// but there is a way to take a screen shot - if you did want to store images to manually or visually verify anything
	
	// just like with our Scanner - we need to close resources when we're done with them.
	// WebDriver has two methods
	// .close() - will close the current browser window only
	// .quit() - will close the cureent browser window - along with any other windows that are open - and 'destroy' the driver instance
	driver.quit();
	}
}
