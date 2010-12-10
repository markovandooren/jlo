package subobjectjava.translate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jnome.core.language.Java;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rejuse.association.Association;
import org.rejuse.association.SingleAssociation;

import subobjectjava.build.JLoBuilder;
import subobjectjava.input.SubobjectJavaModelFactory;
import subobjectjava.model.language.SubobjectJava;
import chameleon.core.Config;
import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.language.Language;
import chameleon.core.lookup.LookupException;
import chameleon.core.namespace.Namespace;
import chameleon.core.namespace.RootNamespace;
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
			_builder.build(type.nearestAncestor(CompilationUnit.class));
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
