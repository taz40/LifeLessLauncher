package lightsoutgaming.lifeless.launcher.main;

import java.awt.Graphics2D;

import lightsoutgaming.lifeless.launcher.textures.textureclass;

import taz40.lightsoutgamingengine.V1.Button;
import taz40.lightsoutgamingengine.V1.Function;
import taz40.lightsoutgamingengine.V1.Screen;
import taz40.lightsoutgamingengine.V1.ScreenFactory;

public class yesnoscreen extends Screen {

	public yesnoscreen(ScreenFactory screenfactory) {
		super(screenfactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCustomCreate() {
		// TODO Auto-generated method stub
		int ButtonPressed = screenfactory.getGame().texturerenderer.LoadTexture("gui/Button_Pressed.png", textureclass.class);
		int ButtonUnpressed = screenfactory.getGame().texturerenderer.LoadTexture("gui/Button.png", textureclass.class);
		
		this.addEntity(new Button(this, "Yes", 20, 100, 100, 50, 2, ButtonPressed, ButtonUnpressed, new Function(this) {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				LauncherMain.install();
				screenfactory.getGame().Exit();
			}
			
		}));
		
		this.addEntity(new Button(this, "No", 180, 100, 100, 50, 2, ButtonPressed, ButtonUnpressed, new Function(this) {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				LauncherMain.launch();
				screenfactory.getGame().Exit();
			}
			
		}));
	}

	@Override
	public void onCustomDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCustomDraw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.scale(2, 2);
		g.drawString("Would You Like To Update?", 0, 30);
	}

	@Override
	public void onCustomUpdate() {
		// TODO Auto-generated method stub

	}

}
