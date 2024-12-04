package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[][] tabuleiro = {
                //0,1,2,3,4,5,6,7,8,8,10,11
                {0,0,0,0,0,0,0,0,0,0,0,0},//0
                {0,1,1,1,0,1,1,1,1,1,1,0},//1
                {0,1,0,1,0,1,0,0,0,0,1,0},//2
                {0,0,0,1,0,1,1,1,1,0,1,0},//3
                {0,1,1,1,1,0,0,0,1,0,1,0},//4
                {0,0,0,0,1,0,1,0,1,0,1,0},//5
                {0,1,1,0,1,0,1,0,1,0,1,0},//6
                {0,0,1,0,1,0,1,0,1,0,1,0},//7
                {0,1,1,1,1,1,1,1,1,0,1,0},//8
                {0,0,0,0,0,0,1,0,0,0,1,0},//9
                {3,1,1,1,1,1,1,0,1,1,1,0},//10
                {0,0,0,0,0,0,0,0,0,0,0,0}//11
        };
        int[] inicio = {0, 0};
        int[] objetivo = {3, 3};

        Resultado resultado = buscaHillClimbing(tabuleiro, inicio, objetivo);
        System.out.println("Caminho: ");
        resultado.caminho.forEach(i->{
            for (int i1 : i) {
                System.out.print(i1 + " ");
            }
            System.out.println("\n");
        });
        System.out.println("Sucesso: " + resultado.sucesso);
    }

    public static Resultado buscaHillClimbing(int[][] tabuleiro, int[] inicio, int[] objetivo) {
        int[] posicaoAtual = inicio;
        List<int[]> caminho = new ArrayList<>();
        caminho.add(posicaoAtual);
        int linhas = tabuleiro.length;
        int colunas = tabuleiro[0].length;

        while (!iguais(posicaoAtual, objetivo)) {
            int linha = posicaoAtual[0];
            int coluna = posicaoAtual[1];
            int[][] vizinhos = {
                    {linha - 1, coluna},
                    {linha + 1, coluna},
                    {linha, coluna - 1},
                    {linha, coluna + 1}
            };

            List<int[]> vizinhosValidos = new ArrayList<>();
            for (int[] vizinho : vizinhos) {
                if (vizinho[0] >= 0 && vizinho[0] < linhas && vizinho[1] >= 0 && vizinho[1] < colunas && tabuleiro[vizinho[0]][vizinho[1]] == 1) {
                    vizinhosValidos.add(vizinho);
                }
            }

            int[] proximo = null;
            int melhorHeuristica = Integer.MAX_VALUE;
            for (int[] vizinho : vizinhosValidos) {
                int h = distanciaManhattan(vizinho, objetivo);
                if (h < melhorHeuristica) {
                    melhorHeuristica = h;
                    proximo = vizinho;
                }
            }

            if (proximo == null || melhorHeuristica >= distanciaManhattan(posicaoAtual, objetivo)) {
                System.out.println("Busca falhou: nenhum vizinho melhora a heurística.");
                return new Resultado(caminho, false);
            }

            posicaoAtual = proximo;
            caminho.add(posicaoAtual);
        }

        return new Resultado(caminho, true);
    }

    public static int distanciaManhattan(int[] p1, int[] p2) {
        return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
    }

    public static boolean iguais(int[] p1, int[] p2) {
        return p1[0] == p2[0] && p1[1] == p2[1];
    }

    static class Resultado {
        List<int[]> caminho;
        boolean sucesso;

        Resultado(List<int[]> caminho, boolean sucesso) {
            this.caminho = caminho;
            this.sucesso = sucesso;
        }
    }
}
