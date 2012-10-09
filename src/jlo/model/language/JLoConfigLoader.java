package jlo.model.language;

import java.io.File;

import chameleon.workspace.ConfigException;
import chameleon.workspace.View;
import jnome.input.LazyJavaFileInputSourceFactory;
import jnome.workspace.JavaConfigLoader;
import jnome.workspace.JavaProjectConfig;

public class JLoConfigLoader extends JavaConfigLoader {

	public JLoConfigLoader(String javaBaseJarPath) {
		super(javaBaseJarPath);
	}

	protected JavaProjectConfig createProjectConfig(String projectName, File root, View view) throws ConfigException {
		return new JLoProjectConfig(view, new LazyJavaFileInputSourceFactory(), projectName, root, baseJarPath());
	}

}
