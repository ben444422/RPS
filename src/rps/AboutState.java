package rps;

import java.awt.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class AboutState extends BasicGameState {

    private int stateID;

    //menu button coordinates
    private float menuButtonX = 600f;
    private float menuButtonY = 100f;
    private float menuButtonScale = 1f;
    //expansion step
    private float scaleStep = 0.000075f;
    
    private ResourceManager rm;

    public AboutState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }
    TrueTypeFont trueTypeFont = null;

    public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
        rm = ResourceManager.getInstance();
        rm.loadImage("ABOUT_BACKGROUND", "rpsData/images/aboutBG.jpg");
        rm.loadImage("-ABOUT-_MENU_BUTTON", "rpsData/images/aboutMenuButton.jpg");
        
        rm.loadSound("CLICK_SOUND", "rpsData/sounds/click.wav");
        
        /**
        bg = new Image("aboutBG.jpg");
        menuButton = new Image("aboutMenuButton.jpg");

        clickSound = new Sound("click.wav");
        
        **/

        Font font = new Font("Verdana", Font.BOLD, 20);
        trueTypeFont = new TrueTypeFont(font, true);
    }

    public void render(GameContainer gc, StateBasedGame sb, Graphics gra) throws SlickException {
        // render the background
        rm.getImage("ABOUT_BACKGROUND").draw(0, 0);

        // Draw menu
        rm.getImage("-ABOUT-_MENU_BUTTON").draw(menuButtonX, menuButtonY, menuButtonScale);
    }

    public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
        Input input = gc.getInput();

        //get information about mouse cursor position
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        boolean insideMenuButton = false;
        Image menuButton = rm.getImage("-ABOUT-_MENU_BUTTON");
        if ((mouseX >= menuButtonX && mouseX <= menuButtonX + menuButton.getWidth())
                && (mouseY >= menuButtonY && mouseY <= menuButtonY + menuButton.getHeight())) {
            insideMenuButton = true;
        }

        if (insideMenuButton) {
            //if user hovers over button, let it expand
            if (menuButtonScale < 1.05f) {
                menuButtonScale += scaleStep * delta;
            }
            //if user clicks this button go into game state
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                rm.getSound("CLICK_SOUND").play();
                sb.enterState(RPSMain.MENUSTATE);
            }

        } else {
            if (menuButtonScale > 1.0f) {
                menuButtonScale -= scaleStep * delta;
            }
        }
    }
}
