package dev.prater;

//import java.io.File;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;

import dev.prater.utils.Amentum;

public class AppDriver {
	public static void main(String[] args)
	{
		Amentum amn = new Amentum();
		amn.serverRequestHandler();
	}
}
