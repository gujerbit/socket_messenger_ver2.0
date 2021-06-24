package model;

public abstract class Receive extends Default {

	protected abstract void protocolRead(String message);
	
	protected abstract void update();
	
}
