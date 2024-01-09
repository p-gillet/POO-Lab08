package test.java.chess;

import chess.PieceType;
import chess.PlayerColor;
import engine.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests pour le jeu d'échec
 */
public class AppTest 
{

    //////////////////////////// v TESTS DE LA CLASSE BOARD v ////////////////////////////

    /**
     * Test la création du plateau de jeu
     */
    @Test
    public void testBoardCreation() {
        Board board = new Board();
        assertEquals(board.getTurn(), 0);
        assertNull(board.getLastPiecePlayed());
        assertNull(board.getCastlingSquares());
        assertNull(board.getEnPassantSquare());
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                assertNotNull(board.getSquare(i, j));
            }
        }
    }

    /**
     * Test la réinitialisation du plateau de jeu
     */
    @Test
    public void testResetBoard() {
        Board board = new Board();
        board.setTurn(5);
        board.setLastPiecePlayed(new Pawn(new Square(4,2), PlayerColor.BLACK, board));
        board.setCastlingSquares(new Square[]{new Square(4,2), new Square(5,3)});
        board.setEnPassantSquare(new Square(4,2));
        board.resetBoard();
        assertEquals(board.getTurn(), 0);
        assertNull(board.getLastPiecePlayed());
        assertNull(board.getCastlingSquares());
        assertNull(board.getEnPassantSquare());
    }

    /**
     * Test si le changement de couleur après chaque tour fonctionne
     */
    @Test
    public void testColorTurn() {
        class TestBoard extends Board {
            public TestBoard() {
                super();
            }
            public PlayerColor colorTurn() {
                return super.colorTurn();
            }
        }
        TestBoard board = new TestBoard();
        assertEquals(board.colorTurn(), PlayerColor.WHITE);
        board.setTurn(1);
        assertEquals(board.colorTurn(), PlayerColor.BLACK);
        board.setTurn(2);
        assertEquals(board.colorTurn(), PlayerColor.WHITE);
        board.setTurn(3);
        assertEquals(board.colorTurn(), PlayerColor.BLACK);
    }

    /**
     * Test le getter pour les cases
     */
    @Test
    public void testGetSquare() {
        Board board = new Board();
        Square square = board.getSquare(3, 4);
        assertEquals(3, square.getX());
        assertEquals(4, square.getY());
    }

    /**
     * Test le déplacement de pièce
     */
    @Test
    public void testMovePiece() {
        Board board = new Board();
        board.setStartingPiecePosition();
        Square from = board.getSquare(4, 1);
        Square to = board.getSquare(4, 3);
        Piece piece = from.getPiece();
        board.movePiece(from.getPiece(), to);
        assertNull(from.getPiece());
        assertNotNull(to.getPiece());
        assertEquals(piece, to.getPiece());
    }

    /**
     * Test du déplacement de pièce en cas d'échec
     */
    @Test
    public void testProtectionOfKing() {
        Board board = new Board();
        board.setStartingPiecePosition();

        board.movePiece(board.getSquare(7,1).getPiece(), board.getSquare(7, 2));
        board.movePiece(board.getSquare(3,6).getPiece(), board.getSquare(3, 4));
        board.movePiece(board.getSquare(7,2).getPiece(), board.getSquare(7, 3));
        board.movePiece(board.getSquare(3,7).getPiece(), board.getSquare(3, 5));
        board.movePiece(board.getSquare(7,3).getPiece(), board.getSquare(7, 4));
        board.movePiece(board.getSquare(3,5).getPiece(), board.getSquare(1, 3));

        // Le roi blanc est en échec si le pion bouge alors le mouvement est impossible
        assertFalse(board.checkMovement(board.getSquare(3, 1), board.getSquare(3, 3)));
    }

    /**
     * Test du placement de pièce sur une case
     */
    @Test
    public void testSetPiece() {
        Board board = new Board();
        Square square = board.getSquare(4, 4);
        Piece piece = new Queen(square, PlayerColor.WHITE, board);
        board.setPiece(piece, square);
        assertEquals(piece, square.getPiece());
    }

    /**
     * Test la suppression d'une pièce
     */
    @Test
    public void testRemovePiece() {
        Board board = new Board();
        board.setStartingPiecePosition();
        Square square = board.getSquare(4, 0);
        board.removePiece(square);
        assertNull(square.getPiece());
    }

    /**
     * Test du roque
     */
    @Test
    public void testGetSetCastlingSquares() {
        Board board = new Board();
        Square[] squares = {board.getSquare(0, 0), board.getSquare(7, 0)};
        board.setCastlingSquares(squares);
        assertArrayEquals(squares, board.getCastlingSquares());
    }

    /**
     * Test de la prise en passant
     */
    @Test
    public void testGetSetEnPassantSquare() {
        Board board = new Board();
        Square square = board.getSquare(4, 4);
        board.setEnPassantSquare(square);
        assertEquals(square, board.getEnPassantSquare());
    }

    /**
     * Test de la dernière pièce jouée
     */
    @Test
    public void testGetSetLastPiecePlayed() {
        Board board = new Board();
        Square square = board.getSquare(4, 4);
        Piece piece = new Pawn(square, PlayerColor.WHITE, board);
        board.setLastPiecePlayed(piece);
        assertEquals(piece, board.getLastPiecePlayed());
    }

    /**
     * Test pour la définition du nombre de tour
     */
    @Test
    public void testGetSetTurn() {
        Board board = new Board();
        assertEquals(0, board.getTurn());
        board.setTurn(1);
        assertEquals(1, board.getTurn());
        board.setTurn(2);
        assertEquals(2, board.getTurn());
    }

    /**
     * Test de la position de départ des pièces
     */
    @Test
    public void testPiecesStartPosition() {
        Board board = new Board();
        board.setStartingPiecePosition();
        assertTrue(board.getSquare(0, 0).getPiece() instanceof Rook);
        assertTrue(board.getSquare(1, 0).getPiece() instanceof Knight);
        assertTrue(board.getSquare(2, 0).getPiece() instanceof Bishop);
        assertTrue(board.getSquare(3, 0).getPiece() instanceof Queen);
        assertTrue(board.getSquare(4, 0).getPiece() instanceof King);
        assertTrue(board.getSquare(5, 0).getPiece() instanceof Bishop);
        assertTrue(board.getSquare(6, 0).getPiece() instanceof Knight);
        assertTrue(board.getSquare(7, 0).getPiece() instanceof Rook);
        for (int i = 0; i < board.getSize(); i++) {
            assertEquals(PlayerColor.WHITE, board.getSquare(i, 0).getPiece().getColor());
        }

        assertTrue(board.getSquare(0, 7).getPiece() instanceof Rook);
        assertTrue(board.getSquare(1, 7).getPiece() instanceof Knight);
        assertTrue(board.getSquare(2, 7).getPiece() instanceof Bishop);
        assertTrue(board.getSquare(3, 7).getPiece() instanceof Queen);
        assertTrue(board.getSquare(4, 7).getPiece() instanceof King);
        assertTrue(board.getSquare(5, 7).getPiece() instanceof Bishop);
        assertTrue(board.getSquare(6, 7).getPiece() instanceof Knight);
        for (int i = 0; i < board.getSize(); i++) {
            assertEquals(PlayerColor.BLACK, board.getSquare(i, 7).getPiece().getColor());
        }

        for (int i = 0; i < board.getSize(); i++) {
            assertTrue(board.getSquare(i, 1).getPiece() instanceof Pawn);
            assertTrue(board.getSquare(i, 6).getPiece() instanceof Pawn);
        }
    }

    /**
     * Test la promotion d'un pion
     */
    @Test
    public void testCanPromote() {
        Board board = new Board();
        Square square = board.getSquare(4, 7);
        Pawn pawn = new Pawn(square, PlayerColor.WHITE, board);
        board.setPiece(pawn,square);
        assertTrue(board.canPromote(square));

        square = board.getSquare(4, 0);
        pawn = new Pawn(square, PlayerColor.BLACK, board);
        board.setPiece(pawn,square);
        assertTrue(board.canPromote(square));

        square = board.getSquare(4, 6);
        pawn = new Pawn(square, PlayerColor.WHITE, board);
        board.setPiece(pawn,square);
        assertFalse(board.canPromote(square));

        square = board.getSquare(4, 1);
        pawn = new Pawn(square, PlayerColor.BLACK, board);
        board.setPiece(pawn,square);
        assertFalse(board.canPromote(square));
    }

    /**
     * Test la promotion d'un pion
     */
    @Test
    public void testPromote() {
        Board board = new Board();
        board.setStartingPiecePosition();
        Square square = board.getSquare(4, 6);
        Piece[] choices = board.promote(square);
        assertEquals(4, choices.length);
        assertTrue(choices[0] instanceof Queen || choices[0] instanceof Knight ||
                choices[0] instanceof Rook || choices[0] instanceof Bishop);
        assertTrue(choices[1] instanceof Queen || choices[1] instanceof Knight ||
                choices[1] instanceof Rook || choices[1] instanceof Bishop);
        assertTrue(choices[2] instanceof Queen || choices[2] instanceof Knight ||
                choices[2] instanceof Rook || choices[2] instanceof Bishop);
        assertTrue(choices[3] instanceof Queen || choices[3] instanceof Knight ||
                choices[3] instanceof Rook || choices[3] instanceof Bishop);
    }

    /**
     * Test du déplacement de pièce
     */
    @Test
    public void testMove() {
        Board board = new Board();
        board.setStartingPiecePosition();

        Square from = board.getSquare(4, 3);
        Square to = board.getSquare(4, 4);
        assertFalse(board.checkMovement(from, to));

        from = board.getSquare(4, 6);
        to = board.getSquare(4, 4);
        assertFalse(board.checkMovement(from, to));

        Square square = board.getSquare(0, 2);
        Pawn pawn = new Pawn(square, PlayerColor.WHITE, board);
        board.setPiece(pawn,square);
        from = board.getSquare(0, 1);
        to = board.getSquare(0, 2);
        assertFalse(board.checkMovement(from, to));

        from = board.getSquare(4, 1);
        to = board.getSquare(4, 3);
        assertTrue(board.checkMovement(from, to));
    }

    //////////////////////////// ^ TESTS DE LA CLASSE BOARD ^ ////////////////////////////



    //////////////////////////// v TESTS DES PIECES DE JEU  v ////////////////////////////
    /**
     * Test pour chaque type de pièce:
     * - déplacements propre à la pièce
     * - le getter pour son type
     * - le getter pour son nom
     * - sa méthode toString
     */

    //////////////////////////// v TESTS DE LA REINE  v ////////////////////////////

    @Test
    public void testIsValidMoveForQueen() {
        class TestQueen extends Queen {
            public TestQueen(Square square, PlayerColor color, Board board) {
                super(square, color, board);
            }

            @Override
            public boolean isValidMove(Square target) {
                return super.isValidMove(target);
            }
        }

        Board board = new Board();
        TestQueen queen = new TestQueen(board.getSquare(4, 4), PlayerColor.WHITE, board);
        board.setPiece(queen, board.getSquare(4, 4));

        // Test de déplacement diagonal valide
        assertTrue(queen.isValidMove(board.getSquare(7, 7)));
        assertTrue(queen.isValidMove(board.getSquare(0, 0)));
        assertTrue(queen.isValidMove(board.getSquare(1, 7)));
        assertTrue(queen.isValidMove(board.getSquare(7, 1)));

        // Test de déplacement horizontal ou vertical valide
        assertTrue(queen.isValidMove(board.getSquare(4, 7)));
        assertTrue(queen.isValidMove(board.getSquare(4, 0)));
        assertTrue(queen.isValidMove(board.getSquare(7, 4)));
        assertTrue(queen.isValidMove(board.getSquare(0, 4)));

        // Test de déplacement diagonal non valide
        assertFalse(queen.isValidMove(board.getSquare(5, 6)));
        assertFalse(queen.isValidMove(board.getSquare(3, 2)));
        assertFalse(queen.isValidMove(board.getSquare(5, 2)));
        assertFalse(queen.isValidMove(board.getSquare(3, 6)));

        // Test de déplacement avec une pièce bloquant le chemin
        board.setPiece(new Pawn(board.getSquare(4, 5), PlayerColor.WHITE, board), board.getSquare(4, 5));
        assertFalse(queen.canMove(board.getSquare(4, 7)));
    }

    @Test
    public void testGetTypeForQueen() {
        Board board = new Board();
        Queen queen = new Queen(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals(PieceType.QUEEN, queen.getType());
    }

    @Test
    public void testTextValueForQueen() {
        Board board = new Board();
        Queen queen = new Queen(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals("Queen", queen.textValue());
    }

    @Test
    public void testToStringForQueen() {
        Board board = new Board();
        Queen queen = new Queen(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals("Queen", queen.toString());
    }

    //////////////////////////// ^ TESTS DE LA REINE  ^ ////////////////////////////

    //////////////////////////// v TESTS DU ROI  v ////////////////////////////

    @Test
    public void testIsValidMoveForKing() {
        class TestKing extends King {
            public TestKing(Square square, PlayerColor color, Board board) {
                super(square, color, board);
            }

            @Override
            public boolean isValidMove(Square target) {
                return super.isValidMove(target);
            }
        }

        Board board = new Board();
        TestKing testKing = new TestKing(new Square(3, 3), PlayerColor.WHITE, board);
        board.getSquare(3, 3).setPiece(testKing);

        // Test déplacement valide
        assertTrue(testKing.isValidMove(board.getSquare(3, 4)));
        assertTrue(testKing.isValidMove(board.getSquare(3, 2)));
        assertTrue(testKing.isValidMove(board.getSquare(4, 3)));
        assertTrue(testKing.isValidMove(board.getSquare(2, 3)));
        assertTrue(testKing.isValidMove(board.getSquare(4, 4)));
        assertTrue(testKing.isValidMove(board.getSquare(2, 2)));
        assertTrue(testKing.isValidMove(board.getSquare(4, 2)));
        assertTrue(testKing.isValidMove(board.getSquare(2, 4)));

        // Test déplacement non valide
        assertFalse(testKing.isValidMove(board.getSquare(3, 5)));
        assertFalse(testKing.isValidMove(board.getSquare(3, 1)));
        assertFalse(testKing.isValidMove(board.getSquare(5, 3)));

        // Test déplacement non valide avec une pièce qui bloque le passage
        Pawn blockingPawn = new Pawn(board.getSquare(3, 4), PlayerColor.WHITE, board);
        board.setPiece(blockingPawn, board.getSquare(3, 4));
        assertFalse(board.checkMovement(testKing.getSquare(), board.getSquare(3, 4)));

        // Test déplacement en échec
        board.setPiece(new Pawn(board.getSquare(4, 5), PlayerColor.BLACK, board), board.getSquare(4, 4));
        assertFalse(board.checkMovement(testKing.getSquare(), board.getSquare(4, 4)));
    }

    @Test
    public void testCastleRightForKing() {
        Board board = new Board();

        King king = new King(board.getSquare(4, 0), PlayerColor.WHITE, board);
        Rook rook = new Rook(board.getSquare(7, 0), PlayerColor.WHITE, board);
        board.getSquare(4, 0).setPiece(king);
        board.getSquare(7, 0).setPiece(rook);

        // vérification du petit roque
        assertTrue(king.canMove(board.getSquare(6, 0)));

        // petit roque impossible si le roi a déjà bougé
        board.setPiece(king, board.getSquare(4, 0)); // cette ligne incrémente le nombre de déplacements
        assertFalse(king.canMove(board.getSquare(6, 0)));

        // petit roque impossible si la tour a déjà bougé
        board.setPiece(rook, board.getSquare(7, 0));
        assertFalse(king.canMove(board.getSquare(6, 0)));

        // petit roque impossible si une pièce bloque le chemin
        board.setPiece(new Pawn(board.getSquare(5, 0), PlayerColor.WHITE, board), board.getSquare(5, 0));
        assertFalse(king.canMove(board.getSquare(6, 0)));

        // petit roque impossible si le roi est en échec
        board.setPiece(new Pawn(board.getSquare(4, 1), PlayerColor.BLACK, board), board.getSquare(4, 1));
        assertFalse(king.canMove(board.getSquare(6, 0)));

        // petit roque impossible si le roi passe par une case en échec
        board.setPiece(new Pawn(board.getSquare(5, 1), PlayerColor.BLACK, board), board.getSquare(5, 1));
        assertFalse(king.canMove(board.getSquare(6, 0)));

        // petit roque impossible si la case d'arrivée est en échec
        board.setPiece(new Pawn(board.getSquare(6, 1), PlayerColor.BLACK, board), board.getSquare(6, 1));
        assertFalse(king.canMove(board.getSquare(6, 0)));
    }

    @Test
    public void testCastleLeftForKing() {
        Board board = new Board();

        King king = new King(board.getSquare(4, 0), PlayerColor.WHITE, board);
        Rook rook = new Rook(board.getSquare(0, 0), PlayerColor.WHITE, board);
        board.getSquare(4, 0).setPiece(king);
        board.getSquare(0, 0).setPiece(rook);

        // vérification du grand roque
        assertTrue(king.canMove(board.getSquare(2, 0)));

        // grand roque impossible si le roi a déjà bougé
        board.setPiece(king, board.getSquare(4, 0));
        assertFalse(king.canMove(board.getSquare(2, 0)));

        // grand roque impossible si la tour a déjà bougé
        board.setPiece(rook, board.getSquare(0, 0)); // cette ligne incrémente le nombre de déplacements
        assertFalse(king.canMove(board.getSquare(2, 0)));

        // grand roque impossible si une pièce bloque le passage
        board.setPiece(new Pawn(board.getSquare(1, 0), PlayerColor.WHITE, board), board.getSquare(1, 0));
        assertFalse(king.canMove(board.getSquare(2, 0)));

        // grand roque impossible si le roi est en échec
        board.setPiece(new Pawn(board.getSquare(3, 1), PlayerColor.BLACK, board), board.getSquare(3, 1));
        assertFalse(king.canMove(board.getSquare(2, 0)));

        // grand roque impossible si le roi passe par une case en échec
        board.setPiece(new Pawn(board.getSquare(2, 1), PlayerColor.BLACK, board), board.getSquare(2, 1));
        assertFalse(king.canMove(board.getSquare(2, 0)));
    }

    @Test
    public void testGetTypeForKing() {
        Board board = new Board();
        King king = new King(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals(PieceType.KING, king.getType());
    }

    @Test
    public void testTextValueForKing() {
        Board board = new Board();
        King king = new King(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals("King", king.textValue());
    }

    @Test
    public void testToStringForKing() {
        Board board = new Board();
        King king = new King(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals("King", king.toString());
    }

    //////////////////////////// ^ TESTS DU ROI  ^ ////////////////////////////

    //////////////////////////// v TESTS DU PION  v ////////////////////////////
    @Test
    public void testValidMoveForPawn() {
        class TestPawn extends Pawn {
            public TestPawn(Square square, PlayerColor color, Board board) {
                super(square, color, board);
            }

            @Override
            public boolean isValidMove(Square target) {
                return super.isValidMove(target);
            }
        }

        Board board = new Board();
        TestPawn whitePawn = new TestPawn(board.getSquare(3, 1), PlayerColor.WHITE, board);

        // Test mouvement simple blanc premier tour valide
        assertTrue(whitePawn.canMove(board.getSquare(3, 2)));

        // Test mouvement double premier tour valide
        assertTrue(whitePawn.canMove(board.getSquare(3, 2)));

        // Test mouvement double avec une pièce qui bloque le passage
        TestPawn blockingPawn = new TestPawn(board.getSquare(3, 2), PlayerColor.BLACK, board);
        board.setPiece(blockingPawn, board.getSquare(3, 2));
        assertFalse(whitePawn.canMove(board.getSquare(3, 3)));

        // Test mouvement simple en diagonale
        assertFalse(whitePawn.canMove(board.getSquare(4, 2)));

        // Test mouvement simple en arrière
        assertFalse(whitePawn.canMove(board.getSquare(3, 0)));

        // Test déplacement avec capture valide
        TestPawn enemyPawn1 = new TestPawn(board.getSquare(4, 2), PlayerColor.BLACK, board);
        board.setPiece(enemyPawn1, board.getSquare(4, 2));
        assertTrue(whitePawn.canMove(board.getSquare(4, 2)));

        // Test déplacement avec capture invalide
        TestPawn enemyPawn2 = new TestPawn(board.getSquare(3, 2), PlayerColor.BLACK, board);
        board.setPiece(enemyPawn2, board.getSquare(3, 2));
        assertFalse(whitePawn.canMove(board.getSquare(3, 2)));
    }

    @Test
    public void testEnPassantMove() {
        class TestPawn extends Pawn {
            public TestPawn(Square square, PlayerColor color, Board board) {
                super(square, color, board);
            }

            @Override
            public boolean isValidMove(Square target) {
                return super.isValidMove(target);
            }
        }

        class TestBoard extends Board {
            public TestBoard() {
                super();
            }

            @Override
            public boolean checkMovement(Square from, Square to) {
                if(from.getPiece() == null){
                    return false;
                }

                if(from.getPiece().getColor() != colorTurn()){
                    return false;
                }

                if(to.getPiece() != null && to.getPiece().getColor() == from.getPiece().getColor()){
                    return false;
                }

                Square enPassant;
                if((enPassant = this.getEnPassantSquare()) != null){
                    removePiece(enPassant);
                    this.setEnPassantSquare(null);
                }

                boolean valid = false;
                if(from.getPiece().canMove(to)){
                    valid = true;
                    Piece deadPiece = to.getPiece();
                    movePieceDummy(from.getPiece(), to);
                    movePieceDummy(to.getPiece(), from);
                    if(deadPiece != null){
                        setPieceDummy(deadPiece, to);
                    }
                }
                return valid;
            }
        }

        TestBoard board = new TestBoard();

        TestPawn whitePawn = new TestPawn(board.getSquare(4, 3), PlayerColor.WHITE, board);
        TestPawn blackPawn = new TestPawn(board.getSquare(3, 6), PlayerColor.BLACK, board);

        board.setPiece(whitePawn, board.getSquare(4, 3));
        board.getSquare(3,6).setPiece(blackPawn);

        board.movePiece(whitePawn, board.getSquare(4, 4));
        blackPawn.canMove(board.getSquare(3, 4));
        board.movePiece(blackPawn, board.getSquare(3, 4));

        // Test mouvement en passant valide
        assertTrue(whitePawn.isValidMove(board.getSquare(3, 5)));

        board.checkMovement(whitePawn.getSquare(), board.getSquare(3, 5));

        // Validation du mouvement en passant
        assertNull(board.getSquare(3, 4).getPiece());

    }

    @Test
    public void testThreatensSquare() {
        Board board = new Board();
        Pawn whitePawn = new Pawn(board.getSquare(4, 6), PlayerColor.WHITE, board);
        Pawn blackPawn = new Pawn(board.getSquare(4, 1), PlayerColor.BLACK, board);

        // Test de prise en diagonale valide pour le pion blanc
        assertTrue(whitePawn.threatensSquare(board.getSquare(3, 7)));
        assertTrue(whitePawn.threatensSquare(board.getSquare(5, 7)));

        // Test de prise en avant invalide pour le pion blanc
        assertFalse(whitePawn.threatensSquare(board.getSquare(4, 7)));
        assertFalse(whitePawn.threatensSquare(board.getSquare(4, 5)));

        // Test de prise en diagonale valide pour le pion noir
        assertTrue(blackPawn.threatensSquare(board.getSquare(3, 0)));
        assertTrue(blackPawn.threatensSquare(board.getSquare(5, 0)));

        // Test de prise en avant invalide pour le pion noir
        assertFalse(blackPawn.threatensSquare(board.getSquare(4, 0)));
        assertFalse(blackPawn.threatensSquare(board.getSquare(4, 2)));
    }

    @Test
    public void testCanBePromoted() {
        // Initialize a white pawn at the top of the board
        Pawn whitePawn = new Pawn(new Square(3, 7), PlayerColor.WHITE, new Board());
        assertTrue(whitePawn.canBePromoted());

        // Initialize a black pawn at the bottom of the board
        Pawn blackPawn = new Pawn(new Square(3, 0), PlayerColor.BLACK, new Board());
        assertTrue(blackPawn.canBePromoted());

        // Initialize a white pawn in the middle of the board
        Pawn whitePawn2 = new Pawn(new Square(3, 3), PlayerColor.WHITE, new Board());
        assertFalse(whitePawn2.canBePromoted());

        // Initialize a black pawn in the middle of the board
        Pawn blackPawn2 = new Pawn(new Square(3, 4), PlayerColor.BLACK, new Board());
        assertFalse(blackPawn2.canBePromoted());
    }

    @Test
    public void testGetSetEnPassantVictim() {
        // Initialize a pawn that is not an en passant victim
        Pawn pawn = new Pawn(new Square(3, 3), PlayerColor.BLACK, new Board());
        assertFalse(pawn.getEnPassantVictim());

        // Set the pawn to be an en passant victim and verify that the getEnPassantVictim method returns true
        pawn.setEnPassantVictim(true);
        assertTrue(pawn.getEnPassantVictim());
    }

    @Test
    public void testGetTypeForPawn() {
        Board board = new Board();
        Pawn pawn = new Pawn(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals(PieceType.PAWN, pawn.getType());
    }

    @Test
    public void testTextValueForPawn() {
        Board board = new Board();
        Pawn pawn = new Pawn(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals("Pawn", pawn.textValue());
    }

    @Test
    public void testToStringForPawn() {
        Board board = new Board();
        Pawn pawn = new Pawn(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals("Pawn", pawn.toString());
    }

    //////////////////////////// ^ TESTS DU PION  ^ ////////////////////////////

    //////////////////////////// v TESTS DU FOU  v ////////////////////////////

    @Test
    public void testValidMoveForBishop() {
        class TestBishop extends Bishop {
            public TestBishop(Square square, PlayerColor color, Board board) {
                super(square, color, board);
            }

            @Override
            public boolean isValidMove(Square target) {
                return super.isValidMove(target);
            }
        }
        Board board = new Board();
        TestBishop bishop = new TestBishop(board.getSquare(4, 4), PlayerColor.WHITE, board);
        board.setPiece(bishop, board.getSquare(4, 4));

        // Test de déplacement diagonal valide
        assertTrue(bishop.isValidMove(board.getSquare(7, 7)));
        assertTrue(bishop.isValidMove(board.getSquare(0, 0)));
        assertTrue(bishop.isValidMove(board.getSquare(1, 7)));
        assertTrue(bishop.isValidMove(board.getSquare(7, 1)));

        // Test de déplacement horizontal ou vertical invalide
        assertFalse(bishop.isValidMove(board.getSquare(4, 7)));
        assertFalse(bishop.isValidMove(board.getSquare(4, 0)));
        assertFalse(bishop.isValidMove(board.getSquare(7, 4)));
        assertFalse(bishop.isValidMove(board.getSquare(0, 4)));

        // Test de déplacement diagonal non valide
        assertFalse(bishop.isValidMove(board.getSquare(5, 6)));
        assertFalse(bishop.isValidMove(board.getSquare(3, 2)));
        assertFalse(bishop.isValidMove(board.getSquare(5, 2)));
        assertFalse(bishop.isValidMove(board.getSquare(3, 6)));

        // Test de déplacement avec une pièce bloquant le chemin
        Pawn pawn = new Pawn(board.getSquare(5, 5), PlayerColor.WHITE, board);
        board.setPiece(pawn, board.getSquare(5, 5));
        assertFalse(bishop.canMove(board.getSquare(7, 7)));

    }

    @Test
    public void testGetTypeForBishop() {
        Board board = new Board();
        Bishop bishop = new Bishop(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals(PieceType.BISHOP, bishop.getType());
    }

    @Test
    public void testTextValueForBishop() {
        Board board = new Board();
        Bishop bishop = new Bishop(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals("Bishop", bishop.textValue());
    }

    @Test
    public void testToStringForBishop() {
        Board board = new Board();
        Bishop bishop = new Bishop(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals("Bishop", bishop.toString());
    }

    //////////////////////////// ^ TESTS DU FOU  ^ ////////////////////////////

    //////////////////////////// v TESTS DU CAVALIER  v ////////////////////////////

    @Test
    public void testValidMoveForKnight() {
        class TestKnight extends Knight {
            public TestKnight(Square square, PlayerColor color, Board board) {
                super(square, color, board);
            }

            @Override
            public boolean isValidMove(Square target) {
                return super.isValidMove(target);
            }
        }

        Board board = new Board();
        TestKnight knight = new TestKnight(board.getSquare(4, 4), PlayerColor.WHITE, board);
        board.setPiece(knight, board.getSquare(4, 4));

        // Test de déplacement valide
        assertTrue(knight.canMove(board.getSquare(3, 2)));
        assertTrue(knight.canMove(board.getSquare(5, 2)));
        assertTrue(knight.canMove(board.getSquare(3, 6)));
        assertTrue(knight.canMove(board.getSquare(5, 6)));

        assertTrue(knight.canMove(board.getSquare(6, 5)));
        assertTrue(knight.canMove(board.getSquare(2, 5)));
        assertTrue(knight.canMove(board.getSquare(6, 3)));
        assertTrue(knight.canMove(board.getSquare(2, 3)));

        // Test de déplacement invalide (4 directions)
        assertFalse(knight.canMove(board.getSquare(4, 7)));
        assertFalse(knight.canMove(board.getSquare(4, 0)));
        assertFalse(knight.canMove(board.getSquare(7, 4)));
        assertFalse(knight.canMove(board.getSquare(0, 4)));

        // Test de saut par dessus une pièce
        Pawn pawn1 = new Pawn(board.getSquare(3, 5), PlayerColor.WHITE, board);
        board.setPiece(pawn1, board.getSquare(3, 5));

        Pawn pawn2 = new Pawn(board.getSquare(4, 5), PlayerColor.WHITE, board);
        board.setPiece(pawn2, board.getSquare(4, 5));

        Pawn pawn3 = new Pawn(board.getSquare(5, 5), PlayerColor.WHITE, board);
        board.setPiece(pawn3, board.getSquare(5, 5));

        assertTrue(knight.canMove(board.getSquare(3, 6)));
        assertTrue(knight.canMove(board.getSquare(5, 6)));
    }

    @Test
    public void testGetTypeForKnight() {
        Board board = new Board();
        Knight knight = new Knight(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals(PieceType.KNIGHT, knight.getType());
    }

    @Test
    public void testTextValueForKnight() {
        Board board = new Board();
        Knight knight = new Knight(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals("Knight", knight.textValue());
    }

    @Test
    public void testToStringForKnight() {
        Board board = new Board();
        Knight knight = new Knight(board.getSquare(4, 4), PlayerColor.WHITE, board);
        assertEquals("Knight", knight.toString());
    }

    //////////////////////////// ^ TESTS DU CAVALIER  ^ ////////////////////////////

    //////////////////////////// v TESTS DE LA TOUR  v ////////////////////////////

    @Test
    public void testValidMoveForRook() {
        class TestRook extends Rook {
            public TestRook(Square square, PlayerColor color, Board board) {
                super(square, color, board);
            }

            @Override
            public boolean isValidMove(Square target) {
                return super.isValidMove(target);
            }
        }

        Board board = new Board();
        TestRook rook = new TestRook(board.getSquare(4, 4), PlayerColor.WHITE, board);
        board.setPiece(rook, board.getSquare(4, 4));

        // Test de déplacement valide
        assertTrue(rook.canMove(board.getSquare(4, 7)));
        assertTrue(rook.canMove(board.getSquare(4, 0)));
        assertTrue(rook.canMove(board.getSquare(7, 4)));
        assertTrue(rook.canMove(board.getSquare(0, 4)));

        // Test de déplacement invalide (4 diagonales)
        assertFalse(rook.canMove(board.getSquare(7, 7)));
        assertFalse(rook.canMove(board.getSquare(0, 0)));
        assertFalse(rook.canMove(board.getSquare(7, 0)));
        assertFalse(rook.canMove(board.getSquare(0, 7)));

        // Test de déplacement avec une pièce bloquant le chemin
        Pawn pawn = new Pawn(board.getSquare(4, 5), PlayerColor.WHITE, board);
        board.setPiece(pawn, board.getSquare(4, 5));
        assertFalse(rook.canMove(board.getSquare(4, 7)));
    }

    @Test
    public void testGetTypeForRook() {
        Square square = new Square(1, 1);
        Rook rook = new Rook(square, PlayerColor.BLACK, new Board());
        assertEquals(PieceType.ROOK, rook.getType());
    }

    @Test
    public void testTextValueForRook() {
        Square square = new Square(1, 1);
        Rook rook = new Rook(square, PlayerColor.BLACK, new Board());
        assertEquals("Rook", rook.textValue());
    }

    @Test
    public void testToStringForRook() {
        Square square = new Square(1, 1);
        Rook rook = new Rook(square, PlayerColor.BLACK, new Board());
        assertEquals("Rook", rook.toString());
    }

    //////////////////////////// ^ TESTS DE LA TOUR  ^ ////////////////////////////

    //////////////////////////// ^ TESTS DES PIECES DE JEU  ^ ////////////////////////////
}
