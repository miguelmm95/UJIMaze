package es.uji.al386686.ujimaze

import android.util.Log
import es.uji.al386686.ujimaze.Model.Direction
import es.uji.al386686.ujimaze.Model.Position
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.jvilar.barbariangold.model.Maze
import kotlin.math.roundToInt
import kotlin.random.Random

class Monsters(var position: Position) {

    var xPos: Float = position.col + 0.5f
    var yPos: Float = position.row + 0.5f

    var direction: Direction = Direction.RIGHT

    companion object {
        private const val SPEED = 3f
    }

    fun move(deltaTime: Float, maze: Maze) {
        xPos += SPEED * deltaTime * direction.col
        yPos += SPEED * deltaTime * direction.row

        var newPosition = Position((yPos - 0.5f).roundToInt(), (xPos - 0.5f).roundToInt())

        if (maze[newPosition].type == CellType.WALL) {
            toCenter()
            direction = fixDirection(maze)
        } else {
            if (position != newPosition){
                position = newPosition
                if (!maze[position].hasWall(direction.turnRight()) || !maze[position].hasWall(direction.turnLeft())) {
                    val newDirection = fixDirection(maze)
                    if (direction != newDirection) {
                        toCenter()
                        direction = newDirection
                    }
                }
            }
        }
    }

    fun resetMonsters(maze: Maze, initialPosition: Position) {
        xPos = initialPosition.col + 0.5f
        yPos = initialPosition.row + 0.5f
        direction = fixDirection(maze)

    }

    private fun fixDirection(maze: Maze): Direction {
        val directionList: ArrayList<Direction> = ArrayList()

        if (!maze[position].hasWall(Direction.UP) && Direction.UP != direction.opposite()) {
            directionList.add(Direction.UP)
        }

        if (!maze[position].hasWall(Direction.LEFT) && Direction.LEFT != direction.opposite()) {
            directionList.add(Direction.LEFT)
        }

        if (!maze[position].hasWall(Direction.RIGHT) && Direction.RIGHT != direction.opposite()) {
            directionList.add(Direction.RIGHT)
        }

        if (!maze[position].hasWall(Direction.DOWN) && Direction.DOWN != direction.opposite()) {
            directionList.add(Direction.DOWN)
        }

        return if (directionList.isEmpty()) {
            direction.opposite()
        } else {
            directionList.random()
        }
    }

    private fun toCenter() {
        xPos = position.col + 0.5f
        yPos = position.row + 0.5f
    }
}