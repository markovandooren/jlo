package jlo.model.language;

import java.io.File;
import java.util.Collections;
import java.util.List;

import jnome.workspace.JavaProjectConfig;
import chameleon.workspace.ConfigException;
import chameleon.workspace.FileInputSourceFactory;
import chameleon.workspace.View;

public class JLoProjectConfig extends JavaProjectConfig {

	public JLoProjectConfig(View view, FileInputSourceFactory inputSourceFactory, String projectName, File root, String baseJarPath)
			throws ConfigException {
		super(view, inputSourceFactory, projectName, root, baseJarPath);
		// No BaseJLoProjectLoader is created (doesn't exist) since there no additional predefined elements yet.
	}
	
	@Override
	protected List<String> sourceExtensions() {
		return Collections.singletonList(".jlo");
	}
}
