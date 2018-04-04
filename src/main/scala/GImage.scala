import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

class GImage(private val bufferedImage: BufferedImage) {
  val chars = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. "

  def convertToChars(): Array[Array[Char]] = {
    convertToGray()
    Array.tabulate(bufferedImage.getHeight, bufferedImage.getWidth)((y, x) => {
      val avg: Double = bufferedImage.getRGB(x, y) & 0xFF
      chars.charAt(Math.floor(avg / 3.645).asInstanceOf[Int])
    })
  }

  def writeToFile(): Unit = {
    ImageIO.write(bufferedImage, "jpg", new File("grayscale.jpg"))
  }

  private def convertToGray(): Unit = {
    val height = bufferedImage.getHeight
    val width = bufferedImage.getWidth
    for (y <- 0 until height; x <- 0 until width) {
      val rgb = Math.abs(bufferedImage.getRGB(x, y))
      val blue = rgb & 0xFF
      val green = (rgb >> 8) & 0xFF
      val red = (rgb >> 16) & 0xFF

      val average = (blue + green + red) / 3

      val blueNew = average
      val greenAndBlueNew = blueNew | (average << 8)
      val redGreenAndBlueNew = greenAndBlueNew | (average << 16)
      bufferedImage.setRGB(x, y, redGreenAndBlueNew)
    }
  }
}
