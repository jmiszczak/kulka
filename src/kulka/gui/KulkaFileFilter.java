package kulka.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class KulkaFileFilter extends FileFilter {

	private static String KULKA_EXT = "kulka";

	private static String KULKA_DESC = "Kulka scripts (*.kulka)";

	@Override
	public boolean accept(File f) {
		if (f != null) {
			if (f.isDirectory()) {
				return true;
			}
			String extension = getExtension(f);
			if (extension != null && extension.equalsIgnoreCase(KULKA_EXT)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return KULKA_DESC;
	}

	/**
	 * Helper function for obtaining extension of the file
	 * 
	 * @param f
	 *            File
	 * @return extension of the file, i.e. part of the name after last dot
	 */
	public String getExtension(File f) {
		if (f != null) {
			String filename = f.getName();
			int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				return filename.substring(i + 1).toLowerCase();
			}
		}
		return null;
	}

}
