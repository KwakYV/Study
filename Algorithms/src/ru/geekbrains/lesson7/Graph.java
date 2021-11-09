package ru.geekbrains.lesson7;

public interface Graph {

    void addVertex(String label);
    void addVertex(String label, boolean isTarget);
    Vertex getVertex(String label);

    boolean addEdge(String startLabel, String secondLabel, String... others);
    boolean addEdge(String startLabel, String secondLabel);
    boolean addEdge(String startLabel, String secondLabel, int weight);

    int getSize();

    void display();

    /**
     * англ. Depth-first search, DFS
     */
    void dfs(String startLabel);

    /**
     * англ. breadth-first search, BFS
     */
    void bfs(String startLabel);

    void shortPath(String startLabel);


}
