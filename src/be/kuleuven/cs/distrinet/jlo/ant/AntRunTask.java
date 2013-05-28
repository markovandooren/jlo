package be.kuleuven.cs.distrinet.jlo.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Assertions;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Path.PathElement;

import be.kuleuven.cs.distrinet.jlo.model.language.JLoProjectConfigurator;
import be.kuleuven.cs.distrinet.jlo.translate.AbstractTranslator;


public class AntRunTask extends AbstractTask {

	@Override
	public void execute() throws BuildException {
		Java java = new Java(this);
		java.setFork(true);
		java.setClassname(getClassname()+AbstractTranslator.IMPL);
		if(assertions() != null) {
			java.addAssertions(assertions());
		}
		Path path = getClasspath();
		PathElement jlo_base = path.createPathElement();
		File loc = JLoProjectConfigurator.jloBase().getAbsoluteFile().getAbsoluteFile();
		jlo_base.setLocation(loc);
		path.add(jlo_base);
		
		path.addJavaRuntime();
		
		java.setClasspath(path);
		
		java.execute();
	}
	
	public void addClasspath(Path path) {
		setClasspath(path);
	}
	
	public void setClasspath(Path path) {
		_path = path;
	}
	
	public Path getClasspath() {
		return _path;
	}
	
	private Path _path;
	
	private String _className;
	
	public String getClassname() {
		return _className;
	}
	
	public Assertions assertions() {
		return _assertions;
	}
	
	public void addAssertions(Assertions asserts) {
		_assertions = asserts;
	}
	
	private Assertions _assertions;
	
	public void setClassname(String name) {
		_className = name;
	}
}
