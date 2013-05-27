package be.kuleuven.cs.distrinet.jlo.model.language;

import java.io.File;
import java.net.URL;
import java.util.jar.JarFile;

import sun.net.www.protocol.jar.JarURLConnection;
import be.kuleuven.cs.distrinet.chameleon.core.language.Language;
import be.kuleuven.cs.distrinet.chameleon.workspace.BaseLibraryConfiguration;
import be.kuleuven.cs.distrinet.chameleon.workspace.BaseLibraryConfigurator;
import be.kuleuven.cs.distrinet.chameleon.workspace.ConfigException;
import be.kuleuven.cs.distrinet.chameleon.workspace.ExtensionPredicate;
import be.kuleuven.cs.distrinet.chameleon.workspace.ProjectConfigurator;
import be.kuleuven.cs.distrinet.chameleon.workspace.ProjectException;
import be.kuleuven.cs.distrinet.chameleon.workspace.View;
import be.kuleuven.cs.distrinet.chameleon.workspace.Workspace;
import be.kuleuven.cs.distrinet.chameleon.workspace.ZipLoader;
import be.kuleuven.cs.distrinet.jlo.model.language.JLoProjectConfig.BaseDirectoryLoader;
import be.kuleuven.cs.distrinet.jnome.core.language.Java;
import be.kuleuven.cs.distrinet.jnome.input.BaseJavaProjectLoader;
import be.kuleuven.cs.distrinet.jnome.input.LazyJavaFileInputSourceFactory;
import be.kuleuven.cs.distrinet.jnome.workspace.JavaProjectConfig;
import be.kuleuven.cs.distrinet.jnome.workspace.JavaProjectConfigurator;
import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

public class JLoProjectConfigurator extends JavaProjectConfigurator {

	public JLoProjectConfigurator(JarFile javaBaseJarPath) {
		super(javaBaseJarPath);
	}

	@Override
	protected JavaProjectConfig createProjectConfig(String projectName, File root, View view, Workspace workspace) throws ConfigException {
		return new JLoProjectConfig(projectName, root, view, workspace, new LazyJavaFileInputSourceFactory());
	}
	
	@Override
	public SafePredicate<? super String> sourceFileFilter() {
		return new ExtensionPredicate("jlo");
	}

	protected void processBaseLibraries(View view, Workspace workspace, BaseLibraryConfiguration baseLibraryConfiguration) {
		new JLoBaseLibraryConfigurator(language()).process(view, baseLibraryConfiguration);
	}

	
	public class JLoBaseLibraryConfigurator extends BaseLibraryConfigurator {

		public JLoBaseLibraryConfigurator(Language language) {
			super(language);
		}
		
		@Override
		protected void addBaseLoader(View view) {
			// The base loader for Java already creates the primitive types
			// and load the Java base library.
			// Therefore, we only have to add a loader for the base JLo library file.
			
			// 1) For a normal jar release, the base library is always stored inside jlo.jar
			// 2) For the Eclipse plugin, the base library is included inside the plugin jar
			//    and the jlo-base.jar is included as well.
			// 3) When the Eclipse plugin is run as a nested Eclipse during development, 
			//    we use a Directory loader to read the sources directly.
			URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
			try {
				File file = new File(url.toURI());
				String path = file.getAbsolutePath();
				SafePredicate<? super String> sourceFileFilter = sourceFileFilter();
				if(path.endsWith(".jar")) {
					JarURLConnection connection = (JarURLConnection) url.openConnection();
					view.addBinary(new ZipLoader(connection.getJarFile(), sourceFileFilter));
				} else {
					file = new File(file.getParentFile(),JLO_BASE_LIBRARY_DIRECTORY);
					view.addBinary(new BaseDirectoryLoader(file.getAbsolutePath(), new LazyJavaFileInputSourceFactory(), sourceFileFilter));
				}
			} catch (Exception e) {
				throw new ConfigException(e);
			}
		}
	}

	private static final String JLO_BASE_LIBRARY_DIRECTORY = "base_library/src";

}
