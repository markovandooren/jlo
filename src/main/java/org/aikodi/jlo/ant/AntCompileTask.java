package org.aikodi.jlo.ant;

import java.io.File;

import org.aikodi.chameleon.workspace.ConfigException;
import org.aikodi.chameleon.workspace.LanguageRepository;
import org.aikodi.chameleon.workspace.Project;
import org.aikodi.chameleon.workspace.ProjectConfigurator;
import org.aikodi.chameleon.workspace.Workspace;
import org.aikodi.chameleon.workspace.XMLProjectLoader;
import org.aikodi.java.core.language.Java7LanguageFactory;
import org.aikodi.jlo.build.JLoBuilder;
import org.aikodi.jlo.model.language.JLo;
import org.aikodi.jlo.model.language.JLoLanguageFactory;
import org.aikodi.jlo.model.language.JLoProjectConfigurator;
import org.apache.tools.ant.BuildException;

public class AntCompileTask extends AbstractTask {

	@Override
	public void execute() throws BuildException {
		LanguageRepository repository = new LanguageRepository();
		Workspace workspace = new Workspace(repository);

		repository.add(new Java7LanguageFactory().create());
		JLo jlo = new JLoLanguageFactory().create();
		repository.add(jlo);
		((JLoProjectConfigurator)jlo.plugin(ProjectConfigurator.class)).searchInParent();
		File configFile = getConfig();
		XMLProjectLoader config = new XMLProjectLoader(workspace);
    try {
      Project project = config.project(configFile, null);
      // Attach the builder
      JLoBuilder builder = new JLoBuilder(project.views().get(0));
      // build!
      try {
        builder.buildAll(getDestDir(), null);
        System.out.println("Done building.");
      } catch (org.aikodi.chameleon.plugin.build.BuildException e) {
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
