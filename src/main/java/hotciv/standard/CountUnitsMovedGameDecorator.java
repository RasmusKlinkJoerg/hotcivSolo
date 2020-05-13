package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;

public class CountUnitsMovedGameDecorator extends GameDecorator {

    private int unitsMoved;

    public CountUnitsMovedGameDecorator(Game game) {
        super(game);
    }

    @Override
    public boolean moveUnit(Position from, Position to){
        unitsMoved++;
        System.out.println("Number of units moved: " + unitsMoved);
        return super.moveUnit(from,to);
    }


}
