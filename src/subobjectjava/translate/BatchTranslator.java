package subobjectjava.translate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import subobjectjava.build.JLoBuilder;
import subobjectjava.input.SubobjectJavaModelFactory;
import subobjectjava.model.language.SubobjectJava;
import chameleon.core.Config;
import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.namespace.Namespace;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.input.ParseException;
import chameleon.oo.type.Type;
import chameleon.test.provider.BasicDescendantProvider;
import chameleon.test.provider.ElementProvider;

public class BatchTranslator {

	public BatchTranslator(SubobjectJava language, ElementProvider<Namespace> namespaceProvider, File outputDir) throws ParseException, IOException {
		_sourceLanguage = language;
		_typeProvider = new BasicDescendantProvider<Type>(namespaceProvider, Type.class);
		_builder = new JLoBuilder(_sourceLanguage, outputDir);
	}

	private JLoBuilder _builder;
	
	public JLoBuilder builder() {
		return _builder;
	}
	
	public SubobjectJava sourceLanguage() {
		return _sourceLanguage;
	}
	
	private SubobjectJava _sourceLanguage;
	
	public ElementProvider<Type> typeProvider() {
		return _typeProvider;
	}

	private ElementProvider<Type> _typeProvider;


	public void translate() throws ParseException, IOException, ChameleonProgrammerException, ModelException {
		for(Type type: typeProvider().elements(sourceLanguage())) {
			// The second argument is never used, but for now it must be present because otherwise the
			// aspects compiler does not know which compilation units are present in the project.
			_builder.build(type.nearestAncestor(CompilationUnit.class), new ArrayList<CompilationUnit>());
		}
	}
	
  /**
   * args[0] = path for the directory to write output
   * args[1] = path to read input files
   * ...1 or more input paths possible...
   * args[i] = fqn of package to read, let this start with "@" to read the package recursively
   *...1 or more packageFqns possible...
   * args[n] = fqn of package to read, let this start with "#" to NOT read the package recursively.
   *...1 or more packageFqns possible...
   *
   * Example 
   * java Copy outputDir apiInputDir customInputDir1 customInputDir2 @myPackage.subPackage 
   */
  public static void main(String[] args) throws Exception {
    if(args.length < 2) {
      System.out.println("Usage: java .... JavaTranslator outputDir apiDir inputDir* @recursivePackageFQN* #packageFQN* $typeFQN*");
    }
    BasicConfigurator.configure();
    Logger.getRootLogger().setLevel(Level.FATAL);
    Config.setCaching(true);
    ProviderProvider provider = new ProviderProvider(new SubobjectJavaModelFactory(),".jlow",true,true);
    provider.processArguments(args);
    File outputDir = provider.outputDir();
    long start = System.currentTimeMillis();
    new BatchTranslator((SubobjectJava) provider.language(), provider.namespaceProvider(),outputDir).translate();
    long stop = System.currentTimeMillis();
    System.out.println("Translation took "+(stop - start) + " milliseconds.");
  }

}
