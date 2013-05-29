package be.kuleuven.cs.distrinet.jlo.model.language;

import be.kuleuven.cs.distrinet.chameleon.core.language.Language;
import be.kuleuven.cs.distrinet.chameleon.workspace.ConfigException;
import be.kuleuven.cs.distrinet.chameleon.workspace.FileInputSourceFactory;
import be.kuleuven.cs.distrinet.chameleon.workspace.View;
import be.kuleuven.cs.distrinet.chameleon.workspace.Workspace;
import be.kuleuven.cs.distrinet.jnome.workspace.JavaProjectConfig;

public class JLoProjectConfig extends JavaProjectConfig {

	public JLoProjectConfig(View view, FileInputSourceFactory inputSourceFactory)
			throws ConfigException {
		super(view, inputSourceFactory);
	}

	protected Language jlo() {
		return language("jlo");
	}
		
}
