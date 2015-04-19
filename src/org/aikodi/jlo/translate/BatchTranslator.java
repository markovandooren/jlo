package org.aikodi.jlo.translate;

import java.io.File;
import java.io.IOException;

import org.aikodi.chameleon.core.Config;
import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.namespace.Namespace;
import org.aikodi.chameleon.input.ParseException;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.plugin.build.BuildException;
import org.aikodi.chameleon.test.provider.BasicDescendantProvider;
import org.aikodi.chameleon.test.provider.ElementProvider;
import org.aikodi.chameleon.workspace.ConfigException;
import org.aikodi.chameleon.workspace.LanguageRepository;
import org.aikodi.chameleon.workspace.Project;
import org.aikodi.chameleon.workspace.ProjectConfigurator;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.chameleon.workspace.Workspace;
import org.aikodi.chameleon.workspace.XMLProjectLoader;
import org.aikodi.jlo.build.JLoBuilder;
import org.aikodi.jlo.model.language.JLo;
import org.aikodi.jlo.model.language.JLoLanguageFactory;
import org.aikodi.jlo.model.language.JLoProjectConfigurator;
import org.aikodi.jlo.model.type.RegularJLoType;

import be.kuleuven.cs.distrinet.jnome.core.language.Java7LanguageFactory;

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


	public void translate() throws BuildException {
		for(Type type: typeProvider().elements(sourceProject())) {
			// The second argument is never used for the JLo translation, but for now it must be present because otherwise the
			// aspect weaver does not know which compilation units are present in the project.
			Document compilationUnit = type.nearestAncestor(Document.class);
			compilationUnit.verify();
			_builder.build(compilationUnit, _outputDir);
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
    Config.setCaching(true);

    LanguageRepository repo = new LanguageRepository();
		Workspace workspace = new Workspace(repo);

    repo.add(new Java7LanguageFactory().create());
    JLo jlo = new JLoLanguageFactory().create();
    repo.add(jlo);
    ((JLoProjectConfigurator)jlo.plugin(ProjectConfigurator.class)).searchInParent();
    File configFile = new File(args[0]);
    XMLProjectLoader config = new XMLProjectLoader(workspace);
    try {
      Project project = config.project(configFile, null);
      // Attach the builder
      JLoBuilder builder = new JLoBuilder(project.views().get(0));
      // build!
      try {
        builder.buildAll(new File(args[1]), null);
        System.out.println("Done building.");
      } catch (org.aikodi.chameleon.plugin.build.BuildException e) {
        throw new BuildException(e);
      }
    } catch (ConfigException e) {
      throw new BuildException(e);
    }
//
//
//    
//    File outputDir = new File(args[1]);
//    
//    List<String> list = new ArrayList<String>();
//    for(int i=0; i<args.length;i++){list.add(args[i]);}
//    // remove the output directory
//    list.remove(1);
//    // create a new arguments array
//    // FIXME this is stupid. Arrays are stupid
//    String[] arguments = new String[list.size()];
//    for(int i=0; i<arguments.length;i++){arguments[i]=list.get(i);}
//    ModelBuilder provider = new ModelBuilder(arguments,workspace);
//    Project project = provider.project();
//		View view = project.views().get(0);
//    long start = System.currentTimeMillis();
//    new BatchTranslator(view, provider.namespaceProvider(),outputDir).translate();
//    long stop = System.currentTimeMillis();
//    System.out.println("Translation took "+(stop - start) + " milliseconds.");
  }

}
