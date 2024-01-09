package chess;

/**
 * Regroupe les méthodes appelées par la vue.
 */
public interface ChessController {

  /**
   * Démarre la logique (contrôleur) du programme.
   * Appelé une fois (voir Chess.main())
   * @param view la vue à utiliser
   */
  void start(ChessView view);

  /**
   * Appelé lorsque l'utilisateur a demandé un déplacement de la position A à la position B.
   * La position 0, 0 est en bas à gauche de l'échiquier.
   * @param fromX la coordonée x du point de départ
   * @param fromY la coordonée y du point de départ
   * @param toX la coordonée x de la destination
   * @param toY la coordonée y de la destination
   * @return true si le mouvement a pu avoir lieu, false dans le cas contraire.
   */
  boolean move(int fromX, int fromY, int toX, int toY);

  /**
   * Démarre une nouvelle partie. L'échiquier doit être remis dans sa position initiale.
   */
  void newGame();

}