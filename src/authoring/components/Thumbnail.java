package authoring.components;

import authoring.views.View;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Elliott Bolzan
 *
 */
public class Thumbnail extends View {

	private String imagePath;
	private SimpleObjectProperty<Image> image;
	private ImageView imageView = new ImageView();

	public Thumbnail(SimpleStringProperty path, int width, int height) {
		super("Thumbmnail");
		image = new SimpleObjectProperty<Image>();
		path.addListener(e -> setImage(path.get()));
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		imageView.setPreserveRatio(true);
		imageView.imageProperty().bind(image);
		setImage(path.get());
		setCenter(imageView);
		setMinSize(0, 0);
	}
	
	public void setImage(String path) {
		this.imagePath = path;
		image.set(new Image(path));
	}
	
	public String getImagePath() {
		return imagePath;
	}

}