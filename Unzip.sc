import java.io.{File, FileInputStream}
import java.io.{FileOutputStream, InputStream}
import java.nio.file.{Path, Paths}
import java.util.zip.ZipInputStream


object Test {

  def unzip(zipFile: InputStream, destination: Path): Unit = {
    val zis = new ZipInputStream(zipFile)

    Stream.continually(zis.getNextEntry).takeWhile(_ != null).foreach { file =>
      if (!file.isDirectory) {
        val outPath = destination.resolve(file.getName)
        val outPathParent = outPath.getParent
        if (!outPathParent.toFile.exists()) {
          outPathParent.toFile.mkdirs()
        }

        val outFile = outPath.toFile
        val out = new FileOutputStream(outFile)
        val buffer = new Array[Byte](4096)
        Stream.continually(zis.read(buffer)).takeWhile(_ != -1).foreach(out.write(buffer, 0, _))
      }
    }
  }


  val file = new File("/Users/Jiawen/Desktop/a.zip")
  val fileIn = new FileInputStream(file)
  val zipIn = new ZipInputStream(fileIn)

  val path = Paths.get("/Users/Jiawen/Desktop/unzip")
  unzip(fileIn, path)

}