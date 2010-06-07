package subobjectjava.translate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.language.Language;
import chameleon.core.lookup.LookupException;
import chameleon.exception.ModelException;
import chameleon.oo.type.Type;
import chameleon.output.Syntax;
import chameleon.test.provider.ElementProvider;

public class CompilationUnitWriter {

	public CompilationUnitWriter(
//			Language language, ElementProvider<CompilationUnit> typeProvider, 
			File outputDir) {
		super();
//		_language = language;
//		_compilationUnitProvider = typeProvider;
		_outputDir = outputDir;
	}

//	public Language language() {
//		return _language;
//	}
//	
//	private Language _language;
	
//	public ElementProvider<CompilationUnit> compilationUnitProvider() {
//		return _compilationUnitProvider;
//	}
//	
//	private ElementProvider<CompilationUnit> _compilationUnitProvider;
	
	public String outputDirName() {
		return outputDir().getAbsolutePath();
	}
	
	public File outputDir() {
		return _outputDir;
	}
	
	private File _outputDir;
	
//	public void write() throws IOException, ModelException {
//    int i = 1;
//    for(CompilationUnit cu:compilationUnitProvider().elements(language())) {
//      File out = write(cu);
//      System.out.println(i + " Writing: "+out.getAbsolutePath());
//      i++;
//    }
//
//	}

	public File write(CompilationUnit cu) throws LookupException, ModelException, IOException {
		Syntax writer = cu.language().connector(Syntax.class);
		if(writer != null) {
			String fileName = fileName(cu);
			if(fileName != null) {
				String packageFQN = packageFQN(cu);
				String relDirName = packageFQN.replace('.', File.separatorChar);
				File out = new File(outputDirName()+File.separatorChar + relDirName + File.separatorChar + fileName);
				File parent = out.getParentFile();
				parent.mkdirs();
				out.createNewFile();
				FileWriter fw = new FileWriter(out);
				fw.write(writer.toCode(cu));
				fw.close();
				System.out.println("Wrote: "+out.getAbsolutePath());
				return out;
			}
		} 
		return null;
	}
	
	public String fileName(CompilationUnit compilationUnit) throws LookupException, ModelException {
		Type result = mainType(compilationUnit);
		String name = (result == null ? null : result.getName()+".java");
		return name;
	}

	public String packageFQN(CompilationUnit compilationUnit) throws LookupException, ModelException {
		return mainType(compilationUnit).getNamespace().getFullyQualifiedName();
	}
	
	private Type mainType(CompilationUnit compilationUnit) throws LookupException, ModelException {
		Type result = null;
		for(Type type: compilationUnit.descendants(Type.class)) {
			if((type.nearestAncestor(Type.class) == null) && ((result == null) || (type.scope().ge(result.scope())))) {
				result = type;
			}
		}
		return result;
	}
}
