package be.kuleuven.cs.distrinet.jlo.ant;

import java.io.File;

import org.apache.tools.ant.Task;

public abstract class AbstractTask extends Task {

	/**
	 * Set the configuration file of the Chameleon project. Typically
	 * this file will be named 'project.xml'.
	 * @param config
	 */
	public void setConfig(File config) {
		_config = config;
	}
	
	/**
	 * Return the 
	 * @return
	 */
	public File getConfig() {
		return _config;
	}
	
	private File _config;
	

}
