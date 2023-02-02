import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> dirtyRooms = initRooms();
        String vacuumLocation = initVacuumLocation();
        State start = State.getInstance(dirtyRooms, vacuumLocation);
        String path = findBestPath(start, null);
        showUI(path, start);
    }

    private static void showUI(String path, State start) {
        JFrame frame = new JFrame("Vacuum Cleaner Problem");
        frame.setBounds(600, 300, 400, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        MyPanel panel = new MyPanel(path, start);
        frame.add(panel);
        panel.setBounds(0,0,400,400);
    }

    private static String findBestPath(State start, List<State> sawedStates) {
        if (sawedStates == null) {
            sawedStates = new ArrayList<>();
        }
        if (start == null || sawedStates.contains(start)) {
            return "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        }

        List<State> sawedStates2 = new ArrayList<>(sawedStates);
        sawedStates2.add(start);

        if (start.getDirtyRooms().isEmpty()) {
            return "";
        }
        State state = start.getClean();
        String path = findBestPath(state, sawedStates2);
        if (path.length() < 20) {
            return "C" + path;
        }
        String bestPath;
        state = start.getUp();
        bestPath = "U" + findBestPath(state, sawedStates2);
        state = start.getLeft();
        path = "L" + findBestPath(state, sawedStates2);
        if (path.length() < bestPath.length()) {
            bestPath = path;
        }
        state = start.getDown();
        path = "D" + findBestPath(state, sawedStates2);
        if (path.length() < bestPath.length()) {
            bestPath = path;
        }
        state = start.getRight();
        path = "R" + findBestPath(state, sawedStates2);
        if (path.length() < bestPath.length()) {
            bestPath = path;
        }
        return bestPath;
    }

    private static String initVacuumLocation() {
        return String.valueOf((char) ('A' + randInt(4)));
    }

    private static List<String> initRooms() {
        List<String> dirtyRooms = new ArrayList<>();
        for (char ch = 'A'; ch <= 'D'; ch++) {
            if (randBool()) {
                dirtyRooms.add(String.valueOf(ch));
            }
        }
        return dirtyRooms;
    }

    private static int randInt(int max) {
        return (int) (Math.random() * max);
    }

    private static boolean randBool() {
        return randInt(2) == 0;
    }
}