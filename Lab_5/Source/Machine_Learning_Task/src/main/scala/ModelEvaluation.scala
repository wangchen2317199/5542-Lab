import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.rdd.RDD

/**
 * Created by Chen Wang on 2/22/17.
 */
object ModelEvaluation {
  def evaluateModel(predictionAndLabels: RDD[(Double, Double)]) = {
    val metrics = new MulticlassMetrics(predictionAndLabels)
    val cfMatrix = metrics.confusionMatrix
    println(" |=================== Confusion matrix ==========================")
    println(cfMatrix)
    println(metrics.fMeasure)

  }
}
