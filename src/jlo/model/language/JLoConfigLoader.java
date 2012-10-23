package jlo.model.language;

import java.io.File;

import jnome.input.LazyJavaFileInputSourceFactory;
import jnome.workspace.JavaConfigLoader;
import jnome.workspace.JavaProjectConfig;
import chameleon.workspace.ConfigException;
import chameleon.workspace.View;

public class JLoConfigLoader extends JavaConfigLoader {

	public JLoConfigLoader(String javaBaseJarPath) {
		super(javaBaseJarPath);
	}

	@Override
	protected JavaProjectConfig createProjectConfig(String projectName, File root, View view) throws ConfigException {
		return new JLoProjectConfig(view, new LazyJavaFileInputSourceFactory(), projectName, root, baseJarPath());
	}

}
