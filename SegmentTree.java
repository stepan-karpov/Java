import java.util.ArrayList;

public class SegmentTree {
  Integer size = 1;
  ArrayList<Long> tree = new ArrayList<Long>();
  
  public SegmentTree(Integer n) {
    while (size < n) {
      size *= 2;
    }
    for (int i = 0; i < 2 * size - 1; ++i) {
      tree.add(Long.valueOf(0));
    }
  }

  private void Set(Integer ind, Long value, Integer lx, Integer rx, Integer x) {
    if (rx - lx == 1) {
      tree.set(x, value);
      return;
    }
    Integer m = (lx + rx) / 2;
    if (ind < m) {
      Set(ind, value, lx, m, 2 * x + 1);
    } else {
      Set(ind, value, m, rx, 2 * x + 2);
    }
    tree.set(x, tree.get(2 * x + 1) + tree.get(2 * x + 2));
  }

  public void Set(Integer ind, Integer value) {
    Set(ind, Long.valueOf(value), 0, size, 0);
  }

  public void Set(Integer ind, Long value) {
    Set(ind, Long.valueOf(value), 0, size, 0);
  }

  private Long GetSum(Integer l, Integer r, Integer lx, Integer rx, Integer x) {
    if (l <= lx && rx <= r) {
      return Long.valueOf(tree.get(x));
    }
    if (l >= rx || lx >= r) {
      return Long.valueOf(0);
    }
    Integer m = (lx + rx) / 2;
    Long ans = Long.valueOf(0);
    ans += GetSum(l, r, lx, m, 2 * x + 1);
    ans += GetSum(l, r, m, rx, 2 * x + 2);
    return ans;
  }

  public Long GetSum(Integer l, Integer r) {
    return GetSum(l, r, 0, size, 0);
  }

}
