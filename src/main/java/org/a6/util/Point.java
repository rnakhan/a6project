package org.a6.util;

public class Point {
  private int x;
  private int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Point))
      return false;
    Point p = (Point) o;
    return p.x == this.x && p.y == this.y;
  }

  @Override
  public int hashCode() {
    return x * 31 + y;
  }
}