package jlo.translate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jlo.build.JLoBuilder;
import jlo.input.JLoFactory;
import jlo.input.JLoModelFactory;
import jlo.model.language.JLo;
import jlo.model.type.RegularJLoType;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import chameleon.core.Config;
import chameleon.core.compilationunit.Document;
import chameleon.core.namespace.Namespace;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.input.ParseException;
import chameleon.oo.plugin.ObjectOrientedFactory;
import chameleon.oo.type.Type;
import chameleon.support.tool.ModelBuilder;
import chameleon.test.provider.BasicDescendantProvider;
import chameleon.test.provider.ElementProvider;

public class BatchTranslator {

	public BatchTranslator(JLo language, ElementProvider<Namespace> namespaceProvider, File outputDir) throws ParseException, IOException {
		_sourceLanguage = language;
		_sourceLanguage.setPlugin(ObjectOrientedFactory.class, new JLoFactory());
		_typeProvider = new BasicDescendantProvider<RegularJLoType>(namespaceProvider, RegularJLoType.class);
		_builder = new JLoBuilder(_sourceLanguage, outputDir);
	}

	private JLoBuilder _builder;
	
	public JLoBuilder builder() {
		return _builder;
	}
	
	public JLo sourceLanguage() {
		return _sourceLanguage;
	}
	
	private JLo _sourceLanguage;
	
	public ElementProvider<? extends Type> typeProvider() {
		return _typeProvider;
	}

	private ElementProvider<RegularJLoType> _typeProvider;


	public void translate() throws ParseException, IOException, ChameleonProgrammerException, ModelException {
		for(Type type: typeProvider().elements(sourceLanguage())) {
			// The second argument is never used for the JLo translation, but for now it must be present because otherwise the
			// aspect weaver does not know which compilation units are present in the project.
			Document compilationUnit = type.nearestAncestor(Document.class);
			compilationUnit.verify();
			_builder.build(compilationUnit, new ArrayList<Document>());
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
    ModelBuilder provider = new ModelBuilder(new JLoModelFactory(),args,".jlo",true,true);
    File outputDir = provider.outputDir();
    long start = System.currentTimeMillis();
    new BatchTranslator((JLo) provider.language(), provider.namespaceProvider(),outputDir).translate();
    long stop = System.currentTimeMillis();
    System.out.println("Translation took "+(stop - start) + " milliseconds.");
  }

}
