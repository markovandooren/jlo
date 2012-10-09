package jlo.translate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jlo.build.JLoBuilder;
import jlo.model.language.JLoLanguageFactory;
import jlo.model.type.RegularJLoType;
import jnome.core.language.JavaLanguageFactory;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import chameleon.core.Config;
import chameleon.core.document.Document;
import chameleon.core.namespace.Namespace;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.input.ParseException;
import chameleon.oo.type.Type;
import chameleon.support.tool.ModelBuilder;
import chameleon.test.provider.BasicDescendantProvider;
import chameleon.test.provider.ElementProvider;
import chameleon.workspace.LanguageRepository;
import chameleon.workspace.Project;
import chameleon.workspace.View;

public class BatchTranslator {

	public BatchTranslator(View view, ElementProvider<Namespace> namespaceProvider, File outputDir) throws ParseException, IOException {
		_sourceView = view;
		_typeProvider = new BasicDescendantProvider<RegularJLoType>(namespaceProvider, RegularJLoType.class);
		_builder = new JLoBuilder(_sourceView);
		_outputDir = outputDir;
	}

	private File _outputDir;
	private JLoBuilder _builder;
	
	public JLoBuilder builder() {
		return _builder;
	}
	
	public View sourceProject() {
		return _sourceView;
	}
	
	private View _sourceView;
	
	public ElementProvider<? extends Type> typeProvider() {
		return _typeProvider;
	}

	private ElementProvider<RegularJLoType> _typeProvider;


	public void translate() throws ParseException, IOException, ChameleonProgrammerException, ModelException {
		for(Type type: typeProvider().elements(sourceProject())) {
			// The second argument is never used for the JLo translation, but for now it must be present because otherwise the
			// aspect weaver does not know which compilation units are present in the project.
			Document compilationUnit = type.nearestAncestor(Document.class);
			compilationUnit.verify();
			_builder.build(compilationUnit, new ArrayList<Document>(),_outputDir);
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
      System.out.println("Usage: java .... JavaTranslator projectXML outputDir @recursivePackageFQN*"); // #packageFQN* $typeFQN*
    }
    BasicConfigurator.configure();
    Logger.getRootLogger().setLevel(Level.FATAL);
    Config.setCaching(true);

    LanguageRepository repo = new LanguageRepository();
    repo.add(new JLoLanguageFactory().create());
    repo.add(new JavaLanguageFactory().create());
    File outputDir = new File(args[1]);
    
    List<String> list = new ArrayList<String>();
    for(int i=0; i<args.length;i++){list.add(args[i]);}
    // remove the output directory
    list.remove(1);
    // create a new arguments array
    // FIXME this is stupid. Arrays are stupid
    String[] arguments = new String[list.size()];
    for(int i=0; i<arguments.length;i++){arguments[i]=list.get(i);}
//		Project project = new Project("copy test",new RootNamespace(new LazyNamespaceFactory()),language, new File("."));
    ModelBuilder provider = new ModelBuilder(arguments,repo);
    Project project = provider.project();
		View view = project.views().get(0);
    long start = System.currentTimeMillis();
    new BatchTranslator(view, provider.namespaceProvider(),outputDir).translate();
    long stop = System.currentTimeMillis();
    System.out.println("Translation took "+(stop - start) + " milliseconds.");
  }

}
