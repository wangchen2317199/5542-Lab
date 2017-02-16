import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.rdd.RDD

/**
  * Created by Chen Wang on 02/15/2017.
  */
object ModelEvaluation {
  def evaluateModel(predictionAndLabels: RDD[(Double, Double)]) = {
    val metrics = new MulticlassMetrics(predictionAndLabels)
    val cfMatrix = metrics.confusionMatrix
    println("=================== Confusion Matrix ==========================")
    println(cfMatrix)
    println(metrics.fMeasure)
    scala.tools.nsc.io.File("data/Output.txt").writeAll("=================== Confusion Matrix ==========================\n")
    scala.tools.nsc.io.File("data/Output.txt").appendAll(cfMatrix.toString() + '\n')
    scala.tools.nsc.io.File("data/Output.txt").appendAll("Accuracy: " + metrics.fMeasure)
  }
}