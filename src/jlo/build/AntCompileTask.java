package jlo.build;

import java.io.File;

import jlo.model.language.JLoLanguageFactory;
import jnome.core.language.JavaLanguageFactory;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import chameleon.core.Config;
import chameleon.workspace.BootstrapProjectConfig;
import chameleon.workspace.ConfigException;
import chameleon.workspace.LanguageRepository;
import chameleon.workspace.Project;
import chameleon.workspace.Workspace;

public class AntCompileTask extends Task {

	@Override
	public void execute() throws BuildException {
		Config.setCaching(true);
		LanguageRepository repository = new LanguageRepository();
		Workspace workspace = new Workspace(repository);

		repository.add(new JavaLanguageFactory().create());
		repository.add(new JLoLanguageFactory().create());
		File configFile = getConfig();
		File root = configFile.getParentFile();
		BootstrapProjectConfig config = new BootstrapProjectConfig(root, workspace);
		try {
			Project project = config.project(configFile, null);
			// Attach the builder
			JLoBuilder builder = new JLoBuilder(project.views().get(0));
			// build!
			try {
				builder.buildAll(getOutput(),null);
			} catch (chameleon.plugin.build.BuildException e) {
				throw new BuildException(e);
			}
		} catch (ConfigException e) {
			throw new BuildException(e);
		}
	}
	
	public void setOutput(File output) {
		_output = output;
	}
	
	public File getOutput() {
		return _output;
	}
	
	private File _output;

	/**
	 * Set the configuration file of the Chameleon project. Typically
	 * this file will be named 'project.xml'.
	 * @param config
	 */
	public void setConfig(File config) {
		_config = config;
	}
	
	/**
	 * Return the 
	 * @return
	 */
	public File getConfig() {
		return _config;
	}
	
	private File _config;
	
	/**
	 * args[0] should be the name of the config file.
	 * args[1] should be the name of the output directory.
	 * @param args
	 */
	public static void main(String[] args) {
		AntCompileTask task = new AntCompileTask();
		task.setConfig(new File(args[0]));
		task.setOutput(new File(args[1]));
		task.execute();
	}
}
