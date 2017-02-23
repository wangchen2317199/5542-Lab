import java.io.File

import org.bytedeco.javacpp.opencv_core.{Size, Mat}
import org.bytedeco.javacpp.opencv_highgui._

/**
  * Created by Chen Wang on 2/22/17.
  */
object ResizingImage {

  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","C:\\winutils")
    val IMAGE_CATEGORIES = List("knife", "spoon", "fork")
    val PATH = "data/test/"
    val PATH2 = "data/test2/"
    IMAGE_CATEGORIES.foreach(f => {
      val file = new File(PATH + f)
      val listOffiles = file.listFiles()
      val file2 = new File(PATH2 + f)
      file2.mkdirs()
      var count = 1
      listOffiles.foreach(fi => {
        println(fi.getPath)
        val img = imread(fi.getPath, CV_LOAD_IMAGE_UNCHANGED)
        var src = new Mat
        org.bytedeco.javacpp.opencv_imgproc.resize(img,src,new Size(img.rows()/2,img.cols()/2))


        imwrite(file2.getPath + "/" + count + ".jpg", src)
        count = count + 1
      })
    })
  }
}
