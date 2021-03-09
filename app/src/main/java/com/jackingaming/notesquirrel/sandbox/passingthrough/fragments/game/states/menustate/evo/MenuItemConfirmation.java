package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.evo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

public class MenuItemConfirmation
        implements MenuStateImplEvo.MenuItem {
    private static MenuItemConfirmation uniqueInstance;
    transient private Game game;
    private String name;

    private MenuItemConfirmation() {
        name = "Confirmation";
    }

    public static MenuItemConfirmation getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MenuItemConfirmation();
        }
        return uniqueInstance;
    }

    @Override
    public void init(Game game) {
        this.game = game;
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    @Override
    public void update(long elapsed) {

    }

    @Override
    public void render(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        Rect rect = new Rect(0, 0, 48, 48);
        canvas.drawRect(rect, paint);
//        //background textbox.
//        g.setColor(Color.GRAY);
//        g.fillRect(30, (handler.panelHeight/2)+150,
//                Assets.mainMenu.getWidth()+10, 30);
//        //text.
//        g.setColor(Color.WHITE);
//        String confirmationMessage = null;
//        switch (currentIndexEvolutionMenu) {
//            case JAWS:
//                confirmationMessage = "Spend " + FishStateManager.Jaws.values()[index].getCost() +
//                        " experience point(s) for " + FishStateManager.Jaws.values()[index] + " Jaws?";
//                break;
//            case BODY:
//                if (index <= (FishStateManager.BodyTexture.values().length-1)) {
//                    confirmationMessage = "Spend " + FishStateManager.BodyTexture.values()[index].getCost() +
//                            " experience point(s) for " + FishStateManager.BodyTexture.values()[index] + " BodyTexture?";
//                }
//                else if (index > (FishStateManager.BodyTexture.values().length-1)) {
//                    confirmationMessage = "Spend " + FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)].getCost() +
//                            " experience point(s) for " + FishStateManager.BodySize.values()[index-(FishStateManager.BodyTexture.values().length)] + " BodySize?";
//                }
//                break;
//            case HANDS_AND_FEET:
//                confirmationMessage = "Spend " + FishStateManager.FinPectoral.values()[index].getCost() +
//                        " experience point(s) for " + FishStateManager.FinPectoral.values()[index] + " FinPectoral?";
//                break;
//            case TAIL:
//                confirmationMessage = "Spend " + FishStateManager.Tail.values()[index].getCost() +
//                        " experience point(s) for " + FishStateManager.Tail.values()[index] + " Tail?";
//                break;
//            default:
//                System.out.println("MainMenuState(MenuList.CONFIRMATION).render(Graphics) switch(IndexEvolutionMenu)'s default.");
//                break;
//        }
//        g.drawString(confirmationMessage, 30+3, (handler.panelHeight/2)+150+11);
//        g.drawString("yes", 43, (handler.panelHeight/2)+150+25);
//        g.drawString("no", 93, (handler.panelHeight/2)+150+25);
//        //cursor image: leftOverworld0.
//        overWorldCursor.render(g);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Game getGame() {
        return game;
    }
}