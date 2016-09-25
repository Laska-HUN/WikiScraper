package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
	
	private List<KeyValue> list;
	private boolean toggled = false;
	
	ObservableList<String> browser_list = FXCollections.observableArrayList("PhantomJS", "Firefox");
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField url;

    @FXML
    private Button get;

    @FXML
    private TextArea result;

    @FXML
    private Button upload;

    @FXML
    private ProgressIndicator wait;
    
    @FXML
    private ChoiceBox<String> choice;

    @FXML
    void initialize() {

    	choice.setItems(browser_list);
    	choice.setValue(browser_list.get(0));
    	
        upload.setDisable(true);
            
        get.setOnMouseClicked((event) -> {
       	
        	if(url.getText().contains("wikipedia.org/wiki")) {
        		
        		result.setText("");
        		toggleUI();
        		
	        	Task<Void> table_mining = new Task<Void>() {
	        	    @Override public Void call() {
	        	    	WikiTableScraper scraper = WikiTableScraper.create(url.getText(), choice.getValue());	            		
	            		list = new ArrayList<KeyValue>(Butchery.CutToList(scraper.getTable()));
	            		scraper.kill();	           			            		
	            		return null;
	        	    }
	        	};
	        	
	        	table_mining.setOnSucceeded(e -> {
		        	for(int i=0; i<list.size(); i++) {
	        			result.appendText(list.get(i).getKey()+ "  " + list.get(i).getValue() + " \n");
	        		}
		        	toggleUI();
	        	});
	        		        	
	        	table_mining.setOnFailed(e -> {
	        		result.setText(Error.get());
	        		toggleUI();
	        		upload.setDisable(true);
	        	});
	        	
	        	Thread thread = new Thread(table_mining);
	            thread.setDaemon(true);
	            thread.start();
        	}
        	else {
        		Error.set("The url must point to a Wikipedia page!");
        		result.setText(Error.get());
        		upload.setDisable(true);
        	}
        });
    }
    
	private void toggleUI(){
    	if(toggled==false) {
    		wait.setVisible(true);
    		get.setDisable(true);
        	upload.setDisable(true);
    	}
    	else {
    		wait.setVisible(false);
    		get.setDisable(false);
        	upload.setDisable(false);
    	}
    	toggled = !toggled;
    }
}
