package fishmaple;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class BinaryTreeNode {
    //二叉树的个种子遍历
    public BinaryTreeNode root;

     BinaryTreeNode(Integer integer){
       this.value=integer;
    }
    public BinaryTreeNode createTree(Integer nt){
         this.root=new BinaryTreeNode(nt);
         return root;
    }


    BinaryTreeNode left;
    BinaryTreeNode right;
        Integer value;

    BinaryTreeNode(){

        }

    private ArrayList<Integer> list=new ArrayList<Integer>(200);

    public void readTree(BinaryTreeNode node,int p){
        for(int i=0;i<100;i++){
            list.add(-1);
        }
        list.set(p,node.value);
        if(node.left!=null){
            readTree(node.left,p*2);
        }
        if(node.right!=null){
            readTree(node.right,p*2+1);
        }

    }
        Queue<Integer> q= new ArrayBlockingQueue<Integer>(20);
    public void getCeng(BinaryTreeNode node){
        Queue<BinaryTreeNode> queue= new ArrayBlockingQueue<>(20);
        queue.add(node);
        while(!queue.isEmpty()){
            node=queue.poll();
            System.out.println(node.value);
            if(node.left!=null){
                queue.add(node.left);
            }
            if(node.right!=null){
                queue.add(node.right);
            }
        }
    }
    public void getList(){
        for(Integer i:list){
            System.out.print(i+" ");
        }
    }
    public void preorder(BinaryTreeNode root){
        if(root!=null){
            System.out.print(root.value+" ");
            preorder(root.left);
            preorder(root.right);
        }

    }
    /*//中序遍历
    public void inorder(BinTree root){
        if(root!=null){
            inorder(root.lChild);
            visit(root.getData());
            inorder(root.rChild);
        }

    }
    //后序遍历
    public void afterorder(BinTree root){
        if(root!=null){
            afterorder(root.lChild);
            afterorder(root.rChild);
            visit(root.getData());
        }

    }*/

    public static void main(String args[]){
        BinaryTreeNode bTree=new BinaryTreeNode(1);
       // bTree=bTree.createTree(1);
        //bTree.createTree(1);
        bTree.left=new BinaryTreeNode(3);
        bTree.right=new BinaryTreeNode(8);
        bTree.left.left=new BinaryTreeNode(4);
        bTree.left.left.left=new BinaryTreeNode(6);
        bTree.left.left.right=new BinaryTreeNode(12);
       // bTree.preorder(bTree);
        //bTree.readTree(bTree,1);
        //bTree.getList();
        bTree.getCeng(bTree);
    }
}
