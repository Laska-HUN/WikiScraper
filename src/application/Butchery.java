package application;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class Butchery {
	
	public static List<KeyValue> CutToList(WebElement table) {
		List<KeyValue> cell_list = new ArrayList<KeyValue>(); //kulcs-ertek parokat visszaado lista
		//List<String> str = new ArrayList<String>();

		List<WebElement> rows = table.findElements(By.cssSelector("tr>*")); //tablazat sorai
		List<WebElement> images = table.findElements(By.cssSelector("img"));
		List<WebElement> caption = table.findElements(By.cssSelector("caption"));
		
		if(caption.size()!=0) cell_list.add(new KeyValue("caption", caption.get(0).getText()+"\n"));
		
		/*for(WebElement row : rows) {
			cell_list.add(new KeyValue(row.getTagName(), row.getText())); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MOST IGY MINDEN FASZA
		}*/
		
		for(int i=0; i<rows.size()-1; i++) {
			WebElement row = rows.get(i);
			WebElement next_row = rows.get(i+1);
			
			if(row.getTagName().equals("th") && next_row.getTagName().equals("td")) {
				/*String td_string ="";
				System.out.println("kezd");
				while(rows.get(i+1).getTagName().equals("td")){
					td_string = td_string + rows.get(i+1).getText() + " ";
					System.out.println("ciklus");
					i++;
				}
				System.out.println(td_string);*/
				cell_list.add(new KeyValue(row.getText(), next_row.getText()));
			}
			else if(row.getTagName().equals("th") && next_row.getTagName().equals("th")) {
				if(i==0)cell_list.add(new KeyValue("caption", row.getText()+"\n"));
				else cell_list.add(new KeyValue("\n", row.getText()+"\n"));
			}
		}
		
		cell_list.add(new KeyValue(" ", " "));
		
		for(WebElement img : images) {
			cell_list.add(new KeyValue("img"+images.indexOf(img), img.getAttribute("src")));
		}
		
		return cell_list;
	}
}

//https://en.wikipedia.org/wiki/Codecademy