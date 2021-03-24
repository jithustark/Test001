package FP

import java.io.{File, PrintWriter}
import scala.collection.mutable

object FS2_Streams extends App
{
  def readAndWriteFile(readFrom: String, writeTo: String) = {
    val counts = mutable.Map.empty[String, Int]
    val fileSource = scala.io.Source.fromFile(readFrom)
    try {
      fileSource
        .getLines()
        .toList
        .flatMap { word =>
          counts += (word -> (counts.getOrElse(word, 0) + 1))
        }
    }
    finally{
      fileSource.close()
    }

    val fileContent = counts.foldLeft("") {
      case(accumulator, (word, count)) =>
        accumulator + s"word = $count\n"

    }

    println(fileContent)
    val writer = new PrintWriter(new File(writeTo))
    writer.write(fileContent)
    writer.close()
  }

  readAndWriteFile("Data/sample.txt","Data/sample1.txt")
}
