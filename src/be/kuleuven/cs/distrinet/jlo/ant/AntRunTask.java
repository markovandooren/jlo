package be.kuleuven.cs.distrinet.jlo.ant;

import java.io.File;

import be.kuleuven.cs.distrinet.jlo.model.language.JLoProjectConfig;
import be.kuleuven.cs.distrinet.jlo.translate.AbstractTranslator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Path.PathElement;


public class AntRunTask extends AbstractTask {

	@Override
	public void execute() throws BuildException {
		Java java = new Java(this);
		java.setFork(true);
		java.setClassname(getClassname()+AbstractTranslator.IMPL);
//		Path path = new Path(getProject());
//		path.addJavaRuntime();
//		DirSet bin = new DirSet();
//		bin.setDir(dir)
//		
//		path.add(new Path(p, path))
//		java.setClasspath(path);
		Path path = getClasspath();
		PathElement jlo_base = path.createPathElement();
		File loc = JLoProjectConfig.jloBase().getAbsoluteFile().getAbsoluteFile();
		jlo_base.setLocation(loc);
		path.add(jlo_base);
		
		path.addJavaRuntime();
		
		java.setClasspath(path);
		
		java.execute();
	}
	
	public void addClasspath(Path path) {
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
	
	public void setClassname(String name) {
		_className = name;
	}
}
