package be.kuleuven.cs.distrinet.jlo.model.component;


public abstract class SingleActualComponentArgument extends ActualComponentArgument {

	public SingleActualComponentArgument(String name) {
		setName(name);
	}
	
	private String _name;
	
	public String name() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
}
