package rps;

import java.awt.Font;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState extends BasicGameState {

    private int stateID;
    private RPS rpsGame;
    //state images
    private Image rockImage0;
    private Image rockImage1;
    private Image rockImage2;
    private Image paperImage0;
    private Image paperImage1;
    private Image paperImage2;
    private Image scissorsImage0;
    private Image scissorsImage1;
    private Image scissorsImage2;
    private Image compRockImage;
    private Image userRockImage;
    private Image compPaperImage;
    private Image userPaperImage;
    private Image compScissorsImage;
    private Image userScissorsImage;
            
    
    private TrueTypeFont ttf;
    private Image[] rockImages;
    private Image[] paperImages;
    private Image[] scissorsImages;
    //menu button coordinates
    private float menuButtonX = 550f;
    private float menuButtonY = 100f;
    private float menuButtonScale = 1f;
    private float rockButtonX = 35f;
    private float rockButtonY = 100f;
    private float paperButtonX = rockButtonX;
    private float paperButtonY = rockButtonY + 100f;
    private float scissorsButtonX = rockButtonX;
    private float scissorsButtonY = rockButtonY + 200f;
    private float compMoveImageX = 465f;
    private float compMoveImageY = rockButtonY;
    private float userMoveImageX = rockButtonX;
    private float userMoveImageY = rockButtonY;
    private float verdictCompX = compMoveImageX - 15;
    private float verdictCompY = compMoveImageY - 15;
    private float verdictUserX = userMoveImageX - 15;
    private float verdictUserY = userMoveImageY - 15;
    private float winBarX = 56f;
    private float winBarY = 541f;
    private float menuX = 650f;
    private float menuY = 480f;
    private float resetX = menuX - 125;
    private float resetY = menuY;
    private float buttonScale = 1f;
    private float winRate;
    //true if the first game has not been played yet
    private boolean firstGame = true;
    private Color white = new Color(255, 255, 255);
    private Color shade = new Color(50, 50, 100);
    private boolean inRock;
    private boolean inPaper;
    private boolean inScissors;
    private boolean inMenu;
    private boolean inReset;
    private String decision;
    private char userMove;
    
    private ResourceManager rm;

    private enum GAMESTATES {

        PLAYER_CHOOSE, VERDICT;
    }
    private GAMESTATES currentState;

    public GameState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        rm = ResourceManager.getInstance();
        rm.loadImage("GAME_BACKGROUND", "rpsData/images/gameBG.jpg");
        rm.loadImage("-GAME-_ROCK_BUTTON", "rpsData/images/gameRockButton.jpg");
        rm.loadImage("-GAME-_PAPER_BUTTON", "rpsData/images/gamePaperButton.jpg");
        rm.loadImage("-GAME-_SCISSORS_BUTTON", "rpsData/images/gameScissorsButton.jpg");
        rm.loadImage("-GAME-_MENU_BUTTON", "rpsData/images/gameMenuButton.jpg");
        rm.loadImage("-GAME-_RESET_BUTTON", "rpsData/images/gameResetButton.jpg");
        rm.loadImage("-GAME-_WIN_BAR", "rpsData/images/gameWinBar.jpg");
        rm.loadImage("-GAME-_BLANK_IMAGE", "rpsData/images/blankImage.jpg");
        rm.loadImage("-GAME-_LOSE_BORDER", "rpsData/images/loseBorder.jpg");
        rm.loadImage("-GAME-_WIN_BORDER", "rpsData/images/winBorder.jpg");
        rm.loadImage("-GAME-_TIE_BORDER", "rpsData/images/tieBorder.jpg");
        
        rockImage0 = rm.loadImage("-GAME-_ROCK_IMAGE_0", "rpsData/images/rockImage0.jpg");
        rockImage1 = rm.loadImage("-GAME-_ROCK_IMAGE_1", "rpsData/images/rockImage1.jpg");
        rockImage2 = rm.loadImage("-GAME-_ROCK_IMAGE_2", "rpsData/images/rockImage2.jpg");
        paperImage0 = rm.loadImage("-GAME-_PAPER_IMAGE_0", "rpsData/images/paperImage0.jpg");
        paperImage1 = rm.loadImage("-GAME-_PAPER_IMAGE_1", "rpsData/images/paperImage1.jpg");
        paperImage2 = rm.loadImage("-GAME-_PAPER_IMAGE_2", "rpsData/images/paperImage2.jpg");
        scissorsImage0 = rm.loadImage("-GAME-_SCISSORS_IMAGE_0", "rpsData/images/scissorsImage0.jpg");
        scissorsImage1 = rm.loadImage("-GAME-_SCISSORS_IMAGE_1", "rpsData/images/scissorsImage1.jpg");
        scissorsImage2 = rm.loadImage("-GAME-_SCISSORS_IMAGE_2", "rpsData/images/scissorsImage2.jpg");
        rockImages = new Image[]{rockImage0, rockImage1, rockImage2};
        paperImages = new Image[]{paperImage0, paperImage1, paperImage2};
        scissorsImages = new Image[]{scissorsImage0, scissorsImage1, scissorsImage2};        
        
        rm.loadSound("CLICK_SOUND", "rpsData/sounds/click.wav");
        rm.loadSound("DING_SOUND", "rpsData/sounds/ding.wav");
        rm.loadSound("BUZZER_SOUND", "rpsData/sounds/buzzer.wav");
        
        /**
        bg = new Image("gameBG.jpg");
        rockButton = new Image("gameRockButton.jpg");
        paperButton = new Image("gamePaperButton.jpg");
        scissorsButton = new Image("gameScissorsButton.jpg");
        menuButton = new Image("gameMenuButton.jpg");
        resetButton = new Image("gameResetButton.jpg");
        winBar = new Image("gameWinBar.jpg");
        
        blankImage = new Image("blankImage.jpg");
        loseBorder = new Image("loseBorder.jpg");
        winBorder = new Image("winBorder.jpg");
        tieBorder = new Image("tieBorder.jpg");
        clickSound = new Sound("click.wav");
        buzzerSound = new Sound("buzzer.wav");
        dingSound = new Sound("ding.wav");

        rockImage0 = new Image("rockImage0.jpg");
        rockImage1 = new Image("rockImage1.jpg");
        rockImage2 = new Image("rockImage2.jpg");

        paperImage0 = new Image("paperImage0.jpg");
        paperImage1 = new Image("paperImage1.jpg");
        paperImage2 = new Image("paperImage2.jpg");

        scissorsImage0 = new Image("scissorsImage0.jpg");
        scissorsImage1 = new Image("scissorsImage1.jpg");
        scissorsImage2 = new Image("scissorsImage2.jpg");

        rockImages = new Image[]{rockImage0, rockImage1, rockImage2};
        paperImages = new Image[]{paperImage0, paperImage1, paperImage2};
        scissorsImages = new Image[]{scissorsImage0, scissorsImage1, scissorsImage2};

        
        **/
        winRate = 0.0f;
        currentState = GAMESTATES.PLAYER_CHOOSE;

        Font font = new Font("Verdana", Font.BOLD, 25);
        ttf = new TrueTypeFont(font, true);
        rpsGame = new RPS();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        //draw the background
        rm.getImage("GAME_BACKGROUND").draw(0, 0);
        //draw the winrateBar
        rm.getImage("-GAME-_WIN_BAR").draw(winBarX, winBarY, rm.getImage("-GAME-_WIN_BAR").getWidth() * winRate, rm.getImage("-GAME-_WIN_BAR").getHeight());
        ttf.drawString(56f, 500f, this.getWinRateString(), Color.black);

        switch (currentState) {
            case PLAYER_CHOOSE:
                this.renderPlayerChoose();
                break;
            case VERDICT:
                this.renderVerdict();
        }

        //if it is the first move, draw a question mark
        if (firstGame) {
            rm.getImage("-GAME-_BLANK_IMAGE").draw(compMoveImageX, compMoveImageY);
        } else {
            //the computer's move will be drawn in every state
            this.drawMove('c');
        }

        //render the menu button
        this.renderButtons();

    }

    //render the menu Button
    private void renderButtons() {
        if (inMenu) {
            rm.getImage("-GAME-_MENU_BUTTON").draw(menuX, menuY, shade);
        } else {
            rm.getImage("-GAME-_MENU_BUTTON").draw(menuX, menuY, white);
        }
        
        if (inReset) {
            rm.getImage("-GAME-_RESET_BUTTON").draw(resetX, resetY, shade);
        } else {
            rm.getImage("-GAME-_RESET_BUTTON").draw(resetX, resetY, white);
        }
               
    }

    //modify the win rate and convert to a string
    private String getWinRateString() {
        String winString = "" + (winRate * 100);
        if (winRate < 0.10) {
            winString = "0" + winString;
        }
        winString = winString + "000";

        return winString.substring(0, 5) + "%";
    }

    //draw the image denoting the computers move
    private void drawMove(char entity) {
        char move;
        if (entity == 'c') {
            move = decision.charAt(0);
            Image moveImage = null;
            if (move == 'r') {
                moveImage = compRockImage;
            } else if (move == 'p') {
                moveImage = compPaperImage;
            } else if (move == 's') {
                moveImage = compScissorsImage;
            }
            moveImage.draw(compMoveImageX, compMoveImageY);
        } else if (entity == 'u') {
            move = userMove;
            Image moveImage = null;
            if (move == 'r') {
                moveImage = userRockImage;
            } else if (move == 'p') {
                moveImage = userPaperImage;
            } else if (move == 's') {
                moveImage = userScissorsImage;
            }
            moveImage.draw(userMoveImageX, userMoveImageY);
        }
    }

    private void renderPlayerChoose() {
        //if the mouse is hovered over a button the button is shaded a darker color
        if (inRock) {
            rm.getImage("-GAME-_ROCK_BUTTON").draw(rockButtonX, rockButtonY, shade);
        } else {
            rm.getImage("-GAME-_ROCK_BUTTON").draw(rockButtonX, rockButtonY, white);
        }

        if (inPaper) {
            rm.getImage("-GAME-_PAPER_BUTTON").draw(paperButtonX, paperButtonY, shade);
        } else {
            rm.getImage("-GAME-_PAPER_BUTTON").draw(paperButtonX, paperButtonY, white);
        }

        if (inScissors) {
            rm.getImage("-GAME-_SCISSORS_BUTTON").draw(scissorsButtonX, scissorsButtonY, shade);
        } else {
            rm.getImage("-GAME-_SCISSORS_BUTTON").draw(scissorsButtonX, scissorsButtonY, white);
        }
    }

    private void renderVerdict() {
        //draw the verdict borders
        char dec = decision.charAt(1);
        if (dec == 'w') {
            rm.getImage("-GAME-_WIN_BORDER").draw(verdictCompX, verdictCompY);
            rm.getImage("-GAME-_LOSE_BORDER").draw(verdictUserX, verdictUserY);
        } else if (dec == 'l') {
            rm.getImage("-GAME-_LOSE_BORDER").draw(verdictCompX, verdictCompY);
            rm.getImage("-GAME-_WIN_BORDER").draw(verdictUserX, verdictUserY);
        } else if (dec == 't') {
            rm.getImage("-GAME-_TIE_BORDER").draw(verdictCompX, verdictCompY);
            rm.getImage("-GAME-_TIE_BORDER").draw(verdictUserX, verdictUserY);
        }
        //draw the user's move
        this.drawMove('u');
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        switch (currentState) {
            case PLAYER_CHOOSE:
                this.updatePlayerChoose(container, game, delta);
                break;
            case VERDICT:
                //no longer the first game
                firstGame = false;
                this.updateVerdict(container, game, delta);
        }

        //logic governing the menu button
        Input in = container.getInput();
        int mouseX = in.getMouseX();
        int mouseY = in.getMouseY();
        if (this.inButton(mouseX, mouseY, (int) menuX, (int) menuY, rm.getImage("-GAME-_MENU_BUTTON"))) {
            inMenu = true;
        } else {
            inMenu = false;
        }

        if (inMenu) {
            if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                game.enterState(RPSMain.MENUSTATE);
                rm.getSound("CLICK_SOUND").play();
                //normalize the current game state
                currentState = GAMESTATES.PLAYER_CHOOSE;    
                //clear the information about the game
                rpsGame.reset();
                firstGame = true;
            }
        }
        //logic governing rest button
        if (this.inButton(mouseX, mouseY, (int) resetX, (int) resetY, rm.getImage("-GAME-_RESET_BUTTON"))) {
            inReset = true;
        } else {
            inReset = false;
        }
        
        if (inReset) {
            if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                rm.getSound("CLICK_SOUND").play();
                //normalize the current game state
                currentState = GAMESTATES.PLAYER_CHOOSE;    
                //clear the information about the game
                rpsGame.reset();
                firstGame = true;
            }
        }
               
    }
    //if the game is in the player choose state

    private void updatePlayerChoose(GameContainer container, StateBasedGame game, int delta) {
        Input in = container.getInput();
        int mouseX = in.getMouseX();
        int mouseY = in.getMouseY();
        if (this.inButton(mouseX, mouseY, (int) rockButtonX, (int) rockButtonY, rm.getImage("-GAME-_ROCK_BUTTON"))) {
            inRock = true;
        } else {
            inRock = false;
        }

        if (this.inButton(mouseX, mouseY, (int) paperButtonX, (int) paperButtonY, rm.getImage("-GAME-_PAPER_BUTTON"))) {
            inPaper = true;
        } else {
            inPaper = false;
        }

        if (this.inButton(mouseX, mouseY, (int) scissorsButtonX, (int) scissorsButtonY, rm.getImage("-GAME-_SCISSORS_BUTTON"))) {
            inScissors = true;
        } else {
            inScissors = false;
        }

        //if in each and click button then make a new move
        if (inRock) {
            if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                decision = rpsGame.makeMove('r');
                userMove = 'r';
                currentState = GAMESTATES.VERDICT;
                //play correct sound
                this.playSound();
                //set the random pictures for each move
                this.setRandomMoveImages('r', decision.charAt(0));
            }
        } else if (inPaper) {
            if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                decision = rpsGame.makeMove('p');
                userMove = 'p';
                currentState = GAMESTATES.VERDICT;
                //play correct sound
                this.playSound();
                //set the random pictures for each move
                this.setRandomMoveImages('p', decision.charAt(0));
            }
        } else if (inScissors) {
            if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                decision = rpsGame.makeMove('s');
                userMove = 's';
                currentState = GAMESTATES.VERDICT;
                //play correct sound
                this.playSound();
                //set the random pictures for each move
                this.setRandomMoveImages('s', decision.charAt(0));
            }
        }

        //update the winrate
        if (rpsGame.validMoves == 0 && rpsGame.winRate == 0) {
            this.winRate = 0f;
        } else {
            this.winRate = (float) (1.0 - rpsGame.winRate);
        }
    }
    /**
     * check the decision of the game and play the corresponding sound
     */
    private void playSound() {
        char dec = decision.charAt(1);
        if (dec == 'w') {
            rm.getSound("BUZZER_SOUND").play();
        } if (dec == 'l') {
            rm.getSound("DING_SOUND").play();
        }
    }
    private void setRandomMoveImages(char userMove, char compMove) {
        Random r = new Random();
        if (userMove == 'r') {
            userRockImage = rockImages[r.nextInt(rockImages.length)];
        } else if (userMove == 'p') {
            userPaperImage = paperImages[r.nextInt(paperImages.length)];
        } else if (userMove == 's') {
            userScissorsImage = scissorsImages[r.nextInt(scissorsImages.length)];
        }

        if (compMove == 'r') {
            compRockImage = rockImages[r.nextInt(rockImages.length)];
        } else if (compMove == 'p') {
            compPaperImage = paperImages[r.nextInt(paperImages.length)];
        } else if (compMove == 's') {
            compScissorsImage = scissorsImages[r.nextInt(scissorsImages.length)];
        }
    }

    private void updateVerdict(GameContainer container, StateBasedGame game, int delta) {
        Input in = container.getInput();
        int mouseX = in.getMouseX();
        int mouseY = in.getMouseY();


        //if the button clicks on the user move picture then advance to player move state
        if (this.inButton(mouseX, mouseY, (int) userMoveImageX, (int) userMoveImageY, rockImage0)) {
            if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                currentState = GAMESTATES.PLAYER_CHOOSE;
                rm.getSound("CLICK_SOUND").play();
            }
        }

    }

    private boolean inButton(int mouseX, int mouseY, int buttonX, int buttonY, Image buttonImage) {
        if ((mouseX >= buttonX && mouseX <= buttonX + buttonImage.getWidth())
                && (mouseY >= buttonY && mouseY <= buttonY + buttonImage.getHeight())) {
            return true;

        }
        return false;
    }
}
