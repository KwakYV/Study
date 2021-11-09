package ru.geekbrains.lesson7;

import java.util.Objects;

public class Vertex {
    private final String label;
    private boolean isVisited;
    private boolean isMarked;
    private int markValue;
    private boolean isTarget;

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public Vertex(String label) {
        this.label = label;
        this.markValue = Integer.MAX_VALUE;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vertex vertex = (Vertex) o;
        return Objects.equals(label, vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return "Vertex{" + "label='" + label + '\'' + '}';
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public int getMarkValue() {
        return markValue;
    }

    public void setMarkValue(int markValue) {
        this.markValue = markValue;
    }

    public void setTarget(boolean target) {
        isTarget = target;
    }

    public boolean isTarget() {
        return isTarget;
    }
}
