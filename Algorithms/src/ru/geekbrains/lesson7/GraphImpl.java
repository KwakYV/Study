package ru.geekbrains.lesson7;

import java.util.*;

public class GraphImpl implements Graph {

    private final List<Vertex> vertexList;
    private final boolean[][] adjMatrix;
    private final int[][] weightMatrix;

    public GraphImpl(int maxVertexCount) {
        this.vertexList = new ArrayList<>(maxVertexCount);
        this.adjMatrix = new boolean[maxVertexCount][maxVertexCount];
        this.weightMatrix = new int[maxVertexCount][maxVertexCount];
    }

    @Override
    public void addVertex(String label) {
        vertexList.add(new Vertex(label));
    }

    @Override
    public void addVertex(String label, boolean isTarget) {
        Vertex vertex = new Vertex(label);
        vertex.setTarget(true);
        vertexList.add(vertex);
    }

    @Override
    public Vertex getVertex(String label) {
        return vertexList.get(indexOf(label));
    }

    @Override
    public boolean addEdge(String startLabel, String secondLabel) {
        int startIndex = indexOf(startLabel);
        int endIndex = indexOf(secondLabel);

        if (startIndex == -1 || endIndex == -1) {
            return false;
        }

        adjMatrix[startIndex][endIndex] = true;
        return true;
    }

    @Override
    public boolean addEdge(String startLabel, String secondLabel, int weight) {
        int startIndex = indexOf(startLabel);
        int endIndex = indexOf(secondLabel);

        if (startIndex == -1 || endIndex == -1) {
            return false;
        }

        adjMatrix[startIndex][endIndex] = true;
        weightMatrix[startIndex][endIndex] = weight;
        return true;
    }


    private int indexOf(String label) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexList.get(i).getLabel().equals(label)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public boolean addEdge(String startLabel, String secondLabel, String... others) {
        boolean result = addEdge(startLabel, secondLabel);

        for (String other : others) {
            result &= addEdge(startLabel, other);
        }

        return result;
    }

    @Override
    public int getSize() {
        return vertexList.size();
    }

    @Override
    public void display() {
        for (int i = 0; i < getSize(); i++) {
            System.out.print(vertexList.get(i));
            for (int j = 0; j < getSize(); j++) {
                if (adjMatrix[i][j]) {
                    System.out.print(" -> " + vertexList.get(j));
                }
            }
            System.out.println();
        }
    }

    @Override
    public void dfs(String startLabel) {
        int startIndex = indexOf(startLabel);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Неверная вершина: " + startLabel);
        }

        Stack<Vertex> stack = new Stack<>();
        Vertex vertex = vertexList.get(startIndex);

        visitedVertex(stack, vertex);
        while (!stack.isEmpty()) {
            vertex = getNearUnvisitedVertex(stack.peek());
            if (vertex != null) {
                visitedVertex(stack, vertex);
            } else {
                stack.pop();
            }
        }
        System.out.println();
    }

    private Vertex getNearUnvisitedVertex(Vertex vertex) {
        int currentIndex = vertexList.indexOf(vertex);
        for (int i = 0; i < getSize(); i++) {
            if (adjMatrix[currentIndex][i] && !vertexList.get(i).isVisited()) {
                return vertexList.get(i);
            }
        }
        return null;
    }

    private List<Vertex> getNearestMarkedVertexes(Vertex vertex){
        List<Vertex> result = new ArrayList<>();
        int currentIdex = vertexList.indexOf(vertex);
        Vertex child;
        int newMarkedValue = 0;
        for (int i = 0; i < getSize(); i++) {
            if (adjMatrix[currentIdex][i] && !vertexList.get(i).isVisited()){
                child = vertexList.get(i);
                newMarkedValue = vertex.getMarkValue() + getWeight(vertex, child);
                if (child.getMarkValue() > newMarkedValue){
                    child.setMarkValue(newMarkedValue);
                }
                result.add(child);
            }
        }
        return result;
    }

    private void visitedVertex(Stack<Vertex> stack, Vertex vertex) {
        System.out.print(vertex.getLabel() + " ");
        stack.add(vertex);
        vertex.setVisited(true);
    }

    private void visitedVertex(Queue<Vertex> queue, Vertex vertex) {
        System.out.print(vertex.getLabel() + " ");
        queue.add(vertex);
        vertex.setVisited(true);
    }

    @Override
    public void bfs(String startLabel) {
        int startIndex = indexOf(startLabel);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Неверная вершина: " + startLabel);
        }

        Queue<Vertex> queue = new LinkedList<>();
        Vertex vertex = vertexList.get(startIndex);

        visitedVertex(queue, vertex);
        while (!queue.isEmpty()) {
            vertex = getNearUnvisitedVertex(queue.peek());
            if (vertex != null) {
                visitedVertex(queue, vertex);
            } else {
                queue.remove().setVisited(true);
            }
        }
        System.out.println();
    }


    private int getWeight(Vertex from, Vertex to){
        int fromIndex = vertexList.indexOf(from);
        int toIndex = vertexList.indexOf(to);
        return weightMatrix[fromIndex][toIndex];
    }


    public void shortPath(String startLabel){
        int startIndex = indexOf(startLabel);
        if (startIndex == -1) {
            throw new IllegalArgumentException("Неверная вершина: " + startLabel);
        }
        Vertex vertex = vertexList.get(startIndex);
        vertex.setMarkValue(0);
        findPath(vertex);
    }

    private void findPath(Vertex vertex) {
        List<Vertex> lst = getNearestMarkedVertexes(vertex);
        if (lst.isEmpty()){
            return;
        }
        vertex.setVisited(true);
        for (Vertex item : lst) {
            findPath(item);
        }
    }
}
