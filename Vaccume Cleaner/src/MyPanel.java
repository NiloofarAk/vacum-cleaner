import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class MyPanel extends JPanel {
    private String path;
    private State start;
    private JPanel vacuum;
    private JPanel p1;
    private JPanel p2;
    private JPanel p3;
    private JPanel p4;

    private JButton startBtn;

    public MyPanel(String path, State start) {
        this.path = path;
        this.start = start;
        initComponents();
        setLayout(null);
    }

    private void initComponents() {
        startBtn = new JButton("start");
        startBtn.setBounds(160, 300, 80, 35);
        startBtn.addActionListener(startBtnListener);

        p1 = new JPanel(null);
        p1.setBounds(90, 30, 100, 100);
        p1.setBackground(start.getDirtyRooms().contains("A") ? Color.gray : Color.pink);
        p1.setBorder(BorderFactory.createLineBorder(Color.black));
        p2 = new JPanel(null);
        p2.setBounds(190, 30, 100, 100);
        p2.setBackground(start.getDirtyRooms().contains("B") ? Color.gray : Color.pink);
        p2.setBorder(BorderFactory.createLineBorder(Color.black));
        p3 = new JPanel(null);
        p3.setBounds(90, 130, 100, 100);
        p3.setBackground(start.getDirtyRooms().contains("D") ? Color.gray : Color.pink);
        p3.setBorder(BorderFactory.createLineBorder(Color.black));
        p4 = new JPanel(null);
        p4.setBounds(190, 130, 100, 100);
        p4.setBackground(start.getDirtyRooms().contains("C") ? Color.gray : Color.pink);
        p4.setBorder(BorderFactory.createLineBorder(Color.black));

        int[] vacLocation = calculateVacLoc(start);
        vacuum = new JPanel(null);
        vacuum.setBounds(vacLocation[0], vacLocation[1], 30, 30);
        vacuum.setBackground(Color.orange);
        vacuum.setBorder(BorderFactory.createLineBorder(Color.black));

        add(vacuum);

        add(startBtn);
        add(p1);
        add(p2);
        add(p3);
        add(p4);
    }

    private int[] calculateVacLoc(State start) {
        int[] location = new int[2];
        switch (start.getVacuumLocation()) {
            case "A":
                location[0] = 125;
                location[1] = 65;
                break;
            case "B":
                location[0] = 225;
                location[1] = 65;
                break;
            case "C":
                location[0] = 225;
                location[1] = 165;
                break;
            case "D":
                location[0] = 125;
                location[1] = 165;
                break;
        }
        return location;
    }

    private ActionListener startBtnListener = new ActionListener() {
        private boolean isMoveStarted = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isMoveStarted) {
                return;
            }
            isMoveStarted = true;
            move(0);
        }
    };

    private void move(int index) {
        if (path.length() == index) {
            remove(vacuum);
            return;
        }
        switch (path.charAt(index)) {
            case 'U':
                moveUp(index);
                break;
            case 'L':
                moveLeft(index);
                break;
            case 'D':
                moveDown(index);
                break;
            case 'R':
                moveRight(index);
                break;
            case 'C':
                clean(index);
                break;
        }
    }

    private void clean(int index) {
        vacuum.setBackground(Color.green);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                JPanel panel = findBackground();
                panel.setBackground(Color.pink);
                vacuum.setBackground(Color.orange);
                move(index + 1);
            }
        }, 350);
    }

    private void moveUp(int index) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int x = vacuum.getX();
                int y = vacuum.getY();
                vacuum.setBounds(x, y - 1, vacuum.getWidth(), vacuum.getHeight());
            }
        }, 0, 10);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                move(index + 1);
            }
        }, 1000);
    }

    private void moveRight(int index) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int x = vacuum.getX();
                int y = vacuum.getY();
                vacuum.setBounds(x + 1, y, vacuum.getWidth(), vacuum.getHeight());
            }
        }, 0, 10);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                move(index + 1);
            }
        }, 1000);
    }

    private void moveDown(int index) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int x = vacuum.getX();
                int y = vacuum.getY();
                vacuum.setBounds(x, y + 1, vacuum.getWidth(), vacuum.getHeight());
            }
        }, 0, 10);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                move(index + 1);
            }
        }, 1000);
    }

    private void moveLeft(int index) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int x = vacuum.getX();
                int y = vacuum.getY();
                vacuum.setBounds(x - 1, y, vacuum.getWidth(), vacuum.getHeight());
            }
        }, 0, 10);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                move(index + 1);
            }
        }, 1000);
    }

    private JPanel findBackground() {
        int x = vacuum.getX();
        int y = vacuum.getY();
        if (x >= 90 && x < 190) {
            if (y >= 30 && y < 130) {
                return p1;
            } else {
                return p3;
            }
        } else {
            if (y >= 30 && y < 130) {
                return p2;
            } else {
                return p4;
            }
        }
    }
}
