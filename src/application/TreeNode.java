package application;

public class TreeNode 
{
	public String data;
	public int count = 1;
	
	public TreeNode right, left;
	
	public TreeNode(String value)
	{
		data = value;
		right = left = null;
	}
}
