package authoring.canvas;

import authoring.Workspace;
import authoring.components.HTMLDisplay;
import authoring.views.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author Elliott Bolzan
 *
 */
public class HelpBar extends View {
	
	private Workspace workspace;
	
	/**
	 * @param title
	 */
	public HelpBar(Workspace workspace) {
		super("");
		this.workspace = workspace;
		setup();
	}
	
	private void setup() {
		ToolBar bar = new ToolBar();
		Label instructions = new Label(workspace.getResources().getString("Instructions"));
		instructions.setPadding(new Insets(4));
		HBox box = new HBox(instructions);
		HBox.setHgrow(box, Priority.ALWAYS);
		box.setAlignment(Pos.CENTER);
		bar.getItems().addAll(box);
		setCenter(instructions);
		setOnMouseClicked(e -> mouseClicked());
	}
	
	private void mouseClicked() {
    	HTMLDisplay display = new HTMLDisplay(workspace.getResources().getString("HelpPath"), workspace.getResources().getString("HelpTitle"));
    	display.show();
	}

}
