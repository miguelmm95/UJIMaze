package es.uji.al386686.ujimaze

import android.util.Log
import es.uji.al386686.ujimaze.Model.Direction
import es.uji.al386686.ujimaze.Model.Position
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.jvilar.barbariangold.model.Maze
import kotlin.math.roundToInt

class Princess(var xPos : Float, var yPos : Float, var direction: Direction, var position: Position, var isMoving : Boolean) {

    companion object{
        private const val SPEED = 1.5f
    }

    fun move(deltaTime: Float, maze: Maze) {
        if (!isMoving){
            return
        }

        xPos  += SPEED * deltaTime * direction.col
        yPos += SPEED * deltaTime * direction.row

        var newPosition = Position((yPos - 0.5).roundToInt(),(xPos - 0.5).roundToInt())

        if(maze[newPosition].type == CellType.WALL || maze[newPosition].type == CellType.DOOR){
            toCenter()
            isMoving = false
        }else{
            position = newPosition
            isMoving = true
        }

        coinDetection(maze)
    }

    fun changeDirection(nextDirection: Direction, maze: Maze) {
        if (direction != nextDirection && !maze[position].hasWall(nextDirection)
                && maze[position.translate(nextDirection)].type != CellType.DOOR){
            toCenter()
            isMoving = true
            direction = nextDirection
        }
    }

    fun coinDetection(maze:Maze){
        if(maze[position].type == CellType.GOLD && !maze[position].used){
            maze[position].used = true
        }
    }

    private fun toCenter() {
        xPos = position.col + 0.5f
        yPos = position.row + 0.5f
    }
}