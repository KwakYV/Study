package ru.geekbrains.lesson7;

public class Main {
    public static void main(String[] args) {
//        testGraph();
//        testDfs();
        testBfs();
    }

    private static void testGraph() {
        Graph graph = new GraphImpl(4);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", "C");
        graph.addEdge("B", "C", "D");
        graph.addEdge("C", "A", "B", "D");
        graph.addEdge("D", "B", "C");

        System.out.println("Size of graph is " + graph.getSize());
        graph.display();
    }

    private static void testDfs() {
        Graph graph = new GraphImpl(7);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");

        graph.addEdge("A", "B", "C", "D");
        graph.addEdge("B", "E");
        graph.addEdge("D", "F");
        graph.addEdge("F", "G");

        graph.dfs("A");
    }

    private static void testBfs() {
        Graph graph = new GraphImpl(10);
        graph.addVertex("Москва");
        graph.addVertex("Тула");
        graph.addVertex("Липецк");
        graph.addVertex("Рязань");
        graph.addVertex("Тамбов");
        graph.addVertex("Воронеж", true);
        graph.addVertex("Саратов");
        graph.addVertex("Калуга");
        graph.addVertex("Орел");
        graph.addVertex("Курск");

        graph.addEdge("Москва", "Тула", 4);
        graph.addEdge("Москва", "Рязань", 7);
        graph.addEdge("Москва", "Калуга", 6);

        graph.addEdge("Тула", "Липецк", 5);
        graph.addEdge("Рязань", "Тамбов", 8);
        graph.addEdge("Калуга", "Орел", 3);

        graph.addEdge("Липецк", "Воронеж", 20);
        graph.addEdge("Тамбов", "Саратов", 9);
        graph.addEdge("Орел", "Курск", 2);

        graph.addEdge("Саратов", "Воронеж", 10);
        graph.addEdge("Курск", "Воронеж", 8);

//        graph.bfs("Москва");
        graph.shortPath("Москва");
        System.out.println(String.format("The shortest path to Воронеж is %d", graph.getVertex("Воронеж").getMarkValue()));

    }
}
