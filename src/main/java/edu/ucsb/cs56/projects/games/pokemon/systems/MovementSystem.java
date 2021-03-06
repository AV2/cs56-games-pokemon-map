package edu.ucsb.cs56.projects.games.pokemon.systems;

import edu.ucsb.cs56.projects.games.pokemon.Vector2;
import edu.ucsb.cs56.projects.games.pokemon.World;
import edu.ucsb.cs56.projects.games.pokemon.components.CollisionComponent;
import edu.ucsb.cs56.projects.games.pokemon.components.Entity;
import edu.ucsb.cs56.projects.games.pokemon.components.MovementComponent;
import edu.ucsb.cs56.projects.games.pokemon.components.PositionComponent;
import edu.ucsb.cs56.projects.games.pokemon.framework.Ref;
import edu.ucsb.cs56.projects.games.pokemon.graphics.Animation;

/**
 * Handles all movement
 *
 * @author William Bennett
 */
public class MovementSystem extends SystemBase {

    /**
     * Handles movement and applies it a delta time forward
     *
     * @param deltaT the delta time
     * @param world the world
     */
    public void applyMovement(float deltaT, World world) {
        Entity[] entities = world.getEntities();
        Entity[][] tiles = world.tiles;
        handleMessages();
        for (Entity E : entities) {
            PositionComponent PC = (PositionComponent)E.getComponent(PositionComponent.class);
            MovementComponent MC = (MovementComponent)E.getComponent(MovementComponent.class);
            
            if ( MC == null) 
            	continue;
	    
           
            PositionComponent NextPosition = PC;
            
            //checks direction and then location player is headed and if no collision issues, moves the player
            if(MC.getDirection() == MovementComponent.Direction.West) {
            	NextPosition.position.x = PC.position.x - 1;
            	if(overLaps(NextPosition, entities, tiles)) {
            		PC.position.x++;
            	}
            }
	
            if(MC.getDirection() == MovementComponent.Direction.East) {
            	NextPosition.position.x = PC.position.x + 1;
            	if(overLaps(NextPosition, entities, tiles)) {
            		PC.position.x--;
            	}
            }
	
            if(MC.getDirection() == MovementComponent.Direction.North) {
            	NextPosition.position.y = PC.position.y + 1;
            	if(overLaps(NextPosition, entities, tiles)) {
            		PC.position.y--;
            	}
            }
	
            if(MC.getDirection() == MovementComponent.Direction.South) {
            	NextPosition.position.y = PC.position.y - 1;
            	if(overLaps(NextPosition, entities, tiles)) {
            		PC.position.y++;
            	}
            }

            PC = null;
            MC = null;
            NextPosition = null;
      
        }
        entities = null;
        tiles = null;
           
    }    
	
    /**
     * A method to check to see if one entity overlaps another
     *
     * @param PC1 the position component of the moving object
     * @param entities the list of the world's entities
     * @param tiles the array of tiles in the world
     * @return a boolean that determines whether an entity overlaps another entity with collision
     */
    public boolean overLaps(PositionComponent PC1, Entity[] entities, Entity[][] tiles) {


        //checks all the entities in the world
        for (Entity object : entities) {
            PositionComponent PC2 = (PositionComponent)object.getComponent(PositionComponent.class);
            CollisionComponent CC2 = (CollisionComponent)object.getComponent(CollisionComponent.class);

         
     	//checks if moving entity's next position is inside the hitbox of the other entity
           if (( ((PC1.position.x) <= ((PC2.position.x) + CC2.width)) &&
                    ((PC1.position.x) >= ((PC2.position.x))) &&
                    ((PC1.position.y) >= ((PC2.position.y))) &&
                    ((PC1.position.y) <= ((PC2.position.y + CC2.height))) && CC2.hasCollision)) {
                return true;
            }
          
         
        }

        //checks all the tiles in the world
        for (Entity[] object : tiles) {
            for (Entity object2 : object) {
                PositionComponent PC2 = (PositionComponent) object2.getComponent(PositionComponent.class);
                CollisionComponent CC2 = (CollisionComponent) object2.getComponent(CollisionComponent.class);

		
                //checks if moving entity's next position is inside the hitbox of the tile
                if (((PC1.position.x) < ((PC2.position.x) + CC2.width)) &&
                        ((PC1.position.x) > ((PC2.position.x))) &&
                        ((PC1.position.y) > ((PC2.position.y))) &&
                        ((PC1.position.y) < ((PC2.position.y + CC2.height))) && CC2.hasCollision) {
                    return true;
                }
                

                //checks if moving entity's next position is outside the bounds of the map
                if (  (PC1.position.x >= 41) ||  (PC1.position.y >= 40)   ) {
                    return true;
                }

                if (  (PC1.position.x <= -1) ||  (PC1.position.y <= -1)   ) {
                    return true;
                }

                else {
                    continue;
                }
            }
        }
        return false;

    }

    /**
     * Handles the message for a movement change
     */
    @Override
    protected void handleMessages() {
        while (!messenger.isEmpty()) {
            SystemMessage msg = messenger.dequeue();
            Ref<Object> value = new Ref<>();
            if (msg.getMessage("movement-changed", value)) {
                MovementComponent MC = (MovementComponent)msg.subject.getComponent(MovementComponent.class);
                Vector2 vel = (Vector2) value.reference;
                MC.velocity = vel;
            }
            else {
                messenger.enqueue(msg);
            }
            messenger.merge();
        }
    }
}
