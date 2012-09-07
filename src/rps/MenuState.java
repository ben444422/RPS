package rps;

import java.awt.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState {

    private int stateID;

    private ResourceManager rm;
    //menu button coordinates
    private float startButtonX = 550f;
    private float startButtonY = 100f;
    private float startButtonScale = 1f;
    private float aboutButtonX = 550f;
    private float aboutButtonY = 200f;
    private float aboutButtonScale = 1f;
    private float exitButtonX = 550f;
    private float exitButtonY = 300;
    private float exitButtonScale = 1f;
    //expansion step
    private float scaleStep = 0.000075f;

    public MenuState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    public void init(GameContainer gc, StateBasedGame sb) throws SlickException {   
        rm = ResourceManager.getInstance();
        //initialize images
        rm.loadImage("MENU_BACKGROUND", "rpsData/images/menuBG.jpg");
        rm.loadImage("-MENU-_ABOUT_BUTTON", "rpsData/images/menuAboutButton.jpg");
        rm.loadImage("-MENU-_START_BUTTON", "rpsData/images/menuStartButton.jpg");
        rm.loadImage("-MENU-_EXIT_BUTTON", "rpsData/images/menuExitButton.jpg");
        
        //initialize sounds
        rm.loadSound("CLICK_SOUND", "rpsData/sounds/click.wav");

        Font font = new Font("Verdana", Font.BOLD, 20);
    }

    public void render(GameContainer gc, StateBasedGame sb, Graphics gra) throws SlickException {
        // render the background
        rm.getImage("MENU_BACKGROUND").draw(0, 0);
        
        // Draw menu
        rm.getImage("-MENU-_START_BUTTON").draw(startButtonX, startButtonY, startButtonScale);
        rm.getImage("-MENU-_ABOUT_BUTTON").draw(aboutButtonX, aboutButtonY, aboutButtonScale);
        rm.getImage("-MENU-_EXIT_BUTTON").draw(exitButtonX, exitButtonY, exitButtonScale);
    }

    public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
        Input input = gc.getInput();

        //get information about mouse cursor position
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        boolean insideStartButton = false;
        boolean insideAboutButton = false;
        boolean insideExitButton = false;
        Image startButton = rm.getImage("-MENU-_START_BUTTON");
        Image aboutButton = rm.getImage("-MENU-_ABOUT_BUTTON");
        Image exitButton = rm.getImage("-MENU-_EXIT_BUTTON");
        if ((mouseX >= startButtonX && mouseX <= startButtonX + startButton.getWidth())
                && (mouseY >= startButtonY && mouseY <= startButtonY + startButton.getHeight())) {
            insideStartButton = true;
        } else if ((mouseX >= aboutButtonX && mouseX <= aboutButtonX + aboutButton.getWidth())
                && (mouseY >= aboutButtonY && mouseY <= aboutButtonY + aboutButton.getHeight())) {
            insideAboutButton = true;
        } else if ((mouseX >= exitButtonX && mouseX <= exitButtonX + exitButton.getWidth())
                && (mouseY >= exitButtonY && mouseY <= exitButtonY + exitButton.getHeight())) {
            insideExitButton = true;
        }


        if (insideStartButton) {
            //if user hovers over button, let it expand
            if (startButtonScale < 1.05f) {
                startButtonScale += scaleStep * delta;
            }
            //if user clicks this button go into game state
            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                /**
                Sound click = new Sound("rpsData/sounds/click.wav");
                click.play();
                 * **/
                rm.getSound("CLICK_SOUND").play();
                sb.enterState(RPSMain.GAMESTATE);
            }


        } else {
            if (startButtonScale > 1.0f) {
                startButtonScale -= scaleStep * delta;
            }
        }

        if (insideAboutButton) {
            if (aboutButtonScale < 1.05f) {
                aboutButtonScale += scaleStep * delta;
            }

            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                rm.getSound("CLICK_SOUND").play();
                sb.enterState(RPSMain.ABOUTSTATE);
            }

        } else {
            if (aboutButtonScale > 1.0f) {
                aboutButtonScale -= scaleStep * delta;
            }
        }

        if (insideExitButton) {
            if (exitButtonScale < 1.05f) {
                exitButtonScale += scaleStep * delta;
            }

            if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                rm.getSound("CLICK_SOUND").play();
                gc.exit();
            }

        } else {
            if (exitButtonScale > 1.0f) {
                exitButtonScale -= scaleStep * delta;
            }
        }
    }
}
