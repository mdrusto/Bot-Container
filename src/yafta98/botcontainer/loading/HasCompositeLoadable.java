package yafta98.botcontainer.loading;

public interface HasCompositeLoadable extends HasLoadable {
	
	@Override
	public CompositeLoadable getLoadable();
}
