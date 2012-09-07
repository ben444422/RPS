package rps;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RPSMain extends StateBasedGame {

    public static final int MENUSTATE = 0;
    public static final int ABOUTSTATE = 1;
    public static final int GAMESTATE = 2;

    public RPSMain() {
        super("Rock Paper Scissors");
        this.addState(new MenuState(MENUSTATE));
        this.addState(new AboutState(ABOUTSTATE));
        this.addState(new GameState(GAMESTATE));
        this.enterState(MENUSTATE);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new RPSMain());

        app.setDisplayMode(800, 600, false);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        gameContainer.setShowFPS(false);
        this.getState(MENUSTATE).init(gameContainer, this);
        this.getState(ABOUTSTATE).init(gameContainer, this);
        this.getState(GAMESTATE).init(gameContainer, this);
    }
}