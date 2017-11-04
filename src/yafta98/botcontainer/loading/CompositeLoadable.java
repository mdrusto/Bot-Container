package yafta98.botcontainer.loading;

import java.util.ArrayList;
import java.util.List;

public class CompositeLoadable extends Loadable {
	
	private List<Loadable> children = new ArrayList<>();
	
	public void addLoadable(Loadable loadable) {
		children.add(loadable);
	}
	
	public List<Loadable> getChildren() {
		return children;
	}
	
	@Override
	public void execute() {
		beforeLoading();
		load();
		afterLoading();
	}
	
	@Override
	public void load() {
		for (Loadable l: children) {
			beforeChild();
			l.execute();
			afterChild();
		}
	}
	
	public void beforeChild() {}
	
	public void afterChild() {}
}
