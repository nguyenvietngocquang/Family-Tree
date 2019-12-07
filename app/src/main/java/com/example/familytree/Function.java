package com.example.familytree;

import java.util.ArrayList;

import de.blox.graphview.Graph;
import de.blox.graphview.Node;

public class Function {

    // Tạo Graph với các Node có quan hệ cha - con
    public static Graph createGraph(String name, ArrayList<Member> list) {
        Graph graph = new Graph();
        Node[] node = new Node[list.size()];
        int start = 1, end = 1, check = -1;

        node[0] = new Node(name);
        Member tempMember = findMember(node[0].getData().toString(), list);
        for (Member member : list) {
            if (member.isMale()) {
                if ((member.getFather() != null && tempMember.getName().equals(member.getFather().getName())) || (tempMember.getFather() != null && member.getName().equals(tempMember.getFather().getName()))) {
                    Node resultNode = new Node(member.getName());
                    if (!contains(resultNode, node)) {
                        node[end] = resultNode;
                        end++;
                    }
                }
            }
        }

        while (check != 0) {
            check = 0;
            int S = start;
            int E = end;
            for (int i = S; i < E; i++) {
                tempMember = findMember(node[i].getData().toString(), list);
                for (Member member : list) {
                    if (member.isMale()) {
                        if ((member.getFather() != null && tempMember.getName().equals(member.getFather().getName())) || (tempMember.getFather() != null && member.getName().equals(tempMember.getFather().getName()))) {
                            Node resultNode = new Node(member.getName());
                            if (!contains(resultNode, node)) {
                                node[end] = resultNode;
                                end++;
                                check++;
                            }
                        }
                    }
                }
                start++;
            }
        }

        Member root = new Member();
        for (int i = 0; i < end; i++) {
            root = findMember(node[i].getData().toString(), list);
            if (root.getFather() != null && root.getFather().getFather() == null) {
                graph.addEdge(findNode(root.getFather().getName(), node), node[i]);
                break;
            }
        }

        for (int i = 0; i < end; i++) {
            tempMember = findMember(node[i].getData().toString(), list);
            if (!tempMember.equals(root) && tempMember.getFather() != null) {
                graph.addEdge(findNode(tempMember.getFather().getName(), node), node[i]);
            }
        }

        return graph;
    }

    // Kiểm tra có tồn tại Node trong Array không?
    public static boolean contains(Node nodeTemp, Node[] node) {
        for (Node tempNode : node) {
            if (tempNode == null || tempNode.getData().toString().equals(""))
                return false;
            else if (tempNode.getData().toString().equals(nodeTemp.getData().toString()))
                return true;
        }
        return false;
    }

    // Tìm Node trong Array theo tên
    public static Node findNode(String name, Node[] node) {
        for (Node tempNode : node) {
            if (tempNode == null || tempNode.getData().toString().equals(""))
                return null;
            if (tempNode.getData().toString().equals(name))
                return tempNode;
        }
        return null;
    }

    // Tìm Member trong ArrayList theo tên
    public static Member findMember(String name, ArrayList<Member> list) {
        for (Member member : list) {
            if (member.getName().equals(name))
                return member;
        }
        return null;
    }

    // Chuyển chuỗi tên theo dạng chuẩn chữ hoa đầu từng từ
    public static String convertName(String name) {
        if (name.equals(""))
            return "";
        name = name.toLowerCase();
        String[] list = name.split(" ");
        String result = new String("");
        for (String temp : list) {
            temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
            result += temp + " ";
        }
        return result.trim();
    }

}