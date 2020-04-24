package hotciv.stub;


import frds.broker.Servant;
import hotciv.framework.*;

public class StubGame4 implements Game {

    private Player inTurn;
    private int age;

    public StubGame4() {
        inTurn = Player.RED;
        age = 0;
    }

    @Override
    public Tile getTileAt(Position p) {
        return null;
    }

    @Override
    public Unit getUnitAt(Position p) {
        return null;
    }

    @Override
    public City getCityAt(Position p) {
        return null;
    }

    // === Turn handling ===
    public void endOfTurn() {
        //System.out.println( "-- StubGame2 / endOfTurn called." );
        inTurn = (getPlayerInTurn() == Player.RED ?
                Player.BLUE :
                Player.RED );
        // no age increments
    }
    public Player getPlayerInTurn() { return inTurn; }

    @Override
    public Player getWinner() {
        return Player.RED;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        return true;
    }


    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        age = 37;
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        age = 42;
    }

    @Override
    public void performUnitActionAt(Position p) {
        age = 69;
    }

    @Override
    public void addObserver(GameObserver observer) {

    }


    public void setTileFocus(Position position) { }
    public Position getTileFocus() { return null; }
}
