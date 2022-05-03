package es.uji.al386686.ujimaze

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController

class MainActivity : GameActivity() {

    private var width = 0
    private var height = 0

    var cellSize : Int = 0
    var offSetX : Int = 0
    var offSetY : Int = 0

    private val model = MainModel()
    private lateinit var controller: Controller

    private lateinit var graphics: Graphics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landscapeFullScreenOnCreate()
    }

    override fun buildGameController(): IGameController {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        controller = Controller(displayMetrics.widthPixels,
                displayMetrics.heightPixels, model, this)
        return controller
    }

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        this.width = width
        this.height = height
        graphics = Graphics(width, height)
    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(Color.GRAY)

        drawMaze()
        drawPrincess()

        return graphics.frameBuffer
    }

    fun calculateMeasures(model: MainModel){
        cellSize = height / model.maze.nRows
        offSetX = (width - model.maze.nCols * cellSize) / 2
        offSetY = (height - model.maze.nRows * cellSize) / 2
    }

    fun drawMaze() {

        //calculateMeasures(model)

        for (row in 0 until model.maze.nRows) {
            for (col in 0 until model.maze.nCols) {
                if (model.maze[row, col].type == CellType.WALL) {
                    graphics.drawRect(((col * cellSize) + offSetX).toFloat(), ((row * cellSize) + offSetY).toFloat(), cellSize.toFloat(), cellSize.toFloat(), Color.BLUE)
                }
                if (model.maze[row, col].type == CellType.HOME) {
                    drawMonsters()
                }
                if (model.maze[row, col].type == CellType.POTION && !model.maze[row, col].used) {
                    graphics.drawRect(((col * cellSize) + offSetX).toFloat(), ((row * cellSize) + offSetY).toFloat(), cellSize.toFloat(), cellSize.toFloat(), Color.GREEN)
                }
                if (model.maze[row, col].type == CellType.GOLD && !model.maze[row, col].used) {
                    graphics.drawCircle((col + 0.5f)  * cellSize + offSetX, (row + 0.5f) * cellSize + offSetY, (cellSize / 6).toFloat(), Color.YELLOW)
                }
            }
        }
    }

    fun drawPrincess() {
        if (!model.princess.hasPotion) {
            graphics.drawCircle(model.princess.xPos * cellSize + offSetX, model.princess.yPos * cellSize + offSetY, (cellSize / 2).toFloat(), Color.CYAN)
        } else {
            graphics.drawCircle(model.princess.xPos * cellSize + offSetX, model.princess.yPos * cellSize + offSetY, (cellSize / 2).toFloat(), Color.GREEN)
        }
    }

    fun drawMonsters() {
        for (m in model.monsters) {
            graphics.drawRect((m.xPos - 0.5f) * cellSize + offSetX, (m.yPos - 0.5f) * cellSize + offSetY, cellSize.toFloat(), cellSize.toFloat(), Color.RED)
        }
    }
}