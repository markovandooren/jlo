package jlo.model.language;

import java.io.File;

import jnome.input.LazyJavaFileInputSourceFactory;
import jnome.workspace.JavaProjectConfigurator;
import jnome.workspace.JavaProjectConfig;
import chameleon.workspace.ConfigException;
import chameleon.workspace.View;
import chameleon.workspace.BootstrapProjectConfig.BaseLibraryConfiguration;

public class JLoConfigLoader extends JavaProjectConfigurator {

	public JLoConfigLoader(String javaBaseJarPath) {
		super(javaBaseJarPath);
	}

	@Override
	protected JavaProjectConfig createProjectConfig(String projectName, File root, View view, BaseLibraryConfiguration baseLibraryConfiguration) throws ConfigException {
		return new JLoProjectConfig(view, new LazyJavaFileInputSourceFactory(), projectName, root, baseJarPath(),baseLibraryConfiguration);
	}

}
