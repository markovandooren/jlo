package jlo.model.language;

import java.io.File;

import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;
import jnome.input.LazyJavaFileInputSourceFactory;
import jnome.workspace.JavaProjectConfig;
import jnome.workspace.JavaProjectConfigurator;
import chameleon.workspace.BootstrapProjectConfig.BaseLibraryConfiguration;
import chameleon.workspace.ConfigException;
import chameleon.workspace.ExtensionPredicate;
import chameleon.workspace.View;
import chameleon.workspace.Workspace;

public class JLoProjectConfigurator extends JavaProjectConfigurator {

	public JLoProjectConfigurator(String javaBaseJarPath) {
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
