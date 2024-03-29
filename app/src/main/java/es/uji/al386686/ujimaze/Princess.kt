package es.uji.al386686.ujimaze

import es.uji.al386686.ujimaze.Model.Direction
import es.uji.al386686.ujimaze.Model.Position
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.jvilar.barbariangold.model.Maze
import kotlin.math.roundToInt

class Princess(var xPos: Float, var yPos: Float, var position: Position, var isMoving: Boolean) {

    var direction: Direction = Direction.RIGHT
    var coinsCollected: Int = 0
    var hasPotion: Boolean = false
    var coinCollected : Boolean = false
    var potionTime: Float = 0f
    var lifes : Int = INITIAL_LIFES
    var isDead : Boolean = false

    companion object {
        private const val SPEED = 5f
        private const val POTION_DURATION = 5f
        private const val INITIAL_LIFES = -1
    }

    fun move(deltaTime: Float, maze: Maze) {

        checkPotion(deltaTime)

        if (!isMoving) {
            return
        }

        xPos += SPEED * deltaTime * direction.col
        yPos += SPEED * deltaTime * direction.row

        var newPosition = Position((yPos - 0.5).roundToInt(), (xPos - 0.5).roundToInt())

        if (maze[newPosition].type == CellType.WALL || maze[newPosition].type == CellType.DOOR) {
            toCenter()
            isMoving = false
        } else {
            position = newPosition
            isMoving = true
        }

        coinDetection(maze)
        potionDetection(maze)
    }

    fun changeDirection(nextDirection: Direction, maze: Maze) {
        if (direction != nextDirection && !maze[position].hasWall(nextDirection)
                && maze[position.translate(nextDirection)].type != CellType.DOOR) {
            toCenter()
            isMoving = true
            direction = nextDirection
        }
    }

    private fun coinDetection(maze: Maze) {
        if (maze[position].type == CellType.GOLD && !maze[position].used) {
            maze[position].used = true
            coinsCollected += 1
            coinCollected = true
        }
    }

    private fun potionDetection(maze: Maze) {
        if (maze[position].type == CellType.POTION && !maze[position].used) {
            maze[position].used = true
            hasPotion = true
        }
    }

    fun resetPrincess(maze: Maze) {
        xPos = maze.origin.col + 0.5f
        yPos = maze.origin.row + 0.5f
        coinsCollected = 0
        hasPotion = false
        potionTime = 0f
        direction = Direction.RIGHT
    }

    fun dead(maze: Maze) {
        xPos = maze.origin.col + 0.5f
        yPos = maze.origin.row + 0.5f
        hasPotion = false
        potionTime = 0f
        direction = Direction.RIGHT

        checkLifes()
    }

    private fun checkLifes(){
        if(lifes < 0){
            isDead = true
        }else{
            lifes -= 1
        }
    }

    private fun checkPotion(deltaTime: Float) {
        if (hasPotion) {
            potionTime += deltaTime
            if (potionTime >= POTION_DURATION) {
                hasPotion = false
                potionTime = 0f
            }
        }
    }

    private fun toCenter() {
        xPos = position.col + 0.5f
        yPos = position.row + 0.5f
    }
}