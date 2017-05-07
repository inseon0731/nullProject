package com.example.hong_inseon.projectlouvre;

import java.util.Stack;

public class Dikstra {
    static int inf = 99999999;


    public static int[] dikstra(int[][] graph, int start, int end) {
        int vCount = graph[0].length; // 정점의 수
        boolean[] isVisits = new boolean[vCount]; // 방문 배열
        int[] distance = new int[vCount]; // 거리배열
        int[] historyPath = new int[vCount]; // 히스토리 배열

        int nextVertex = start; // distance 배열의 최소값의 정점
        int min = 0; // distance 배열의 최소값
        // 초기화
        for (int i = 0; i < vCount; i++) {
            isVisits[i] = false; // 방문 한 곳 없다고 초기화
            distance[i] = inf; // 전부 다 무한대로 초기화
            historyPath[i] = inf; // 전부 다 무한대로 초기화
        }
        distance[start] = 0; // 시작점 0 으로 초기화

        // 다익스트라 실행
        while (true) {
            min = inf; // 최소값을 infinity 초기화
            for (int j = 0; j < vCount; j++) {
                if (isVisits[j] == false && distance[j] < min) { // 가장 먼저 방문했던
                    // 노드는 제외한다,
                    // 또한 최소값을
                    // 찾기위한
                    // 조사(선택정렬을
                    // 생각하면 된다.)
                    nextVertex = j; // 다음으로 이동할 정점 선택
                    min = distance[j]; // 다음으로 이동한 최소값
                }
            }
            if (min == inf)
                break; // 최소값이 infinity이면 모든 정점을 지났다는 것, 최소값이 모든 정점을 지났으면
            // infinity
            isVisits[nextVertex] = true; // 다음으로 이동할 정점 방문

            for (int j = 0; j < vCount; j++) {
                int distanceVertex = distance[nextVertex]
                        + graph[nextVertex][j]; // 정점에서 방문한 다른 정점의 거리
                if (distance[j] > distanceVertex) // 정점에서 다른 정점에서의 거리가 distance
                // 배열보다 적다면 교체해 준다.
                {
                    distance[j] = distanceVertex; // 교체해 준다.
                    historyPath[j] = nextVertex; // 교체 된다면 그 지점의 정점을 기록을 남긴다.
                }
            }
        }

        return  gp(historyPath, start, end);
    }

    public static void printPath(int[] historyPath, int start, int end) { // 경로계산
        Stack<Integer> path = new Stack<Integer>();
        int vertex = end; // 거꾸로 탐색한다.
        while (true) {
            path.push(vertex);
            if (vertex == start)
                break; // 시작점이면 리턴한다.
            vertex = historyPath[vertex]; // 탐색
        }

        System.out.print(start + " 부터 " + end + " 까지 경로는 : ");
        while (!path.isEmpty())
            // 출력
            System.out.print(" " + path.pop());
    }

    public static int [] gp(int[] hp, int start, int end) {
        int[] getP;
        if(end != -1) {
            Stack<Integer> path = new Stack<Integer>();
            int count = 0;
            int vertex = end; // 뒤에서부터 지나온 흔적을 찾아간다.
            while (true) {
                path.push(vertex);
                count++;
                if (vertex == start)
                    break; // 시작점이면 탈출
                vertex = hp[vertex]; // 지나온 정점 탐색
            }

            getP = new int[count];
            int i = 0;
            while (!path.isEmpty()) {
                getP[i] = path.pop();
                i++;
            }
        }
        else
            getP = null;
        return getP;
    }
}