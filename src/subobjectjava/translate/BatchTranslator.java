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

import subobjectjava.input.SubobjectJavaModelFactory;
import subobjectjava.model.language.SubobjectJava;
import chameleon.core.Config;
import chameleon.core.language.Language;
import chameleon.core.lookup.LookupException;
import chameleon.core.namespace.Namespace;
import chameleon.core.namespace.RootNamespace;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.input.ParseException;
import chameleon.oo.type.Type;
import chameleon.test.provider.BasicDescendantProvider;
import chameleon.test.provider.ElementProvider;

public class BatchTranslator {

	public BatchTranslator(SubobjectJava language, ElementProvider<Namespace> namespaceProvider) throws ParseException, IOException {
		_sourceLanguage = language;
		_typeProvider = new BasicDescendantProvider<Type>(namespaceProvider, Type.class);
		_translator = new JavaTranslator();
	}

	
	private JavaTranslator _translator;
	
	public JavaTranslator basicTranslator() {
		return _translator;
	}
	
	public Language sourceLanguage() {
		return _sourceLanguage;
	}
	
	private Language _sourceLanguage;
	
	public ElementProvider<Type> typeProvider() {
		return _typeProvider;
	}

	private ElementProvider<Type> _typeProvider;


	public Java translate() throws ParseException, IOException, ChameleonProgrammerException, LookupException {
		RootNamespace clone = sourceLanguage().defaultNamespace().clone();
		Java result = new Java();
		result.cloneConnectorsFrom(sourceLanguage());
		result.cloneProcessorsFrom(sourceLanguage());
		result.setDefaultNamespace(clone);
		Map<Type,Type> map = new HashMap<Type,Type>();
		for(Type type: typeProvider().elements(result)) {
			Type newType = basicTranslator().translation(type);
			map.put(newType, type);
		}
		
		for(Entry<Type,Type> entry : map.entrySet()) {
			SingleAssociation newParentlink = entry.getKey().parentLink();
			SingleAssociation oldParentlink = entry.getValue().parentLink();
			Association childLink = oldParentlink.getOtherRelation();
			childLink.replace(oldParentlink, newParentlink);
		}
		return result;
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
   * java Copy outputDir baseInputDir customInputDir1 customInputDir2 @myPackage.subPackage 
   */
  public static void main(String[] args) throws Exception {
    if(args.length < 2) {
      System.out.println("Usage: java .... JavaTranslator outputDir inputDir* @recursivePackageFQN* #packageFQN* $typeFQN*");
    }
    BasicConfigurator.configure();
    Logger.getRootLogger().setLevel(Level.FATAL);
    Config.setCaching(true);
//    Config.setCacheElementReferences(true);
//    Config.setCacheElementProperties(true);
    ProviderProvider provider = new ProviderProvider(new SubobjectJavaModelFactory(),".java",true,true);
    provider.processArguments(args);
    long start = System.currentTimeMillis();
    Java result = new BatchTranslator((SubobjectJava) provider.language(), provider.namespaceProvider()).translate();
    // Output
    long stop = System.currentTimeMillis();
    File outputDir = provider.outputDir();
    TypeWriter writer = new TypeWriter(result, new BasicDescendantProvider<Type>(provider.namespaceProvider(), Type.class),outputDir);
    writer.write();
    System.out.println("Translation took "+(stop - start) + " milliseconds.");
  }

}
