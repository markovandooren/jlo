package be.kuleuven.cs.distrinet.jlo.eclipse;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.core.resources.IProject;

import be.kuleuven.cs.distrinet.jlo.build.JLoBuilder;
import be.kuleuven.cs.distrinet.jlo.model.component.ComponentRelation;
import be.kuleuven.cs.distrinet.jlo.model.component.InstantiatedMemberSubobjectParameter;
import be.kuleuven.cs.distrinet.jlo.model.component.MultiFormalComponentParameter;
import be.kuleuven.cs.distrinet.jlo.model.component.SingleFormalComponentParameter;
import be.kuleuven.cs.distrinet.jnome.eclipse.JavaEditorExtension;
import be.kuleuven.cs.distrinet.chameleon.eclipse.presentation.treeview.CompositeIconProvider;
import be.kuleuven.cs.distrinet.chameleon.eclipse.presentation.treeview.DefaultIconProvider;
import be.kuleuven.cs.distrinet.chameleon.eclipse.presentation.treeview.IconProvider;
import be.kuleuven.cs.distrinet.chameleon.workspace.View;

public class JLoEditorExtension extends JavaEditorExtension {


	public JLoEditorExtension() {
	  SUBOBJECT_ICON_PROVIDER = new DefaultIconProvider("subobject", ComponentRelation.class);
	  SINGLE_SUBOBJECT_PARAMETER_ICON_PROVIDER = new DefaultIconProvider("singleformalsubobjectparameter", SingleFormalComponentParameter.class);
	  MULTI_SUBOBJECT_PARAMETER_ICON_PROVIDER = new DefaultIconProvider("multiformalsubobjectparameter", MultiFormalComponentParameter.class);
	  ACTUAL_SUBOBJECT_ARGUMENT_ICON_PROVIDER = new DefaultIconProvider("actualsubobjectargument", InstantiatedMemberSubobjectParameter.class);
	}
	
	@Override
	protected void initializeRegistry() {
		super.initializeRegistry();
		try {
			register("component.png","subobject");
			register("ext_point_obj.gif","singleformalsubobjectparameter");
			register("ext_points_obj.gif","multiformalsubobjectparameter");
			register("extension_obj.gif","actualsubobjectargument");
			register("plugin_obj.gif","plugin");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void register(String fileName, String iconName) throws MalformedURLException {
		register(fileName, iconName, Bootstrapper.PLUGIN_ID);
	}


  @Override
	public CompositeIconProvider createIconProvider(){
  	CompositeIconProvider result = super.createIconProvider();
  	// Members are treated last. Remove and add back at the end.
  	result.remove(MEMBER_ICON_PROVIDER);
  	result.add(SUBOBJECT_ICON_PROVIDER);
  	result.add(SINGLE_SUBOBJECT_PARAMETER_ICON_PROVIDER);
  	result.add(MULTI_SUBOBJECT_PARAMETER_ICON_PROVIDER);
  	result.add(ACTUAL_SUBOBJECT_ARGUMENT_ICON_PROVIDER);
  	result.add(MEMBER_ICON_PROVIDER);
  	return result;
	}

  public final IconProvider SUBOBJECT_ICON_PROVIDER;
  public final IconProvider SINGLE_SUBOBJECT_PARAMETER_ICON_PROVIDER;
  public final IconProvider MULTI_SUBOBJECT_PARAMETER_ICON_PROVIDER;
  public final IconProvider ACTUAL_SUBOBJECT_ARGUMENT_ICON_PROVIDER;
  
  @Override
  public JLoOutlineSelector createOutlineSelector() {
  	return new JLoOutlineSelector();
  }


	@Override
	public String pluginID() {
		return Bootstrapper.PLUGIN_ID;
	}
	
	@Override
	public JLoEditorExtension clone() {
		return new JLoEditorExtension();
	}

	@Override
	public File buildDirectory(File projectRoot) {
		return new File(projectRoot.getAbsolutePath()+File.separator+"java");
	}
	
	@Override
	public void initialize(View view) {
		new JLoBuilder(view);
	}
}
