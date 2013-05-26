package be.kuleuven.cs.distrinet.jlo.model.language;

import java.io.File;
import java.net.URL;
import java.util.jar.JarFile;

import sun.net.www.protocol.jar.JarURLConnection;
import be.kuleuven.cs.distrinet.chameleon.core.language.Language;
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

}
