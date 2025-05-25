package com.mygdx.game.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Map {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    public Map(String mapPath){
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.convertObjectToTileSpace = true;
        params.flipY = true;

        tiledMap = new TmxMapLoader().load("Map/MapGame.tmx",params);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1f);
    }

    public void render(OrthographicCamera camera){
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void dispose(){
        if(tiledMap != null) tiledMap.dispose();
        if(mapRenderer != null) mapRenderer.dispose();
    }

    public TiledMap getTiledMap(){
        return tiledMap;
    }
}
