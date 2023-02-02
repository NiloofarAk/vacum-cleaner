import java.util.ArrayList;
import java.util.List;

public class State {
    private static List<State> allState = new ArrayList<>();

    public static State getInstance(List<String> dirtyRooms, String vacuumLocation) {
        for (State state : allState) {
            List<String> lastStateDirtyRooms = state.getDirtyRooms();
            if (!vacuumLocation.equals(state.getVacuumLocation())) {
                continue;
            }
            if (lastStateDirtyRooms.size() != dirtyRooms.size()) {
                continue;
            }
            boolean flag = true;
            for (int i = 0; i < dirtyRooms.size(); i++) {
                if (!lastStateDirtyRooms.contains(dirtyRooms.get(i))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return state;
            }
        }
        return new State(dirtyRooms, vacuumLocation);
    }

    private List<String> dirtyRooms;
    private String vacuumLocation;
    private State up;
    private State left;
    private State right;
    private State down;
    private State clean;
    private boolean setActionsFlag;

    private State(List<String> dirtyRooms, String vacuumLocation) {
        this.dirtyRooms = dirtyRooms;
        this.vacuumLocation = vacuumLocation;
        setActionsFlag = true;
        allState.add(this);
    }

    public void setActions() {
        if (setActionsFlag) {
            setActionsFlag = false;
            setUp();
            setDown();
            setLeft();
            setRight();
            setClean();
        }
    }

    private void setUp() {
        switch (vacuumLocation) {
            case "A":
                up = null;
                break;
            case "B":
                up = null;
                break;
            case "C":
                up = getInstance(dirtyRooms, "B");
                break;
            case "D":
                up = getInstance(dirtyRooms, "A");
                break;
        }
    }

    private void setLeft() {
        switch (vacuumLocation) {
            case "A":
                left = null;
                break;
            case "B":
                left = getInstance(dirtyRooms, "A");
                break;
            case "C":
                left = getInstance(dirtyRooms, "D");
                break;
            case "D":
                left = null;
                break;
        }
    }

    private void setRight() {
        switch (vacuumLocation) {
            case "A":
                right = getInstance(dirtyRooms, "B");
                break;
            case "B":
                right = null;
                break;
            case "C":
                right = null;
                break;
            case "D":
                right = getInstance(dirtyRooms, "C");
                break;
        }
    }

    private void setDown() {
        switch (vacuumLocation) {
            case "A":
                down = getInstance(dirtyRooms, "D");
                break;
            case "B":
                down = getInstance(dirtyRooms, "C");
                break;
            case "C":
                down = null;
                break;
            case "D":
                down = null;
                break;
        }
    }

    public void setClean() {
        if (dirtyRooms.contains(vacuumLocation)) {
            clean = getInstance(cleanRoom(vacuumLocation), vacuumLocation);
        } else {
            clean = null;
        }
    }

    private List<String> cleanRoom(String room) {
        List<String> newDirtyRooms = new ArrayList<>();
        for (String element : dirtyRooms) {
            if (!element.equals(room)) {
                newDirtyRooms.add(element);
            }
        }
        return newDirtyRooms;
    }

    public List<String> getDirtyRooms() {
        return dirtyRooms;
    }

    public String getVacuumLocation() {
        return vacuumLocation;
    }

    public State getUp() {
        getterCheck();
        return up;
    }

    public State getLeft() {
        getterCheck();
        return left;
    }

    public State getRight() {
        getterCheck();
        return right;
    }

    public State getDown() {
        getterCheck();
        return down;
    }

    public State getClean() {
        getterCheck();
        return clean;
    }

    public void getterCheck() {
        if (setActionsFlag) {
            setActions();
            setActionsFlag = false;
        }
    }
}