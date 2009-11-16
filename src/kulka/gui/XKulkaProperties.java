package kulka.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("serial")
public class XKulkaProperties extends Properties {

	private static String rcFileComment = "Properties for xkulka";
	private static String defaultRcFile = ".xkulkarc";

	@SuppressWarnings("unused")
	private static String[][] defaultProperties = {
			{ "xkulka.restarting.warn", "0" },
			{ "xkulka.properties.file", ".xkulka" } };

	@SuppressWarnings("unused")
	private String rcFileName;
	private File rcFile;

	public XKulkaProperties(String rcFileName) {
		rcFile = new File(rcFileName);
	}

	public XKulkaProperties() {
		String userHome = System.getProperty("user.home");
		String separator = System.getProperty("file.separator");
		rcFile = new File(userHome + separator + defaultRcFile);
	}

	void storeDefaultSettings() {

		setProperty("ala", "kot");

		try {
			store(new FileOutputStream(rcFile), rcFileComment);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
