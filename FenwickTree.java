import java.util.ArrayList;

public class FenwickTree {
  Integer n;
  ArrayList<Long> tree = new ArrayList<Long>();

  public FenwickTree(Integer n) {
    for (int i = 0; i < n; ++i) {
      tree.add(Long.valueOf(0));
    }
    this.n = n;
  }

  private Integer f(Integer i) {
    return i & (i + 1);
  }
  private Integer g(Integer i) {
    return i | (i + 1);
  }

  public void Set(Integer ind, Integer value) {
    Set(ind, Long.valueOf(value));
  }

  public void Set(Integer ind, Long value) {
    for (int i = ind; i < n; i = g(i)) {
      tree.set(i, tree.get(i) + value);
    }
  }

  private Long GetSum(Integer r) {
    Long ans = Long.valueOf(0);
    for (int i = r; i >= 0; i = f(i) - 1) {
      ans += tree.get(i);
    }
    return ans;
  }

  public Long GetSum(Integer l, Integer r) {
    return GetSum(r) - GetSum(l - 1);
  } 
}
