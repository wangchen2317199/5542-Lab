import org.apache.spark.mllib.linalg.{DenseVector, Vector}
import org.bytedeco.javacpp.opencv_core.{CV_32FC1, Mat}
import org.bytedeco.javacpp.opencv_features2d.{BOWImgDescriptorExtractor, DescriptorExtractor, FlannBasedMatcher, KeyPoint}
import org.bytedeco.javacpp.opencv_highgui.{CV_LOAD_IMAGE_GRAYSCALE, imread}
import org.bytedeco.javacpp.opencv_nonfree.SIFT
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by Chen Wang on 02/15/2017.
  */
object ImageUtils {

  def descriptors(file: String): Mat = {
    val img_1 = imread(file, CV_LOAD_IMAGE_GRAYSCALE)
    if (img_1.empty()) {
      println("Image is empty")
      -1
    }

    val detector = new SIFT()
    val keypoints_1 = new KeyPoint

    val mask = new Mat
    val descriptors = new Mat

    detector.detectAndCompute(img_1, mask, keypoints_1, descriptors)

    println(s"Key Descriptors ${descriptors.rows()} x ${descriptors.cols()}")
    descriptors
  }

  def bowDescriptors(file: String, dictionary: Mat): Mat = {
    val matcher = new FlannBasedMatcher()
    val detector = new SIFT()
    val extractor = DescriptorExtractor.create("SIFT")
    val bowDE = new BOWImgDescriptorExtractor(extractor, matcher)
    bowDE.setVocabulary(dictionary)
    println(bowDE.descriptorSize() + " " + bowDE.descriptorType())

    val img = imread(file, CV_LOAD_IMAGE_GRAYSCALE)
    if (img.empty()) {
      println("Image is empty")
      -1
    }

    val keypoints = new KeyPoint

    detector.detect(img, keypoints)

    val response_histogram = new Mat
    bowDE.compute(img, keypoints, response_histogram)

    println("Histogram size : " + response_histogram.size().asCvSize().toString)
    println("Histogram : " + response_histogram.asCvMat().toString)
    response_histogram
  }

  def matToVector(mat: Mat): Vector = {
    val imageCvmat = mat.asCvMat()

    val noOfCols = imageCvmat.cols()

    val imageInDouble = new Array[Double](noOfCols)
    for (col <- 0 to noOfCols - 1) {
      val pixel = imageCvmat.get(0, col)
      imageInDouble(col) = pixel
    }
    val featureVector = new DenseVector(imageInDouble)
    featureVector
  }

  def matToVectors(mat: Mat): Array[Vector] = {
    val imageCvmat = mat.asCvMat()

    val noOfCols = imageCvmat.cols()
    val noOfRows = imageCvmat.rows()

    val fVectors = new ArrayBuffer[DenseVector]()

    for (row <- 0 to noOfRows - 1) {
      val imageInDouble = new Array[Double](noOfCols)
      for (col <- 0 to noOfCols - 1) {
        val pixel = imageCvmat.get(row, col)
        imageInDouble :+ pixel
      }
      val featureVector = new DenseVector(imageInDouble)
      fVectors :+ featureVector
    }

    fVectors.toArray
  }

  def matToDoubles(mat: Mat): Array[Array[Double]] = {
    val imageCvmat = mat.asCvMat()

    val noOfCols = imageCvmat.cols()
    val noOfRows = imageCvmat.rows()

    val fVectors = new ArrayBuffer[Array[Double]]()

    for (row <- 0 to noOfRows - 1) {
      val imageInDouble = new Array[Double](noOfCols)
      for (col <- 0 to noOfCols - 1) {
        val pixel = imageCvmat.get(row, col)
        imageInDouble :+ pixel
      }
      fVectors :+ imageInDouble
    }
    fVectors.toArray
  }

  def matToString(mat: Mat): List[String] = {
    val imageCvmat = mat.asCvMat()

    val noOfCols = imageCvmat.cols()
    val noOfRows = imageCvmat.rows()

    val fVectors = new mutable.MutableList[String]

    for (row <- 0 to noOfRows - 1) {
      val vecLine = new StringBuffer("")
      for (col <- 0 to noOfCols - 1) {
        val pixel = imageCvmat.get(row, col)
        vecLine.append(pixel + " ")
      }

      fVectors += vecLine.toString
    }
    fVectors.toList
  }

  def vectorsToMat(centers: Array[Vector]): Mat = {

    val vocab = new Mat(centers.size, centers(0).size, CV_32FC1)

    var i = 0
    for (c <- centers) {

      var j = 0
      for (v <- c.toArray) {
        vocab.asCvMat().put(i, j, v)
        j += 1
      }
      i += 1
    }
    vocab
  }
}