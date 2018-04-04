import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import java.util.Scanner

import javax.imageio.ImageIO

object Main extends App {
  val scanner = new Scanner(System.in)
  while (true) {
    println("Enter a file path or url of an image")
    val input = scanner.nextLine()
    val isUrl = input.startsWith("http:") || input.startsWith("https:")
    val bufferedImage: BufferedImage =
      if (isUrl) {
        ImageIO.read(new URL(input))
      } else {
        ImageIO.read(new File(input))
      }
    val gImage = new GImage(bufferedImage)
    val chars = gImage.convertToChars()
    chars.foreach { row =>
      row.foreach(col => print(col))
      println
    }
    gImage.writeToFile()
  }
}
