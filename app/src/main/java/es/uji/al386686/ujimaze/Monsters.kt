package es.uji.al386686.ujimaze

import es.uji.al386686.ujimaze.Model.Direction
import es.uji.al386686.ujimaze.Model.Position
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.jvilar.barbariangold.model.Maze
import kotlin.math.roundToInt
import kotlin.random.Random

class Monsters(var position: Position) {

    var xPos : Float = position.col + 0.5f
    var yPos : Float = position.row + 0.5f

    var direction: Direction = Direction.RIGHT

    companion object{
        private const val SPEED = 1f
    }

    fun move(deltaTime : Float, maze: Maze){
        xPos += SPEED * deltaTime * direction.col
        yPos += SPEED * deltaTime * direction.row
        
        if(!maze[position].hasWall(direction.turnRight()) ||
                !maze[position].hasWall(direction.turnLeft())){
            val newDirection = fixDirection(maze)
            if(direction != newDirection){
                toCenter()
                direction = newDirection
            }
        }
    }

    private fun fixDirection(maze: Maze): Direction {

    }

    private fun toCenter() {
        xPos = position.col + 0.5f
        yPos = position.row + 0.5f
    }
}