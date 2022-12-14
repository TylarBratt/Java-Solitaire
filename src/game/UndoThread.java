package game;

public class UndoThread extends Thread {
    public void run(Main main){
        int mementoBackgroundArraySize = Main.getBackgroundArray().size();
        if ( mementoBackgroundArraySize <= 1 ) {
            System.out.println("No moves to be made");
        }
        else {
            Background bg = Main.getBackgroundArray().get(Main.getBackgroundArray().size()-2);
            Main.getBackgroundArray().remove(Main.getBackgroundArray().size()-1);

            main.add(bg);
            bg.revalidate();
            bg.repaint();
        }
    }
}