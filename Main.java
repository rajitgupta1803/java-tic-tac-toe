import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Scanner inp = new Scanner(System.in);
    static String[][] board = {
            {"_", "_", "_"},
            {"_", "_", "_"},
            {"_", "_", "_"}
    };
    static String[] rows = {"a", "b", "c"};
    static String[] cols = {"1", "2", "3"};
    static String player_char = "X";
    static String comp_char = "O";
    static String curr_player;

    public static void main(String[] args) {
        introText();
        first_play(curr_player);
        do {
            draw_board();
            if (check_winner().equals("won")) {
                System.out.printf("%s won!", curr_player);
                break;
            } else if (check_winner().equalsIgnoreCase("tie")) {
                System.out.println("Match Tied! no winner :(");
                break;
            }
            change_player(curr_player);
        }
        while (true);
    }

    static String[] play_turn() {
        System.out.print("Type the coordinates: ");
        String move = inp.next();
        return move.split("");
    }

    static void edit_board(String[] player_coord) {
        int row_coordinate = switch (player_coord[0]) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            default -> -1;
        };
        try {
            if (board[row_coordinate][Integer.parseInt(player_coord[1]) - 1].equals("_")) {
                board[row_coordinate][Integer.parseInt(player_coord[1]) - 1] = player_char;
            } else {
                throw new Exception("not empty");
            }

        } catch (Exception e) {
            if (e.getMessage().equals("not empty"))
                System.out.println("That coordinate is not empty, try somewhere else -_-");
            else System.out.println("These coordinates are not valid");

            edit_board(play_turn());
        }

    }

    static void draw_board() {
        for (String[] i : board) {
            for (String k : i) {
                System.out.print(k + " | ");
            }
            System.out.println(" ");
        }
    }

    static String check_winner() {
        //rows
        for (String[] row : board) {
            if (!Arrays.asList(row).contains("_")) {
                if (row[0].equals(row[1]) && row[1].equals(row[2])) {
                    return "won";
                }
            }
        }
        //columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i])) {
                if (!board[0][i].equals("_")) {
                    return "won";
                }
            }
        }

        //diagonals left top to right bottom
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
            if (!board[0][0].equals("_")) {
                return "won";
            }
        }
        //diagonals right top to left bottom
        else if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
            if (!board[0][2].equals("_")) {
                return "won";
            }
        }

        if (!Arrays.asList(board[0]).contains("_") && !Arrays.asList(board[1]).contains("_") && !Arrays.asList(board[2]).contains("_")) {
            return "Tie";
        }
        return "not won";

    }

    static void introText() {
        System.out.println(
                "To play, type the row and then column coordinate of the cell " +
                        "\nThe coordinates work like this \n" +
                        "   1    2    3\n" +
                        "a | |  | |  | |\n" +
                        "b | |  | |  | |\n" +
                        "c | |  | |  | |\n" +
                        "For eg, if u wanted to place your symbol in the top left cell, you have to type a1\n\n");
    }

    static void change_player(String player) {
        if (player.equals("You")) {
            curr_player = "computer";
            System.out.printf("\n%s will play now%n", curr_player);
            computer_player();
        } else if (player.equals("computer")) {
            curr_player = "You";
            System.out.printf("\n%s will play now%n", curr_player);
            edit_board(play_turn());
        }
    }

    static void first_play(String current_player) {
        String[] players = {"computer", "You"};
        int rand_ind = new Random().nextInt(players.length);
        current_player = players[rand_ind];

        if (current_player.equals("computer")) {
            curr_player = "computer";
            System.out.printf("%s will play now%n", curr_player);
            computer_player();
        } else {
            curr_player = "You";
            System.out.printf("%s will play now%n", curr_player);
            draw_board();
            edit_board(play_turn());
        }
    }

    static void computer_player() {
        class check_board {
            public boolean updated = false;

            public void check_row() {
                for (String[] row : board) {
                    if (Arrays.asList(row).contains("_")) {
                        if (Collections.frequency(Arrays.asList(row), comp_char) == 2 || Collections.frequency(Arrays.asList(row), player_char) == 2) {
                            board[Arrays.asList(board).indexOf(row)][Arrays.asList(row).indexOf("_")] = comp_char;
                            updated = true;
                            break;
                        }
                    }
                }
            }

            public void check_col() {
                String[][] cols = new String[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int k = 0; k < 3; k++) {
                        cols[i][k] = board[k][i];
                    }
                }
                for (String[] subcol : cols) {
                    if (Collections.frequency(Arrays.asList(subcol), comp_char) == 2 || Collections.frequency(Arrays.asList(subcol), player_char) == 2) {
                        if (Arrays.asList(subcol).contains("_")) {
                            board[Arrays.asList(subcol).indexOf("_")][Arrays.asList(cols).indexOf(subcol)] = comp_char;
                            updated = true;
                            break;
                        }
                    }
                }
            }

            public void check_diag() {
                String[] diagonal = {board[0][0], board[1][1], board[2][2]};
                if (Arrays.asList(diagonal).contains("_")) {
                    if (Collections.frequency(Arrays.asList(diagonal), comp_char) == 2 || Collections.frequency(Arrays.asList(diagonal), player_char) == 2) {
                        board[Arrays.asList(diagonal).indexOf("_")][Arrays.asList(diagonal).indexOf("_")] = comp_char;
                        updated = true;
                        return;
                    }
                }
                String[] diagonal_other = {board[0][2], board[1][1], board[2][0]};
                if (Arrays.asList(diagonal_other).contains("_")) {
                    if (Collections.frequency(Arrays.asList(diagonal_other), comp_char) == 2 || Collections.frequency(Arrays.asList(diagonal_other), player_char) == 2) {
                        board[Arrays.asList(diagonal_other).indexOf("_")][2 - Arrays.asList(diagonal_other).indexOf("_")] = comp_char;
                        updated = true;
                    }
                }
            }

            public void rand_move() {
                int rand_col = new Random().nextInt(3), rand_row = new Random().nextInt(3);
                if (board[rand_row][rand_col].equals("_")) {
                    board[rand_row][rand_col] = comp_char;
                } else rand_move();
            }
        }
        check_board c = new check_board();
        if (!c.updated) c.check_row();
        if (!c.updated) c.check_col();
        if (!c.updated) c.check_diag();
        if (!c.updated) c.rand_move();
    }

}
