package application;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WikiTableScraper {
	
	private WebElement table = null;
	private WebDriver driver;
	
	private WikiTableScraper(String url, String browser) {
		
		switch(browser) {
			case "Firefox" : {
				//Firefox elinditasa			
				System.setProperty("webdriver.gecko.driver", "./geckodriver");
				this.driver = new FirefoxDriver();
				this.driver.manage().window().setSize(new Dimension(0,0));
				this.driver.manage().window().setPosition(new Point(0, 0));
				break;
			}
			case "PhantomJS" : {
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				                "./phantomjs");                  
				this.driver = new PhantomJSDriver(caps);				
				break;
			}
		}
			
		//Url meglatogatasa
		this.driver.get(url);		
			
		//tablazat levadaszasa
		try {
			this.table = this.driver.findElement(By.cssSelector("[class^='infobox']"));
		} catch(Exception e) {
			Error.set("Unable to find table on this page.");
			kill();
		}
					
	}

	public static WikiTableScraper create(String url, String browser) {
		WikiTableScraper scp = new WikiTableScraper(url, browser);
		return scp;
	}
	
	public void kill() {
		this.driver.quit();
	}
	
	public WebElement getTable() {
		return table;
	}
}
