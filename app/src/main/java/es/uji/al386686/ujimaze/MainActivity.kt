package es.uji.al386686.ujimaze

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.util.DisplayMetrics
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController

class MainActivity : GameActivity(), MainModel.SoundPlayer {

    private var width = 0
    private var height = 0
    private var cellSize: Int = 0
    private var offSetX: Int = 0
    private var offSetY: Int = 0

    private val model = MainModel(this)
    private lateinit var soundPool: SoundPool
    private lateinit var controller: Controller

    private lateinit var graphics: Graphics

    private var coinId = 0
    private var hit1Id = 0
    private var hit2Id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landscapeFullScreenOnCreate()
        prepareSoundPool(this)
    }

    private fun prepareSoundPool(context: Context) {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(attributes)
            .build()
        coinId = soundPool.load(context, R.raw.coineffect, 0)
        hit1Id = soundPool.load(context, R.raw.hiteffect, 0)
        hit2Id = soundPool.load(context, R.raw.hiteffect2, 0)

    }

    override fun buildGameController(): IGameController {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        controller = Controller(
            displayMetrics.widthPixels,
            displayMetrics.heightPixels, model, this
        )
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
        if (model.state == GameStates.GAME) {
            drawPrincess()
        } else {
            drawGameOverHUD()
        }

        return graphics.frameBuffer
    }

    private fun drawGameOverHUD() {
        graphics.setTextSize(150)
        graphics.setTextColor(Color.RED)
        graphics.drawRect(
            (width / 4).toFloat(),
            (height / 4).toFloat(),
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            Color.WHITE
        )
        graphics.drawText((width / 4).toFloat() + 25f, (height / 4).toFloat() + 200f, "GAME OVER")
        graphics.setTextSize(75)
        graphics.setTextColor(Color.BLUE)
        graphics.drawText(
            (width / 4).toFloat() + 225f,
            (height / 4).toFloat() + 350f,
            "Press to start"
        )
    }

    fun calculateMeasures(model: MainModel) {
        cellSize = height / model.maze.nRows
        offSetX = (width - model.maze.nCols * cellSize) / 2
        offSetY = (height - model.maze.nRows * cellSize) / 2
    }

    fun drawMaze() {
        for (row in 0 until model.maze.nRows) {
            for (col in 0 until model.maze.nCols) {
                if (model.maze[row, col].type == CellType.WALL) {
                    graphics.drawRect(
                        ((col * cellSize) + offSetX).toFloat(),
                        ((row * cellSize) + offSetY).toFloat(),
                        cellSize.toFloat(),
                        cellSize.toFloat(),
                        Color.BLUE
                    )
                }
                if (model.maze[row, col].type == CellType.HOME) {
                    drawMonsters()
                }
                if (model.maze[row, col].type == CellType.POTION && !model.maze[row, col].used) {
                    graphics.drawRect(
                        ((col * cellSize) + offSetX).toFloat(),
                        ((row * cellSize) + offSetY).toFloat(),
                        cellSize.toFloat(),
                        cellSize.toFloat(),
                        Color.GREEN
                    )
                }
                if (model.maze[row, col].type == CellType.GOLD && !model.maze[row, col].used) {
                    graphics.drawCircle(
                        (col + 0.5f) * cellSize + offSetX,
                        (row + 0.5f) * cellSize + offSetY,
                        (cellSize / 6).toFloat(),
                        Color.YELLOW
                    )
                }
                if (model.maze[row, col].type == CellType.DOOR) {
                    graphics.drawRect(
                        ((col * cellSize) + offSetX).toFloat(),
                        ((row * cellSize) + offSetY).toFloat(),
                        cellSize.toFloat(),
                        cellSize.toFloat() / 2,
                        Color.BLACK
                    )
                }
            }
        }
    }

    fun drawPrincess() {
        if (!model.princess.hasPotion) {
            graphics.drawCircle(
                (model.princess.xPos * cellSize) + offSetX,
                (model.princess.yPos * cellSize) + offSetY,
                (cellSize / 2).toFloat(),
                Color.CYAN
            )
        } else {
            graphics.drawCircle(
                (model.princess.xPos * cellSize) + offSetX,
                (model.princess.yPos * cellSize) + offSetY,
                (cellSize / 2).toFloat(),
                Color.GREEN
            )
        }
    }

    fun drawMonsters() {
        for (m in model.monsters) {
            graphics.drawRect(
                (m.xPos - 0.5f) * cellSize + offSetX,
                (m.yPos - 0.5f) * cellSize + offSetY,
                cellSize.toFloat(),
                cellSize.toFloat(),
                Color.RED
            )
        }
    }

    override fun playCoinEffect() {
        soundPool.play(coinId, 0.9f, 0.9f, 0, 0, 1f)
    }

    override fun playHit1Effect() {
        soundPool.play(hit1Id, 0.9f, 0.9f, 0, 0, 1f)
    }

    override fun playHit2Effect() {
        soundPool.play(hit2Id, 0.9f, 0.9f, 0, 0, 1f)
    }
}