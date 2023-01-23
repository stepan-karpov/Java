import java.util.ArrayList;
import java.util.Scanner;

public class Solve {
  static public class NaiveTree {
    static public class Node {
      Long value = Long.valueOf(0);
      Integer left_child = -1;
      Integer right_child = -1;
      public Node(Long value) {
        this.value = value;
      }
      public Node(Integer value) {
        this.value = Long.valueOf(value);
      }
    }
  
    ArrayList<Node> tree = new ArrayList<Node>();
  
    public Boolean Find(Integer value) {
      return Find(Long.valueOf(value));
    }
  
    private Boolean Find(Long value, Integer x) {
      Node cur_x = tree.get(x);
      if (value == cur_x.value) {
        return true;
      }
      if (value < cur_x.value) {
        if (cur_x.left_child == -1) {
          return false;
        }
        return Find(value, cur_x.left_child);
      }
      if (cur_x.right_child == -1) {
        return false;
      }
      return Find(value, cur_x.right_child);
    }
  
    public Boolean Find(Long value) {
      if (tree.isEmpty()) {
        return false;
      }
      return Find(value, 0);
    }
  
    private void Add(Long value, Integer x) {
      Node cur_x = tree.get(x);
      if (cur_x.value == value) {
        return;
      }
      if (value < cur_x.value) {
        if (cur_x.left_child == -1) {
          tree.add(new Node(value));
          cur_x.left_child = tree.size() - 1;
          return;
        }
        Add(value, cur_x.left_child);
        return;
      }
      if (cur_x.right_child == -1) {
        tree.add(new Node(value));
        cur_x.right_child = tree.size() - 1;
        return;
      }
      Add(value, cur_x.right_child);
    }
  
    public void Add(Integer value) {
      Add(Long.valueOf(value));
    }
  
    public void Add(Long value) {
      if (tree.isEmpty()) {
        tree.add(new Node(value));
        return;
      }
      Add(value, 0);
    }
  
    private Long Next(Long value, Integer x) {
      if (x == -1) {
        return Long.valueOf(-1);
      }
      Node cur_x = tree.get(x);
      if (cur_x.value >= value) {
        Long new_ans = Next(value, cur_x.left_child);
        return (new_ans == -1) ? cur_x.value : new_ans;
      }
      return Next(value, cur_x.right_child);
    }
  
    public Long Next(Integer value) {
      return Next(Long.valueOf(value));
    }
  
    public Long Next(Long value) {
      if (tree.isEmpty()) {
        return Long.valueOf(-1);
      }
      return Next(value, 0);
    }
  }
  
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Long MOD = Long.valueOf(1000000000);
    Integer n;
    NaiveTree a = new NaiveTree();

    n = sc.nextInt();
    Long y = Long.valueOf(0);

    for (int i = 0; i < n; ++i) {
      String cmd;
      cmd = sc.next();
      if (cmd.equals("+")) {
        Long value = sc.nextLong();
        a.Add((value + y) % MOD);
        y = Long.valueOf(0);
      } else {
        Long value = sc.nextLong();
        y = a.Next(value);
        System.out.println(y);
      }
    }

  }
}