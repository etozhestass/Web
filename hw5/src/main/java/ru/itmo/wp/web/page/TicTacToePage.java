package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        State state;
        HttpSession session = request.getSession();
        if (request.getParameter("newGame") != null) {
            state = new State();
        } else {
            state = getState(session);
        }
        if (state.phase == Phase.RUNNING && state.makeMove(request)) {
            state.phase = state.checkBoard();
            state.crossesMove = !state.crossesMove;
        }
        view.put("state", state);
        session.setAttribute("TicTacToeState", state);
    }

    private State getState(HttpSession session) {
        State state = (State) session.getAttribute("TicTacToeState");
        if (state == null) {
            state = new State();
        }
        return state;
    }

    public enum Phase {
        RUNNING, WON_X, WON_O, DRAW
    }

    public enum Cell {
        X, O
    }

    public static class State {
        private final int size = 3;
        private final Cell[][] cells;
        private int occupied;
        private Phase phase;
        private boolean crossesMove;

        public State() {
            this.cells = new Cell[this.size][this.size];
            this.occupied = 0;
            this.phase = Phase.RUNNING;
            this.crossesMove = true;
        }
        

        public boolean makeMove(HttpServletRequest request) {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (request.getParameter("cell_" + row + col) != null && cells[row][col] == null) {
                        cells[row][col] = crossesMove ? Cell.X : Cell.O;
                        occupied++;
                        return true;
                    }
                }
            }
            return false;
        }

        public Phase checkBoard() {
            Phase res = checkLine(false);
            if (res != null) {
                return res;
            }
            res = checkLine(true);
            if (res != null) {
                return res;
            }
            res = checkDiag(false);
            if (res != null) {
                return res;
            }
            res = checkDiag(true);
            if (res != null) {
                return res;
            }
            if (occupied == size * size) {
                return Phase.DRAW;
            }
            return Phase.RUNNING;
        }

        private Phase checkDiag(boolean isRight) {
            Cell firstCell = isRight ? cells[0][2] : cells[0][0];
            int i = 1;
            if (firstCell != null) {
                while (i < size) {
                    Cell currCell = isRight ? cells[i][size - 1 - i] : cells[i][i];
                    if (!firstCell.equals(currCell)) {
                        break;
                    }
                    i++;
                }
                if (i == 3) {
                    return getWinner(firstCell);
                }
            }
            return null;
        }

        private Phase checkLine(boolean isRow) {
            Cell firstCell;
            boolean ended;
            for (int i = 0; i < size; i++) {
                firstCell = isRow ? cells[i][0] : cells[0][i];
                ended = true;
                if (firstCell == null) {
                    continue;
                }
                for (int j = 1; j < size; j++) {
                    Cell currCell = isRow ? cells[i][j] : cells[j][i];
                    if (!firstCell.equals(currCell)) {
                        ended = false;
                        break;
                    }
                }
                if (ended) {
                    return getWinner(firstCell);
                }
            }
            return null;
        }

        private Phase getWinner(Cell cell) {
            return cell.equals(Cell.X) ? Phase.WON_X : Phase.WON_O;
        }

        public int getSize() {
            return size;
        }

        public Cell[][] getCells() {
            return cells;
        }

        public Phase getPhase() {
            return phase;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }
    }
}
