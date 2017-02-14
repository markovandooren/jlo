package org.aikodi.jlo.model.language;

import org.aikodi.chameleon.core.language.Language;
import org.aikodi.chameleon.workspace.ConfigException;
import org.aikodi.chameleon.workspace.FileDocumentLoaderFactory;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.java.workspace.JavaProjectConfiguration;

public class JLoProjectConfiguration extends JavaProjectConfiguration {

	public JLoProjectConfiguration(View view, FileDocumentLoaderFactory inputSourceFactory)
			throws ConfigException {
		super(view, inputSourceFactory);
	}

	protected Language jlo() {
		return language("jlo");
	}
		
}
