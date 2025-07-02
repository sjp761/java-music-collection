package application;

public class BinaryTree 
{
	private TreeNode root;
	
	public BinaryTree() 
	{ 
		root = null; 
	}
	
	public boolean isEmpty()
	{
		return root == null;
	}
	

	public void insert(String value) //Public and private method seperated to keep things cleaner
	{
		root = insert(value, root);
	}
	
	private TreeNode insert(String value, TreeNode node)
	{
		if (node == null) // base case
			node = new TreeNode(value);
		else if (value.compareTo(node.data) < 0) //Comparator for the string
			node.left = insert(value, node.left);
		else if (value.compareTo(node.data) > 0)
			node.right = insert(value, node.right);
		else
			node.count++;
		
		return node;
	}
	

	private String inorder(TreeNode node) 
	{
		if (node == null) 
		{
			return "";
		}
		
		return inorder(node.left)
			+ node.data + "\n"
			+ inorder(node.right);
	}	

	public String inorder() 
	{
		return inorder(root);
	}


	private String preorder(TreeNode node) 
	{
		if (node == null) {
			return "";
		}
		return node.data + "\n"
			+ preorder(node.left)
			+ preorder(node.right);
	}

	public String preorder() 
	{
		return preorder(root);
	}

	private String postorder(TreeNode node) 
	{
		if (node == null) {
			return "";
		}
		return postorder(node.left)
			+ postorder(node.right)
			+ node.data + "\n";
	}

	public String postorder() 
	{
		return postorder(root);
	}
	
}
