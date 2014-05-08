public class TreeC<T extends Comparable<T>> {
	private T info;
	private TreeC<T> left;
	private TreeC<T> right;
	
	//constructor
	public TreeC(T info){
		this.info = info;
		this.left = null;
		this.right = null;
	}

	public int compareTo(Object o)
	{
		// TODO: Implement this method
		return 0;
	}
	
}
