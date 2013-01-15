package jlo.model.language;

import java.io.File;
import java.net.URL;

import jnome.workspace.JavaProjectConfig;

import org.rejuse.predicate.SafePredicate;

import chameleon.core.language.Language;
import chameleon.workspace.BootstrapProjectConfig.BaseLibraryConfiguration;
import chameleon.workspace.ConfigException;
import chameleon.workspace.DirectoryLoader;
import chameleon.workspace.FileInputSourceFactory;
import chameleon.workspace.ProjectConfigurator;
import chameleon.workspace.View;
import chameleon.workspace.Workspace;
import chameleon.workspace.ZipLoader;

public class JLoProjectConfig extends JavaProjectConfig {

	public JLoProjectConfig(String projectName, File root, View view, Workspace workspace, FileInputSourceFactory inputSourceFactory, String baseJarPath, BaseLibraryConfiguration baseLibraryConfiguration)
			throws ConfigException {
		super(projectName, root, view, workspace, inputSourceFactory, baseJarPath,baseLibraryConfiguration);
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
				SafePredicate<? super String> sourceFileFilter = jlo().plugin(ProjectConfigurator.class).sourceFileFilter();
				if(path.endsWith(".jar")) {
					view.addBinary(new ZipLoader(path, sourceFileFilter));
				} else {
					file = new File(file,JLO_BASE_LIBRARY_DIRECTORY);
					view.addBinary(new DirectoryLoader(path, fileInputSourceFactory(), sourceFileFilter));
				}
			} catch (Exception e) {
				throw new ConfigException(e);
			}
		}
	}
	
	protected Language jlo() {
		return language("jlo");
	}
		
	private static final String JLO_BASE_LIBRARY_DIRECTORY = "base_library";
}
