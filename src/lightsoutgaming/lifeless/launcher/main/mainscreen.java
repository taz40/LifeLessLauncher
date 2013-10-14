package lightsoutgaming.lifeless.launcher.main;

import java.awt.Graphics2D;

import lightsoutgaming.lifeless.launcher.textures.textureclass;

import taz40.lightsoutgamingengine.V1.Button;
import taz40.lightsoutgamingengine.V1.Function;
import taz40.lightsoutgamingengine.V1.Screen;
import taz40.lightsoutgamingengine.V1.ScreenFactory;

public class mainscreen extends Screen {

	public mainscreen(ScreenFactory screenfactory) {
		super(screenfactory);
		// TODO Auto-generated constructor stub
	}

	int ButtonPressed, ButtonUnpressed;
	
	@Override
	public void onCustomCreate() {
		
		ButtonPressed = screenfactory.getGame().texturerenderer.LoadTexture("gui/Button_Pressed.png", textureclass.class);
		ButtonUnpressed = screenfactory.getGame().texturerenderer.LoadTexture("gui/Button.png", textureclass.class);
		
		this.addEntity(Button.createXCenteredButton("Play", 300, 100, 50, 2, ButtonPressed, ButtonUnpressed, new Function(this) {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				LauncherMain.update();
				screenfactory.getGame().Exit();
			}
			
		}, this));
		
		this.addEntity(Button.createXCenteredButton("Exit", 500, 100, 50, 2, ButtonPressed, ButtonUnpressed, new Function(this) {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				screenfactory.getGame().Exit();
			}
			
		}, this));
		
	}

	@Override
	public void onCustomDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCustomDraw(Graphics2D arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCustomUpdate() {
		// TODO Auto-generated method stub
		
	}
	
}
