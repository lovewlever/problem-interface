package com.problem.pl.commons

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.OutputStream
import java.util.Random

import javax.imageio.ImageIO

class VerifyCode {
    private val width = 90
    private val high = 40
    private val random = Random()
    private val fonts = arrayOf("宋体", "微软雅黑", "TimesRoman", "Cambria")
    private val chars = "1234567890"
    private val operator = "+-x"
    private val backColor = Color.white
    private val text: String? = null
    private var no1: Int = 0
    private var no2: Int = 0
    private var op: String? = null

    //获取图片缓存
    //获取操作数s1，绘制
    //获取操作符oper，绘制
    //获取操作数s2，绘制
    //绘制 等号和问号
    //绘制干扰线
    //返回BufferedImage图片
    val image: BufferedImage
        get() {
            val bi = BufferedImage(width, high, BufferedImage.TYPE_INT_RGB)
            val g2 = bi.graphics as Graphics2D
            g2.color = this.backColor
            g2.fillRect(0, 0, 90, 40)
            val sb = StringBuilder()
            val s1 = randomNum()
            this.no1 = Integer.parseInt(s1)
            val p1 = 5.0f
            sb.append(s1)
            drawString(g2, s1, p1)
            val oper = randomOperator().trim { it <= ' ' }
            this.op = oper
            sb.append(oper)
            val p2 = 1.0f * width / 5
            drawString(g2, oper, p2)
            val s2 = randomNum()
            this.no2 = Integer.parseInt(s2)
            val p3 = 2.0f * width / 5
            sb.append(s2)
            drawString(g2, s2, p3)
            val calculate = "=?"
            val p4 = 3.0f * width / 5
            drawString(g2, calculate, p4)
            drawLine(bi)
            return bi
        }

    //获取算术的运算结果
    val value: Int
        get() {
            return when (op) {
                "+" -> no1 + no2
                "-" -> no1 - no2
                "x" -> no1 * no2
                else -> 0
            }
        }

    //获取随机颜色
    private fun randomColor(): Color {
        val red = random.nextInt(150)
        val green = random.nextInt(150)
        val blue = random.nextInt(150)
        return Color(red, green, blue)
    }

    //method：获取随机字体
    private fun randomFont(): Font {
        val index = random.nextInt(fonts.size)
        val fontName = fonts[index]
        val style = random.nextInt(4)
        val size = 24 + random.nextInt(4)

        return Font(fontName, style, size)
    }

    //method：获取随机操作符号
    private fun randomOperator(): String {
        return operator[2] + ""
    }

    //method：获取随机数字
    private fun randomNum(): String {
        val index = random.nextInt(chars.length)
        return chars[index] + ""
    }

    //method：添加干扰线
    private fun drawLine(image: BufferedImage) {
        val num = 3
        val graphic = image.graphics as Graphics2D
        for (i in 0 until num) {
            val x1 = random.nextInt(width)
            val x2 = random.nextInt(width)
            val y1 = random.nextInt(high)
            val y2 = random.nextInt(high)
            graphic.color = this.randomColor()
            graphic.stroke = BasicStroke(1.5f)
            graphic.drawLine(x1, y1, x2, y2)
        }
    }

    //method：绘制方法
    private fun drawString(graphics: Graphics2D, s: String, position: Float) {
        graphics.color = randomColor()
        graphics.font = randomFont()
        graphics.drawString(s, position, (high - 5).toFloat())
    }

    fun output(bi: BufferedImage, out: OutputStream) {
        ImageIO.write(bi, "JPEG", out)
    }
}