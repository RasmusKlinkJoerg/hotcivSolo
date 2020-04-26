package hotciv.standard;

public class OperationNames {

    //Prefixes
    public static final String GAME_PREFIX = "game";
    public static final String CITY_PREFIX = "city";
    public static final String UNIT_PREFIX = "unit";
    public static final String TILE_PREFIX = "tile";

    //Game methods
    public static final String GET_WINNER = "game-get-winner";
    public static final String GET_PLAYER_IN_TURN = "game-get-player-in-turn";
    public static final String END_OF_TURN = "game-end-of-turn";
    public static final String GET_AGE = "game-get-age";
    public static final String MOVE_UNIT = "game-move-unit";
    public static final String CHANGE_WORKFORCEFOCUS = "game-change-workforce-focus";
    public static final String CHANGE_PRODUCTION = "game-change-production";
    public static final String PERFORM_UNIT_ACTION = "game-perform-unit-action";
    public static final String GET_CITY = "game-get-city";
    public static final String GET_UNIT = "game-get-unit";
    public static final String GET_TILE = "game-get-tile";

    //City methods
    public static final String GET_OWNER_CITY = "city-get-owner";
    public static final String GET_SIZE = "city-get-size";
    public static final String GET_TREASURY = "city-get-treasury";
    public static final String GET_PRODUCTION = "city-get-production";
    public static final String GET_WORKFORCEFOCUS = "city-get-workforcefocus";
    public static final String GET_FOOD_COUNT = "city-get-foodcount";

    //Unit methods
    public static final String GET_TYPESTRING_UNIT = "unit-get-typestring";
    public static final String GET_OWNER_UNIT = "unit-get-owner";
    public static final String GET_MOVECOUNT = "unit-get-movecount";
    public static final String GET_DEFENSE = "unit-get-def";
    public static final String GET_ATTACK = "unit-get-atc";

    //Tile methods
    public static final String GET_TYPESTRING_TILE = "tile-get-typestring";

}
