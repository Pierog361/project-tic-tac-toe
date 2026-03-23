/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.bot;

import org.example.board.Board;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeBot {

    private final float blocking_parameter = 1f;
    private final float wining_parameter = 1f;

    private final char botChar = Board.O;
    private final char plrChar = Board.X;

    private final Board board;
    private int row = 0, column = 0;

    private class Cell {
        int xCoordinate, yCoordinate;
        char cellChar;

        Cell(int x, int y) {
            this.xCoordinate = x;
            this.yCoordinate = y;
            this.cellChar = board.getImage()[y][x];
        }
    }

    private class Option {

        boolean occupied = false;
        Cell cell_1, cell_2, cell_3;

        public Option(Cell cell_1, Cell cell_2, Cell cell_3) {

            this.cell_1 = cell_1;
            this.cell_2 = cell_2;
            this.cell_3 = cell_3;

            if (cell_1.cellChar != Board.EMPTY && cell_2.cellChar != Board.EMPTY && cell_3.cellChar != Board.EMPTY)
                occupied = true;
        }

        public int getWiningBScore() {

            int winingBScore = 0;
            if (cell_1.cellChar == botChar) winingBScore++;
            if (cell_2.cellChar == botChar) winingBScore++;
            if (cell_3.cellChar == botChar) winingBScore++;
            return winingBScore;
        }

        public int getWiningPScore() {

            int winingPScore = 0;
            if (cell_1.cellChar == plrChar) winingPScore++;
            if (cell_2.cellChar == plrChar) winingPScore++;
            if (cell_3.cellChar == plrChar) winingPScore++;
            return winingPScore;
        }

        public Cell getPossibleMove() {
            if (cell_1.cellChar == Board.EMPTY) return cell_1;
            if (cell_2.cellChar == Board.EMPTY) return cell_2;
            if (cell_3.cellChar == Board.EMPTY) return cell_3;
            throw new IllegalStateException("Axis fully occupied!");
        }
    }

    public TicTacToeBot(Board board) {
        this.board = board;
    }

    public void generateMove() throws IllegalStateException{

        List<Option> options = new ArrayList<>();
        options.add(new Option(new Cell(0, 0), new Cell(0, 1), new Cell(0, 2)));
        options.add(new Option(new Cell(1, 0), new Cell(1, 1), new Cell(1, 2)));
        options.add(new Option(new Cell(2, 0), new Cell(2, 1), new Cell(2, 2)));

        options.add(new Option(new Cell(0, 0), new Cell(1, 0), new Cell(2, 0)));
        options.add(new Option(new Cell(0, 1), new Cell(1, 1), new Cell(2, 1)));
        options.add(new Option(new Cell(0, 2), new Cell(1, 2), new Cell(2, 2)));

        options.add(new Option(new Cell(0, 0), new Cell(1, 1), new Cell(2, 2)));
        options.add(new Option(new Cell(2, 0), new Cell(1, 1), new Cell(0, 2)));

        List<Option> availableOptions = new ArrayList<>(options.stream().filter((o) -> !o.occupied).toList());

        availableOptions.sort((o_1, o_2) -> {
            float o_1_score = o_1.getWiningPScore() * blocking_parameter + o_1.getWiningBScore() * wining_parameter;
            float o_2_score = o_2.getWiningPScore() * blocking_parameter + o_2.getWiningBScore() * wining_parameter;

            return Float.compare(o_2_score, o_1_score);
        });

        if (availableOptions.isEmpty()) throw new IllegalStateException("No available moves!");

        Cell finalCell = availableOptions.getFirst().getPossibleMove();
        row = finalCell.xCoordinate;
        column = finalCell.yCoordinate;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
