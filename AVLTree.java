import java.util.ArrayList;

public class AVLTree {
  public class Node {
    Long value = Long.valueOf(0);
    Integer left_child = -1;
    Integer right_child = -1;
    Integer size = 0;
    Integer balance = 0;
    public Node(Long value) {
      this.value = value;
      this.size = 1;
    }
    public Node(Integer value) {
      this.value = Long.valueOf(value);
      this.size = 1;
    }
  }

  ArrayList<Node> tree = new ArrayList<Node>();

  public Boolean Find(Integer value) {
    return Find(Long.valueOf(value));
  }

  private Integer GetBalance(Integer x) {
    if (x == -1) {
      return 0;
    }
    Node cur_x = tree.get(x);
    Integer left_child_size = 0;
    Integer right_child_size = 0;
    if (cur_x.left_child != -1) {
      left_child_size = tree.get(cur_x.left_child).size;
    }
    if (cur_x.right_child != -1) {
      right_child_size = tree.get(cur_x.right_child).size;
    }
    return left_child_size - right_child_size;
  }

  private void SmallLeftRotation(Integer x) {
    Node cur_x = tree.get(x);
    Integer y = cur_x.left_child;
    Node cur_y = tree.get(y);
    Integer A = cur_y.left_child;
    Integer B = cur_y.right_child;
    Integer C = cur_x.right_child;
    Collections.swap(tree, x, y);
    Integer tmp = x; x = y; y = tmp;
    cur_x = tree.get(x);
    cur_y = tree.get(y);
    cur_y.left_child = A;
    cur_y.right_child = x;
    cur_x.left_child = B;
    cur_x.right_child = C;
    Update(x);
    Update(y);
  }

  private void SmallRightRotation(Integer x) {
    Node cur_x = tree.get(x);
    Integer y = cur_x.right_child;
    Node cur_y = tree.get(y);
    Integer A = cur_x.left_child;
    Integer B = cur_y.left_child;
    Integer C = cur_y.right_child;
    Collections.swap(tree, x, y);
    Integer tmp = x; x = y; y = tmp;
    cur_x = tree.get(x);
    cur_y = tree.get(y);
    cur_y.left_child = x;
    cur_y.right_child = C;
    cur_x.left_child = A;
    cur_x.right_child = B;
    Update(x);
    Update(y);
  }

  private void Rebuild(Integer x) {
    Node cur_x = tree.get(x);
    Integer balance = GetBalance(x);
    if (Math.abs(balance) != 2) {
      return;
    }
    if (balance == -2) {
      if (GetBalance(cur_x.right_child) != 1) {
        SmallRightRotation(x);
      } else {
        SmallLeftRotation(cur_x.right_child);
        SmallRightRotation(x);
      }
    } else {
      if (GetBalance(cur_x.left_child) != -1) {
        SmallLeftRotation(x);
      } else {
        SmallRightRotation(cur_x.left_child);
        SmallLeftRotation(x);
      }
    }
  }

  private void Update(Integer x) {
    Integer left_size = 0;
    Integer right_size = 0;
    Node cur_x = tree.get(x);
    if (cur_x.left_child != -1) {
      left_size = tree.get(cur_x.left_child).size;
    }
    if (cur_x.right_child != -1) {
      right_size = tree.get(cur_x.right_child).size;
    }
    cur_x.size = left_size + right_size + 1;
    cur_x.balance = left_size - right_size;
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
        Update(x);
        Rebuild(x);
        return;
      }
      Add(value, cur_x.left_child);
      Update(x);
      Rebuild(x);
      return;
    }
    if (cur_x.right_child == -1) {
      tree.add(new Node(value));
      cur_x.right_child = tree.size() - 1;
      Update(x);
      Rebuild(x);
      return;
    }
    Add(value, cur_x.right_child);
    Update(x);
    Rebuild(x);
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