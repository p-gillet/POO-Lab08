import chess.ChessController;
import chess.ChessView;
import chess.views.console.ConsoleView;
import chess.views.gui.GUIView;
import engine.GameManager;

public class Main {
    public static void main(String[] args) {
        // 1. Création du contrôleur pour gérer le jeu d’échecs
        // Instanciation d'un ChessController
        ChessController controller = new GameManager();

        // 2. Création de la vue désirée
        // Selon l'argument passé en ligne de commande:
        //  - Pas d'argument -> gui (par défaut)
        //  - "gui" -> vue gui
        //  - "console" -> vue console
        if (args.length > 1)
            throw new RuntimeException("Usage: java Main <console|gui>");
        ChessView view = null;
        if (args.length == 0 || args[0].equals("gui")) {
            System.out.println("Launch chess through GUI");
            view = new GUIView(controller);
        } else if (args[0].equals("console")) {
            System.out.println("Launch chess through console");
            view = new ConsoleView(controller);
        } else {
            throw new RuntimeException("Usage: java Main <console|gui>");
        }

        // 3 . Lancement du programme
        controller.start(view);
    }
}