package jlo.translate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import chameleon.exception.ModelException;
import chameleon.oo.type.Type;
import chameleon.plugin.output.Syntax;
import chameleon.test.provider.ElementProvider;
import chameleon.workspace.Project;

public class TypeWriter {

	public TypeWriter(Project project, ElementProvider<Type> typeProvider, File outputDir) {
		super();
		_project = project;
		_typeProvider = typeProvider;
		_outputDir = outputDir;
	}

	public Project project() {
		return _project;
	}
	
	private Project _project;
	
	public ElementProvider<Type> typeProvider() {
		return _typeProvider;
	}
	
	private ElementProvider<Type> _typeProvider;
	
	public String outputDirName() {
		return outputDir().getAbsolutePath();
	}
	
	public File outputDir() {
		return _outputDir;
	}
	
	private File _outputDir;
	
	public void write() throws IOException, ModelException {
    Syntax writer = project().language().plugin(Syntax.class);
    int i = 1;
    for(Type type:typeProvider().elements(project())) {
    	if(type.nearestAncestor(Type.class) == null) {
      String fileName = type.name()+".java";
      String packageFQN = type.namespace().getFullyQualifiedName();
      String relDirName = packageFQN.replace('.', File.separatorChar);
      File out = new File(outputDirName()+File.separatorChar + relDirName + File.separatorChar + fileName);
      System.out.println(i + " Writing: "+out.getAbsolutePath());
      File parent = out.getParentFile();
      parent.mkdirs();
      out.createNewFile();
      FileWriter fw = new FileWriter(out);
      fw.write(writer.toCode(type.parent()));
      fw.close();
      i++;
    	}
    }

	}
}
