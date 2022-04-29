package es.uji.al386686.ujimaze

import es.uji.al386686.ujimaze.Model.Direction
import es.uji.al386686.ujimaze.Model.Position
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.jvilar.barbariangold.model.Maze
import java.util.ArrayList as ArrayList

class MainModel {
    var maze : Maze = Levels.all[0]
    private set

    var princess:Princess
    private set

    var direction:Direction = Direction.RIGHT

    val monsters : ArrayList<Monsters> = addMonsters()

    private fun addMonsters(): ArrayList<Monsters> {
        val monstersAux = ArrayList<Monsters>()

        for(p in maze.enemyOrigins){
            monstersAux.add(Monsters(p))
        }
        return monstersAux
    }

    init {
        princess = Princess(maze.origin.col+0.5f,maze.origin.row+0.5f,direction, Position(maze.origin.col, maze.origin.row),true)
    }

    fun update(deltaTime: Float) {
        princess.move(deltaTime,maze)

        for (m in monsters){
            m.move(deltaTime,maze)
        }
    }

    fun changePrincessDirection(nextDirection: Direction) {
        princess.changeDirection(nextDirection,maze)
    }
}