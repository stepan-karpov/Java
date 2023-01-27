import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Random;

public class Solve {
  static public class CartesianTree {
    Node root = null;
    static Integer kNoSon = 1000000001;
    static public class Node {
      Integer value;
      Integer key;
      Node left_son = null;
      Node right_son = null;
      public Node(Integer value) {
        this.value = value;
        Random rand = new Random();
        key = rand.nextInt(100000);
      }
    }
    
    static public class Splitter {
      Node left = null;
      Node right = null;
      public Splitter(Node left, Node right) {
        this.left = left;
        this.right = right;
      }
    }

    // returns Splitter:
    // left - root of a tree where all elements are < value
    // right - root of a tree where all elements are => value
    private Splitter Split(Node x, Integer value) {
      if (x == null) {
        return new Splitter(null, null);
      }
      if (x.value < value) {
        Splitter current_splitter = Split(x.right_son, value);
        x.right_son = current_splitter.left;
        return new Splitter(x, current_splitter.right);
      }
      Splitter current_splitter = Split(x.left_son, value);
      x.left_son = current_splitter.right;
      return new Splitter(current_splitter.left, x);
    }

    private Node Merge(Node left, Node right) {
      if (left == null) {
        return right;
      }
      if (right == null) {
        return left;
      }
      if (right.key > left.key) {
        right.left_son = Merge(left, right.left_son);
        return right;
      }
      left.right_son = Merge(left.right_son, right);
      return left;
    }

    public void Insert(Integer value) {
      if (Find(value)) {
        return;
      }
      Splitter current_splitter = Split(root, value);
      Node new_element = new Node(value);

      current_splitter.right = Merge(new_element, current_splitter.right);
      root = Merge(current_splitter.left, current_splitter.right);
    }

    private Boolean Find(Node root, Integer value) {
      if (root == null) {
        return false;
      }
      if (root.value == value) {
        return true;
      }
      if (root.value < value) {
        return Find(root.right_son, value);
      }
      return Find(root.left_son, value);
    }

    public Boolean Find(Integer value) {
      return Find(root, value);
    }

    public void Delete(Integer value) {
      if (!Find(value)) {
        return;
      }
      Splitter splitter1 = Split(root, value);
      Splitter splitter2 = Split(splitter1.right, value + 1);

      root = Merge(splitter1.left, splitter2.right);
    }

    private Integer UpperBound(Node root, Integer value, Boolean reverse) {
      if (!reverse) {
        if (root == null) {
          return kNoSon;
        }
        if (root.value > value) {
          return Math.min(root.value, UpperBound(root.left_son, value, reverse));
        }
        return UpperBound(root.right_son, value, reverse);
      }
      if (root == null) {
        return kNoSon;
      }
      if (root.value < value) {
        Integer ans = UpperBound(root.right_son, value, reverse);
        if (ans == kNoSon) {
          return root.value;
        }
        return Math.max(root.value, ans);
      }
      return UpperBound(root.left_son, value, reverse);
    }

    // returns first element > value if reverse = false
    // returns first element < value if reverse = true
    public Integer UpperBound(Integer value, Boolean reverse) {
      return UpperBound(root, value, reverse);
    }

  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Integer n;
    CartesianTree a = new CartesianTree();

    while (sc.hasNextLine()) {
      String cmd = sc.next();
      Integer value = sc.nextInt();
      if (cmd.compareTo("insert") == 0) {
        a.Insert(value);
      } else if (cmd.compareTo("exists") == 0) {
        if (a.Find(value)) {
          System.out.println("true");
        } else {
          System.out.println("false");
        }
      } else if (cmd.compareTo("delete") == 0) {
        a.Delete(value);
      } else if (cmd.compareTo("next") == 0) {
        Integer ans = a.UpperBound(value, false);
        if (ans == a.kNoSon) {
          System.out.println("none");
        } else {
          System.out.println(ans);
        }
      } else if (cmd.compareTo("prev") == 0) {
        Integer ans = a.UpperBound(value, true);
        if (ans == a.kNoSon) {
          System.out.println("none");
        } else {
          System.out.println(ans);
        }
      }
    }
  }
    

}