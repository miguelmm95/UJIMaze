package es.uji.al386686.ujimaze

import es.uji.al386686.ujimaze.Model.Direction
import es.uji.al386686.ujimaze.Model.Position
import es.uji.jvilar.barbariangold.model.Maze

class MainModel {
    var maze : Maze
    private set

    var princess:Princess
    private set

    var direction:Direction

    init {
        direction = Direction.RIGHT
        maze = Levels.all[0]
        princess = Princess(maze.origin.col+0.5f,maze.origin.row+0.5f,direction)
    }

    fun update(deltaTime: Float) {
        princess.move(deltaTime)
    }
}