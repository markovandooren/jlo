package subobjectjava.build;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import jnome.core.language.Java;
import jnome.input.JavaFactory;
import jnome.output.CompilationUnitWriter;
import jnome.output.JavaCodeWriter;
import subobjectjava.model.language.JLo;
import subobjectjava.output.JLoSyntax;
import subobjectjava.translate.IncrementalJavaTranslator;
import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.language.Language;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.oo.plugin.ObjectOrientedFactory;
import chameleon.plugin.Plugin;
import chameleon.plugin.PluginImpl;
import chameleon.plugin.build.Builder;
import chameleon.plugin.output.Syntax;

public class JLoBuilder extends PluginImpl implements Builder {
	
	public JLoBuilder(JLo source, File outputDir) {
		this(outputDir);
		setLanguage(source, Builder.class);
	}
	
	public JLoBuilder(File outputDir) {
		_writer = new CompilationUnitWriter(outputDir, ".java");
	}	
	
	@Override
	public <T extends Plugin> void setLanguage(Language lang, Class<T> pluginInterface) {
		if(! (lang instanceof JLo)) {
			throw new ChameleonProgrammerException();
		}
		super.setLanguage(lang, pluginInterface);
		Java target = new Java();
		//		target.setPlugin(Syntax.class, new JavaCodeWriter());
		target.setPlugin(Syntax.class, new JLoSyntax()); // DEBUG for viewing the intermediate steps, we attach the J.Lo syntax.
		target.setPlugin(ObjectOrientedFactory.class, new JavaFactory());
		_translator = new IncrementalJavaTranslator((JLo) lang, target);
	}

  public CompilationUnitWriter writer() {
  	return _writer;
  }

	CompilationUnitWriter _writer;

	public void build(Collection<CompilationUnit> compilationUnits,
			List<CompilationUnit> allProjectCompilationUnits) throws ModelException, IOException {
		for(CompilationUnit cu: compilationUnits) {
			build(cu, allProjectCompilationUnits);
		}
	}
	
	public void build(CompilationUnit compilationUnit, List<CompilationUnit> allProjectCompilationUnits) throws ModelException, IOException {
		try {
			String fileName = _writer.fileName(compilationUnit);
			System.out.println("Building "+fileName);
			List<CompilationUnit> compilationUnits = _translator.build(compilationUnit);
			for(CompilationUnit translated : compilationUnits) {
				_writer.write(translated);
			}
		} catch(Error e) {
			e.printStackTrace();
			throw e;
		} catch(RuntimeException e) {
			e.printStackTrace();
		}
	}

	public Language targetLanguage() {
		return _translator.targetLanguage();
	}

	public Language sourceLanguage() {
		return _translator.sourceLanguage();
	}

	private IncrementalJavaTranslator _translator;

	@Override
	public Plugin clone() {
		return new JLoBuilder(writer().outputDir());
	}
}
