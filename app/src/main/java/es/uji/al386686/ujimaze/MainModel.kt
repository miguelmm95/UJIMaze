package es.uji.al386686.ujimaze

import android.media.SoundPool
import android.util.Log
import es.uji.al386686.ujimaze.Model.Direction
import es.uji.al386686.ujimaze.Model.Position
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.jvilar.barbariangold.model.Maze
import kotlin.math.abs
import kotlin.system.exitProcess
import java.util.ArrayList as ArrayList

class MainModel(private val soundPlayer: SoundPlayer) {

    interface SoundPlayer {
        fun playCoinEffect()
    }

    var level = 1
    var maze: Maze = Levels.all[level - 1]
        private set

    var princess: Princess
        private set

    val monsters: ArrayList<Monsters> = addMonsters()

    private fun addMonsters(): ArrayList<Monsters> {
        val monstersAux = ArrayList<Monsters>()

        for (p in maze.enemyOrigins) {
            monstersAux.add(Monsters(p))
        }
        return monstersAux
    }

    init {
        princess = Princess(maze.origin.col + 0.5f, maze.origin.row + 0.5f, Position(maze.origin.col, maze.origin.row), true)
    }

    fun update(deltaTime: Float) {

        checkCollisions()

        if (princess.coinCollected) {
            soundPlayer.playCoinEffect()
            princess.coinCollected = false
        }

        if (princess.coinsCollected == maze.gold) {
            if (level == Levels.all.size) {
                restartLevel()
            } else {
                level += 1
                maze = Levels.all[level - 1]
            }
        } else {
            princess.move(deltaTime, maze)

            for (m in monsters) {
                m.move(deltaTime, maze)
            }
        }
    }

    fun changePrincessDirection(nextDirection: Direction) {
        princess.changeDirection(nextDirection, maze)
    }

    fun restartLevel() {
        princess.resetPrincess(maze)
        resetAllMonsters()
        maze.reset()
    }

    fun resetAllMonsters() {
        for (i in 0 until monsters.size) {
            monsters[i].resetMonsters(maze, maze.enemyOrigins[i])
        }
    }

    fun checkCollisions() {
        for (i in 0 until monsters.size) {
            if (abs(princess.xPos - monsters[i].xPos) < 0.4f && abs(princess.yPos - monsters[i].yPos) < 0.4f) {
                if (princess.hasPotion) {
                    monsters[i].position = maze.enemyOrigins[i]
                    monsters[i].xPos = maze.enemyOrigins[i].col + 0.5f
                    monsters[i].yPos = maze.enemyOrigins[i].row + 0.5f
                } else {
                    princess.dead(maze)
                    resetAllMonsters()
                }
            }
        }
    }
}