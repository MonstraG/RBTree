package com.company;
import java.io.PrintWriter;
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
                "показать элемент (показывает поддерево с корнем в этом элементе)",
                "показать все (показывает все дерево)",
                "выйти"
        };
        Boolean exit = false;
        while (!exit) {
            showChoises();
            int choice = scan.nextInt() - 1;
            switch (choice) {
                case 0:
                    out.println("Введите элемент");
                    addNode(scan.next());
                    break;
                case 1:
                    addAlphavite();
                    out.println("Алфавит добавлен");
                    break;
                case 2:
                    out.println("Введите элемент, который нужно удалить");
                    removeNode(scan.next());
                    break;
                case 3:
                    removeAll();
                    out.println("Все элементы были удалены");
                    break;
                case 4:
                    out.println("Введите элемент");
                    find(scan.next());
                    break;
                case 5:
                    out.println("Введите элемент");
                    showElement(scan.next());
                    break;
                case 6:
                    showAll();
                    break;
                case 9:
                    exit = true;
                    break;
            }
            scan.next();
        }

    }

    private void showChoises() {
        out.println("Что вы хотите сделать?");
        for (int i=0; i<action.length; i++)
            out.println(" " + (i + 1) + ". "
                    + action[i]);
    }
    public static void main(String[] args) {
        Program instance = new Program();
    }
}
