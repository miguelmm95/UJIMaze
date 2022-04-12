package es.uji.al386686.ujimaze

import es.uji.jvilar.barbariangold.model.Maze

class MainModel {

    var maze : Maze
    private set

    init {
        maze = Levels.all[0]
    }

}