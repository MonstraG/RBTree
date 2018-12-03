package com.company;

import java.util.Scanner;
import static java.lang.System.out;

public class Program {
    private final Scanner scan;
    private String[] action;

    public Program() {
        scan = new Scanner(System.in);
        action = new String[]{
                "добавить элемент",
                "добавить алфавит (A-Z)",
                "удалить элемент",
                "удалить все элементы",
                "найти элемент",
                "выписать все элементы",
                "выйти"
        };
        boolean exit = false;
        while (!exit) {
            showChoises();
            int choice = scan.nextInt() - 1;
            switch (choice) {
                case 0:
                    out.println("Введите элемент");
                    try {
                        Tree.addNode(scan.next().charAt(0));
                        out.println("Элемент добавлен");
                    } catch (Exception e) { out.println(e.toString()); }
                    break;
                case 1:
                    try {
                        Tree.addAlphabet();
                        out.println("Алфавит добавлен");
                    } catch (Exception e) { e.printStackTrace(); }
                    break;
                case 2:
                    out.println("Введите элемент, который нужно удалить");
                    Tree.removeNode(scan.next().charAt(0));
                    break;
                case 3:
                    Tree.removeAll();
                    out.println("Все элементы были удалены");
                    break;
                case 4:
                    out.println("Введите элемент");
                    Node res = Tree.find(scan.next().charAt(0));
                    if (res == null) {
                        out.println("Такого нет");
                    } else {
                        out.println(res.toString());
                    }
                    break;
                case 5:
                    out.println(Tree.serialize());
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    break;
            }
        }
    }

    private void showChoises() {
        out.println("Что вы хотите сделать?");
        for (int i = 0; i < action.length; i++)
            out.println(" " + (i + 1) + ". " + action[i]);
    }
    public static void main(String[] args) {
        Program instance = new  Program();
    }
}
