package be.kuleuven.cs.distrinet.jlo.model.language;

import java.io.File;
import java.net.URL;
import java.util.jar.JarFile;

import sun.net.www.protocol.jar.JarURLConnection;
import be.kuleuven.cs.distrinet.chameleon.core.language.Language;
import be.kuleuven.cs.distrinet.chameleon.workspace.BaseLibraryConfiguration;
import be.kuleuven.cs.distrinet.chameleon.workspace.ConfigException;
import be.kuleuven.cs.distrinet.chameleon.workspace.DirectoryLoader;
import be.kuleuven.cs.distrinet.chameleon.workspace.FileInputSourceFactory;
import be.kuleuven.cs.distrinet.chameleon.workspace.ProjectConfigurator;
import be.kuleuven.cs.distrinet.chameleon.workspace.View;
import be.kuleuven.cs.distrinet.chameleon.workspace.Workspace;
import be.kuleuven.cs.distrinet.chameleon.workspace.ZipLoader;
import be.kuleuven.cs.distrinet.jnome.workspace.JavaProjectConfig;
import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

public class JLoProjectConfig extends JavaProjectConfig {

	public JLoProjectConfig(String projectName, File root, View view, Workspace workspace, FileInputSourceFactory inputSourceFactory)
			throws ConfigException {
		super(projectName, root, view, workspace, inputSourceFactory);
	}
	
	public static File jloBase() {
		URL url = JLoProjectConfig.class.getProtectionDomain().getCodeSource().getLocation();
		try {
			File file = new File(url.toURI());
			String path = file.getAbsolutePath();
			if(path.endsWith(".jar")) {
				return new File(path);
			} else {
				return new File(file,JLO_BASE_LIBRARY_DIRECTORY);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static class BaseDirectoryLoader extends DirectoryLoader {

		public BaseDirectoryLoader(String root, FileInputSourceFactory factory, SafePredicate<? super String> filter) {
			super(root, filter, true, factory);
		} 
		
	}
	
	protected Language jlo() {
		return language("jlo");
	}
		
}
