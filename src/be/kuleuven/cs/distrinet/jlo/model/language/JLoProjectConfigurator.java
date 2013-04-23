package be.kuleuven.cs.distrinet.jlo.model.language;

import java.io.File;
import java.util.jar.JarFile;

import be.kuleuven.cs.distrinet.chameleon.workspace.BootstrapProjectConfig.BaseLibraryConfiguration;
import be.kuleuven.cs.distrinet.chameleon.workspace.ConfigException;
import be.kuleuven.cs.distrinet.chameleon.workspace.ExtensionPredicate;
import be.kuleuven.cs.distrinet.chameleon.workspace.View;
import be.kuleuven.cs.distrinet.chameleon.workspace.Workspace;
import be.kuleuven.cs.distrinet.jnome.input.LazyJavaFileInputSourceFactory;
import be.kuleuven.cs.distrinet.jnome.workspace.JavaProjectConfig;
import be.kuleuven.cs.distrinet.jnome.workspace.JavaProjectConfigurator;
import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;

public class JLoProjectConfigurator extends JavaProjectConfigurator {

	public JLoProjectConfigurator(JarFile javaBaseJarPath) {
		super(javaBaseJarPath);
	}

	@Override
	protected JavaProjectConfig createProjectConfig(String projectName, File root, View view, Workspace workspace, BaseLibraryConfiguration baseLibraryConfiguration) throws ConfigException {
		return new JLoProjectConfig(projectName, root, view, workspace, new LazyJavaFileInputSourceFactory(), baseJarPath(),baseLibraryConfiguration);
	}
	
	@Override
	public SafePredicate<? super String> sourceFileFilter() {
		return new ExtensionPredicate("jlo");
	}

}
