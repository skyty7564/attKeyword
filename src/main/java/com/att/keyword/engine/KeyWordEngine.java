package com.att.keyword.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.att.keyword.base.Base;

public class KeyWordEngine {

	public WebDriver driver;
	public Properties prop;
	public WebElement element;
	public static Workbook book;
	public static Sheet sheet;

	public final String SCENARIO_SHEET_PATH = "D:\\ATT\\src\\main\\java\\com\\att\\keyword\\scenarios\\SecureCheckout.xlsx";
	public Base base;

	public void startExecution(String sheetName) {
		String locatorName = null;
		String locatorValue = null;
		FileInputStream file = null;

		try {
			file = new FileInputStream(SCENARIO_SHEET_PATH);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		try {
			book = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);
		System.out.println(book);
		System.out.println(sheet.getLastRowNum());
		int k = 0;
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			String locatorColValue = sheet.getRow(i + 1).getCell(k + 1).toString().trim();
			if (!locatorColValue.equalsIgnoreCase("NA")) {
				locatorName = locatorColValue.split("=",2)[0].trim(); // ID
				locatorValue = locatorColValue.split("=",2)[1].trim(); // username
			}

			String action = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
			String value = sheet.getRow(i + 1).getCell(k + 3).toString().trim();

			switch (action) {
			case "open browser":
				base = new Base();
				prop = base.init_properties();
				if (value.isEmpty() || value.equals("NA")) {
					driver = base.init_driver(prop.getProperty("browser"));
				} else {
					driver = base.init_driver(value);
					
				}
				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
				break;
			case "enter url":
				if (value.isEmpty() || value.equals("NA")) {
					driver.get(prop.getProperty("url"));
				} else {
					driver.get(value);
				}
				break;
			case "quit":
				driver.quit();
				break;
			default:
				break;

			}
			
			if (locatorName != null) {
				System.out.println(locatorValue + ":" + action);
				switch (locatorName) {
				case "id":
					element = driver.findElement(By.id(locatorValue));
					if (action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click")) {
						element.click();
					}
					locatorName = null;
					break;
				case "linkText":
					element = driver.findElement(By.linkText(locatorValue));
					element.click();
					locatorName = null;
					break;
				case "css":
				    element = driver.findElement(By.cssSelector(locatorValue));
				    element.click();
				    	
					locatorName=null;
					break;
	
				case "xpath":
					//*[@id="mui-p-87569-P-notCreated"]/div[1]/div/div/div/button[2]
				    element = driver.findElement(By.xpath(locatorValue));
					if(action.equalsIgnoreCase("validate"))
					{
						System.out.println(element.getText());
					}
					else if(action.equalsIgnoreCase("click"))
					{
						   element.click();
					}
				 
				    	
					locatorName=null;
					break;
				

				default:
					break;

				}
			}

		}

	}
}
