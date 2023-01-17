import models.gui.GUI;

public class Main {
    public static void main(String[] args) {
        GUI app = new GUI(800,600);
        app.setVisible(true);
        app.setResizable(false);
    }
}