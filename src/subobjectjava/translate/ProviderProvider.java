/**
 * 
 */
package subobjectjava.translate;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import chameleon.core.language.Language;
import chameleon.core.namespace.Namespace;
import chameleon.input.ModelFactory;
import chameleon.input.ParseException;
import chameleon.test.provider.BasicModelProvider;
import chameleon.test.provider.BasicNamespaceProvider;
import chameleon.test.provider.ElementProvider;

public class ProviderProvider {

	public ProviderProvider(ModelFactory modelFactory, String extension, boolean output, boolean base) {
		_modelProvider = new BasicModelProvider(modelFactory, extension);
		_output = output;
		_base = base;
	}
	
  /**
   * args[0] = path for the directory to write output IF output() == true
   * args[1] = path to read input files
   * ...1 or more input paths possible...
   * args[i] = fqn of package to read, let this start with "@" to read the package recursively
   *...1 or more packageFqns possible...
   * args[n] = fqn of package to read, let this start with "#" to NOT read the package recursively.
   *...1 or more packageFqns possible...
   *
   * Example 
   * java Copy outputDir inputDir1 inputDir2 @myPackage.subPackage #java #java.security 
   * @throws IOException 
   * @throws ParseException 
   */
	public void processArguments(String[] args) throws ParseException, IOException {
    int low = (output() ? 1 : 0);
    if(output()) {
    	_outputDir = new File(args[0]);
    }
    if(base()) {
    	low++;
    }
    Set<File> files = new HashSet<File>();
    // Configure base library directory
    if(base()) {
     	int baseIndex = low-1;
			String arg = args[baseIndex];
			if(! arg.startsWith("@") && ! arg.startsWith("#")&& ! arg.startsWith("%")) {
     		_modelProvider.includeBase(arg);
      }
    }
    for(int i = low; i < args.length;i++) {
     	String arg = args[i];
			if(! arg.startsWith("@") && ! arg.startsWith("#")&& ! arg.startsWith("%")) {
     		_modelProvider.includeCustom(arg);
      }
    }
    _language = _modelProvider.model();
    
    _namespaceProvider = new BasicNamespaceProvider();
    
    for(int i = low; i < args.length;i++) {
      String arg = args[i];
      String actual = arg.substring(1);
			if(arg.startsWith("@")) {
				_namespaceProvider.addNamespace(actual);
      } 
    }
	}
	
	public ElementProvider<Namespace> namespaceProvider() {
		return _namespaceProvider;
	}
	
	public Language language() {
		return _language;
	}
	
	private Language _language;
	
	private BasicNamespaceProvider _namespaceProvider;
	
	private BasicModelProvider _modelProvider;
	
	public boolean output() {
		return _output;
	}
	
	private boolean _output;
	
	public String extension() {
		return _extension;
	}
	
	private String _extension;
	
	public boolean base() {
		return _base;
	}
	
	private boolean _base;
	
	private File _outputDir;

	public File outputDir() {
		return _outputDir;
	}
	
}