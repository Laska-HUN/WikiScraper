package application;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class Butchery {
	
	public static String getData(WebElement table) {
		String data = "\"data\":[\n"; //kulcs-ertek parokat visszaado lista
		//List<String> str = new ArrayList<String>();

		List<WebElement> keys = table.findElements(By.cssSelector("td:nth-last-child(2), th:nth-last-child(2)")); //tablazat sorai
		List<WebElement> values = table.findElements(By.cssSelector("td:nth-child(2), th:nth-child(2)")); //tablazat sorai
		
		for(int i = 0; i< keys.size(); i++) {
			data = data + "{\"" + keys.get(i).getText() + "\":\"" + values.get(i).getText();
			data = (i == keys.size()-1) ? data+ "\"}\n" : data+ "\"},\n"; 
		}
		data = data + "]";
		
		return data;
	}
	
	public static String getImages(WebElement table) {
		String data = "\"images\":[\n";
		List<WebElement> images = table.findElements(By.cssSelector("img"));
		for(int i = 0; i< images.size(); i++) {
			data = data + "{\"img" + i + "\":\"" + images.get(i).getAttribute("src");
			data = (i == images.size()-1) ? data+ "\"}\n" : data+ "\"},\n"; 
		}
		data = data + "]";
		
		return data;
	}
}

//https://en.wikipedia.org/wiki/Codecademy