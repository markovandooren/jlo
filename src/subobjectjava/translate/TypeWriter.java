package subobjectjava.translate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import chameleon.core.language.Language;
import chameleon.core.type.Type;
import chameleon.exception.ModelException;
import chameleon.output.Syntax;
import chameleon.test.provider.ElementProvider;

public class TypeWriter {

	public TypeWriter(Language language, ElementProvider<Type> typeProvider, File outputDir) {
		super();
		_language = language;
		_typeProvider = typeProvider;
		_outputDir = outputDir;
	}

	public Language language() {
		return _language;
	}
	
	private Language _language;
	
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
    Syntax writer = language().connector(Syntax.class);
    int i = 1;
    for(Type type:typeProvider().elements(language())) {
    	if(type.nearestAncestor(Type.class) == null) {
      String fileName = type.getName()+".java";
      String packageFQN = type.getNamespace().getFullyQualifiedName();
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
