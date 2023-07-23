package com.enducube.testing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TestCool extends ApplicationAdapter {
	OrthographicCamera camera;
	Viewport viewport;
	SpriteBatch batch;
	BitmapFont font;
	FreeTypeFontGenerator generator;
	FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	ShapeRenderer shapeRenderer;
	Vector2 circle_pos = new Vector2();
	Vector2 mouse_pos = new Vector2();
	Vector2 start_drag_pos = new Vector2(0,0);
	@Override
	public void create() {

		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);
		batch = new SpriteBatch();
		// font garbage!!!
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/NimbusSanL-Bol.otf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 24;
		font = generator.generateFont(parameter);
		font.setColor(Color.GOLD);

		// fun shapes!
		shapeRenderer = new ShapeRenderer();

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		batch.setProjectionMatrix(camera.combined);
		mouse_pos.x = Gdx.input.getX();
		mouse_pos.y = Gdx.input.getY();
		Vector3 h = camera.unproject(new Vector3(mouse_pos.x,mouse_pos.y,0));
		// checking input like NERD
		if (Gdx.input.justTouched()) {
			Vector3 temp = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
			start_drag_pos = new Vector2(temp.x,temp.y);
		}
		if (Gdx.input.isTouched()) {
			circle_pos.x = Gdx.input.getX();
			circle_pos.y = Gdx.input.getY();
			Vector3 ht = camera.unproject(new Vector3(circle_pos.x,circle_pos.y,0));
			circle_pos = new Vector2(ht.x,ht.y);
			System.out.println(circle_pos);
		}

		// actually rendering stuff
		batch.begin();
		font.draw(batch, h.x + ", " + h.y, 10, 30);
		batch.end();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		if (Gdx.input.isTouched()) {
			shapeRenderer.setColor(Color.BLUE);
			shapeRenderer.line(start_drag_pos,new Vector2(h.x,h.y));
		}
		shapeRenderer.circle(h.x,h.y,15);
		shapeRenderer.circle(circle_pos.x,circle_pos.y,10);
		shapeRenderer.end();




	}
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		System.out.println("h");
	}
	@Override
	public void dispose () {
		font.dispose();
		batch.dispose();
	}
}
