package me.mrkimo.libkimo.util;

public class Triplet<Key,Name,Value> {

  private final Key key;
  private final Name name;
  private final Value value;

  public Triplet(Key key,Name name,Value value) {
    this.key = key;
    this.name = name;
    this.value = value;
  }

  public Key getKey() { return key; }
  public Name getName() { return name; }
  public Value getValue() { return value; }

  @Override
  public int hashCode() { return key.hashCode() ^ name.hashCode() ^ value.hashCode(); }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Triplet)) return false;
    Triplet tripleto = (Triplet) o;
    return this.key.equals(tripleto.getKey()) &&
           this.name.equals(tripleto.getName()) && 
           this.value.equals(tripleto.getValue());
  }
}
