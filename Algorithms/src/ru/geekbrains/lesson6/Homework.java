package ru.geekbrains.lesson6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Homework {
    public static void main(String[] args) {
        List<Tree<Integer>> listOfTrees = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Tree<Integer> tree = new TreeImpl<>();
            ((TreeImpl) tree).setLevelLimit(5); // Не более 5 уровней, сама 5 исключается -> 4 уровня, корень будем считать за 1 уровень.
            while (true){
                if (!tree.add(random.ints(-25,26).findFirst().getAsInt())){
                    break;
                }
            }
            listOfTrees.add(tree);
        }

        int count = 0;
        for (Tree<Integer> item : listOfTrees) {
            if (!isBalanced(((TreeImpl)item).getRoot())){
                count++;
            }
        }
        System.out.println(count*100/20);

    }


    public static boolean isBalanced(Node<Integer> node) {
        return (node == null) ||
                isBalanced(node.getLeftChild()) &&
                        isBalanced(node.getRightChild()) &&
                        Math.abs(height(node.getLeftChild()) - height(node.getRightChild())) <= 1;
    }

    private static int height(Node<Integer> node) {
        return node == null ? 0 : 1 + Math.max(height(node.getLeftChild()), height(node.getRightChild()));
    }
}
