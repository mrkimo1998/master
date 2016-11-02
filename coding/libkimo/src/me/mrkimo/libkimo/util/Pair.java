package me.mrkimo.libkimo.util;

public class Pair<Key,Value> {

  private final Key key;
  private final Value value;

  public Pair(Key key, Value value) {
    this.key = key;
    this.value = value;
  }

  public Key getKey() { return key; }
  public Value getValue() { return value; }

  @Override
  public int hashCode() { return key.hashCode() ^ value.hashCode(); }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Pair)) return false;
    Pair pairo = (Pair) o;
    return this.key.equals(pairo.getKey()) &&
           this.value.equals(pairo.getValue());
  }
}
