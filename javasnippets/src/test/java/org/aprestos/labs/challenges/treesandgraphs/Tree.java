package org.aprestos.labs.challenges.treesandgraphs;

public class Tree {

  public int data;
  public Tree parent;
  public Tree left;
  public Tree right;
  
  public Tree() {}
  
  public Tree(int data) {
    this.data = data;
  }
  
  public Tree(int data, Tree parent, Tree left, Tree right) {
    this.data = data;
    this.parent = parent;
    this.left = left;
    this.right = right;
  }
  
  
  public static Tree search(Tree root, int data) {
    if(null == root)
      return null;
    
    if(root.data == data)
      return root;
    
    if( data < root.data )
      return search(root.left, data);
    else
      return search(root.right, data);
  }
  
  public static Tree minimum(Tree root) {
    if( null == root )
      return null;
    
    Tree min = root;
    while( min.left != null )
      min = min.left;
    
    return min;
  }
  
  public static Tree maximum(Tree root) {
    if( null == root )
      return null;
    
    Tree max = root;
    while( max.right != null )
      max = max.right;
    
    return max;
  }
  
  public static void traverse(Tree root) {
    
    if(null != root) {
      traverse(root.left);
      System.out.println(root.data);
      traverse(root.right);
    }
    
  }
  
  
  public static void insert(Tree node, int data, Tree parent) {
    
    if(null == node) {
      Tree n = new Tree();
      n.data = data;
      n.parent = parent;
      if(data > parent.data)
        parent.right = n;
      else
        parent.left = n;
      return;
    }
    
    if( data < node.data )
      insert( node.left, data, node );
    else
      insert( node.right, data, node );
    
  }
  
  public static void delete(Tree root, int data) {
    
    if(null == root)
      return;
    
    Tree n = search(root,data);
    if( null == n.left && null == n.right ) {
      if( n.parent.left.data == n.data )
        n.parent.left = null;
      else
        n.parent.right = null;
    }
    else if( null == n.left ) {
      if( n.parent.left.data == n.data )
        n.parent.left = n.right;
      else
        n.parent.right = n.right;
    }
    else if( null == n.right ) {
      if( n.parent.left.data == n.data )
        n.parent.left = n.left;
      else
        n.parent.right = n.left;
    }
    else {
      if( n.parent.left.data == n.data ) {
        n.parent.left = n.left;
        insert(n.parent.left, n.right.data, n.parent);
      }
      else {
        n.parent.right = n.left;
        insert(n.parent.right, n.right.data, n.parent);
      }
    }
    
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + data;
    result = prime * result + ((left == null) ? 0 : left.hashCode());
    result = prime * result + ((right == null) ? 0 : right.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Tree other = (Tree) obj;
    if (data != other.data)
      return false;
    if (left == null) {
      if (other.left != null)
        return false;
    } else if (!left.equals(other.left))
      return false;
    if (right == null) {
      if (other.right != null)
        return false;
    } else if (!right.equals(other.right))
      return false;
    return true;
  }


  
  

}
