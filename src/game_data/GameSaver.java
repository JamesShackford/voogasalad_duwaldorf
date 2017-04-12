package game_data;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import engine.Entity;
import engine.game.Level;

// Make references to paths relative. 

public class GameSaver {
	
	private Game game;
	private GameXMLFactory gameXMLFactory;

	public void saveGame(Game game, String filepath) {
		this.game = game;
		gameXMLFactory= new GameXMLFactory();
		gameXMLFactory.setName(game.getName());
		createRoot(filepath);
		saveLevels(game.getLevels(), filepath + "/" + game.getName());
		saveDefaults(game.getDefaults(), filepath + "/" + game.getName());
		saveSong(filepath+game.getSongPath());
		saveDocument(filepath);
	}
	
	private void createRoot(String filePath) {
		File folder = new File(filePath + game.getName());
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}
	
	private void saveSong(String songPath){
		//System.out.println(songPath);
		//LevelSaver ls = new LevelSaver(null);
		//String xmlsong = ls.saveSong(songPath);
		//Element songelement = gameXMLFactory.stringToElement(xmlSong);
		gameXMLFactory.addSong(songPath);
		
		//gameXMLFactory.addSong(gameXMLFactory.stringToElement(songPath));
	}
	
	
	public void saveEntityImage(Entity entity, String filePath) {
		try {
			String sourcePathString = filePath;
			Path sourcePath = Paths.get(sourcePathString);
			String targetPathString = filePath + "/images/" + entity.getName() + "image.png";
			File entityImageFile = new File(targetPathString);
			entityImageFile.getParentFile().mkdirs();
			entityImageFile.createNewFile();
			Path targetPath = Paths.get(targetPathString);
			Files.copy(sourcePath, targetPath, REPLACE_EXISTING);
		} catch (Exception i) {
			i.printStackTrace();
		}
	}
	
	
	
	private void saveDefaults(List<Entity> defaults, String filePath){
		
		
			List<Entity> entities = defaults;
			List<Element> entityNodes = new ArrayList<Element>();
		
			for (int j = 0; j < entities.size(); j++) {
				Entity currEntity = entities.get(j);
				
				Element entityNode = getEntityNode(currEntity);
				entityNodes.add(entityNode);
			}
			LevelSaver ls = new LevelSaver(entityNodes);
			String xmlLevel = ls.saveLevel();
			//System.out.println(xmlLevel);
			Element levelElement = gameXMLFactory.stringToElement(xmlLevel);
			gameXMLFactory.addDefaultEntity(levelElement);
			//System.out.println(xmlLevel);
			
			
		
		
		
	}
	
	private void saveLevels(List<Level> levels, String filePath) {
		for (int i = 0; i < levels.size(); i++) {

			List<Entity> entities = new ArrayList<Entity>(levels.get(i).getEntities());
			List<String> entityfilepaths = new ArrayList<String>();

			for (int j = 0; j < entities.size(); j++) {
				Entity currentity = entities.get(j);
				String entityfilepath = saveEntity(currentity, filepath);
				entityfilepaths.add(entityfilepath);
			}

			LevelSaver ls = new LevelSaver(entityfilepaths, filepath, i);
			ls.saveLevel();
		}
	}

	public String saveEntity(Entity entity, String dataFolderPath) {
		File entityfolder = new File(dataFolderPath + File.separator + "entities");
		if (!entityfolder.exists()) {
			entityfolder.mkdirs();
		}
		String entityfilepath = "";
		try {
			String tempImagePath = entity.getImagePath();
			
			saveEntityImage(entity, dataFolderPath);
			entityfilepath = dataFolderPath + File.separator + "entities" + File.separator + entity.getName() + ".xml";
			File entityfile = new File(entityfilepath);

			XStream xStream = new XStream(new DomDriver());
			xStream.registerConverter(new EntityConverter());
			String xmlstring = xStream.toXML(entity);
			FileWriter fw = new java.io.FileWriter(entityfile);

			fw.write(xmlstring);
			fw.close();
			
			entity.setImagePath(tempImagePath);

		} catch (IOException i) {
			i.printStackTrace();
		}

		return File.separator + "entities" + File.separator + entity.getName() + ".xml";
	}

	public void saveEntityImage(Entity entity, String dataFolderPath) {
		try {

			String sourcePath = new File(new URI(entity.getImagePath())).getAbsolutePath();
			Path sourcepath = Paths.get(sourcePath);

			String targetpathstring = dataFolderPath + File.separator + "images" + File.separator + entity.getName() + "Image.png";
			entity.setImagePath("images" + File.separator + entity.getName() + "Image.png");
			File entityimagefile = new File(targetpathstring);

			entityimagefile.getParentFile().mkdirs();
			entityimagefile.createNewFile();

			Path targetpath = Paths.get(targetpathstring);
			Files.copy(sourcepath, targetpath, REPLACE_EXISTING);

		} catch (Exception i) {
			i.printStackTrace();
		}
	}
}
