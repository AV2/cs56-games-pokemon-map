package edu.ucsb.cs56.S12.sbaldwin.pokemon;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by steven on 6/30/16.
 */
public class World {

    private int[][] tiles; // Change this to tile class later when merge occurs
    private HashSet<Entity> entities; // Change to our storage of ecs later

    // Basic skeleton for world class
    public World(int[][] tiles, HashSet<Entity> entites) {
        this.tiles = tiles;
        this.entities = entities;
	gg = new GameGrid(200, 200);
    }

    public World(int[][] tiles) {
	this.tiles = tiles;
	entites = new HashSet<Entity>();
    }

    public void addEntity(Entity e) {
	entities.add(e);
    }


}
