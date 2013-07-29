package be.kuleuven.cs.distrinet.jlo.model.language;

import be.kuleuven.cs.distrinet.chameleon.core.language.Language;
import be.kuleuven.cs.distrinet.chameleon.workspace.ConfigException;
import be.kuleuven.cs.distrinet.chameleon.workspace.FileInputSourceFactory;
import be.kuleuven.cs.distrinet.chameleon.workspace.View;
import be.kuleuven.cs.distrinet.chameleon.workspace.Workspace;
import be.kuleuven.cs.distrinet.jnome.workspace.JavaProjectConfiguration;

public class JLoProjectConfiguration extends JavaProjectConfiguration {

	public JLoProjectConfiguration(View view, FileInputSourceFactory inputSourceFactory)
			throws ConfigException {
		super(view, inputSourceFactory);
	}

	protected Language jlo() {
		return language("jlo");
	}
		
}
