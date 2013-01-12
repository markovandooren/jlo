package jlo.model.language;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import jnome.workspace.JavaProjectConfig;
import chameleon.workspace.ConfigException;
import chameleon.workspace.DirectoryLoader;
import chameleon.workspace.FileInputSourceFactory;
import chameleon.workspace.View;
import chameleon.workspace.ZipLoader;
import chameleon.workspace.BootstrapProjectConfig.BaseLibraryConfiguration;

public class JLoProjectConfig extends JavaProjectConfig {

	public JLoProjectConfig(View view, FileInputSourceFactory inputSourceFactory, String projectName, File root, String baseJarPath, BaseLibraryConfiguration baseLibraryConfiguration)
			throws ConfigException {
		super(view, inputSourceFactory, projectName, root, baseJarPath,baseLibraryConfiguration);
		// The base loader for Java already creates the primitive types
		// and load the Java base library.
		// Therefore, we only have to add a loader for the base JLo library file.
		
		// 1) For a normal jar release, the base library is always stored inside jlo.jar
		// 2) For the Eclipse plugin, the base library is included inside the plugin jar
		//    and the jlo-base.jar is included as well.
		// 3) When the Eclipse plugin is run as a nested Eclipse during development, 
		//    we use a Directory loader to read the sources directly.
		if(baseLibraryConfiguration.mustLoad("JLo")) {
			URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
			try {
				File file = new File(url.toURI());
				String path = file.getAbsolutePath();
				if(path.endsWith(".jar")) {
					view.addBinary(new ZipLoader(path, JLO_FILE_EXTENSION));
				} else {
					file = new File(file,JLO_BASE_LIBRARY_DIRECTORY);
					view.addBinary(new DirectoryLoader(path, fileInputSourceFactory()));
				}
			} catch (Exception e) {
				throw new ConfigException(e);
			}
		}
	}
	
	@Override
	protected List<String> sourceExtensions() {
		return Collections.singletonList(JLO_FILE_EXTENSION);
	}
	
	private static final String JLO_BASE_LIBRARY_DIRECTORY = "base_library";
	private static final String JLO_FILE_EXTENSION = ".jlo";
}
