package be.kuleuven.cs.distrinet.jlo.ant;

import java.io.File;

import be.kuleuven.cs.distrinet.jlo.build.JLoBuilder;
import be.kuleuven.cs.distrinet.jlo.model.language.JLo;
import be.kuleuven.cs.distrinet.jlo.model.language.JLoLanguageFactory;
import be.kuleuven.cs.distrinet.jlo.model.language.JLoProjectConfigurator;
import be.kuleuven.cs.distrinet.jnome.core.language.JavaLanguageFactory;

import org.apache.tools.ant.BuildException;

import be.kuleuven.cs.distrinet.chameleon.core.Config;
import be.kuleuven.cs.distrinet.chameleon.workspace.BootstrapProjectConfig;
import be.kuleuven.cs.distrinet.chameleon.workspace.ConfigException;
import be.kuleuven.cs.distrinet.chameleon.workspace.LanguageRepository;
import be.kuleuven.cs.distrinet.chameleon.workspace.Project;
import be.kuleuven.cs.distrinet.chameleon.workspace.ProjectConfigurator;
import be.kuleuven.cs.distrinet.chameleon.workspace.Workspace;

public class AntCompileTask extends AbstractTask {

	@Override
	public void execute() throws BuildException {
		Config.setCaching(true);
		LanguageRepository repository = new LanguageRepository();
		Workspace workspace = new Workspace(repository);

		repository.add(new JavaLanguageFactory().create());
		JLo jlo = new JLoLanguageFactory().create();
		repository.add(jlo);
		((JLoProjectConfigurator)jlo.plugin(ProjectConfigurator.class)).searchInParent();
		File configFile = getConfig();
		BootstrapProjectConfig config = new BootstrapProjectConfig(workspace);
		try {
			Project project = config.project(configFile, null);
			// Attach the builder
			JLoBuilder builder = new JLoBuilder(project.views().get(0));
			// build!
			try {
				builder.buildAll(getDestDir(),null);
				System.out.println("Done building.");
			} catch (be.kuleuven.cs.distrinet.chameleon.plugin.build.BuildException e) {
				throw new BuildException(e);
			}
		} catch (ConfigException e) {
			throw new BuildException(e);
		}
	}
	
	public void setDestDir(File output) {
		_output = output;
	}
	
	public File getDestDir() {
		return _output;
	}
	
	private File _output;

	/**
	 * args[0] should be the name of the config file.
	 * args[1] should be the name of the output directory.
	 * @param args
	 */
	public static void main(String[] args) {
		AntCompileTask task = new AntCompileTask();
		task.setConfig(new File(args[0]));
		task.setDestDir(new File(args[1]));
		task.execute();
	}
}
