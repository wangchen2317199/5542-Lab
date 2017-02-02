

import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by Mayanka on 09-Sep-15.
  */
object SparkWordCount {

  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir","C:\\winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc=new SparkContext(sparkConf)

    val input=sc.textFile("input")

    val wc=input.flatMap(line=>{line.split(" ")}).map(word=>(word.replace(",",""),1)).cache()

    val output=wc.reduceByKey(_+_)

    val commonClass = output.collect().filter(f => f._2 == 2)

    var number = 0

    commonClass.foreach(f => number += 1)

    println("How many classes that David and Emily bothe enrolled?\nAnswer: " + number)
  }

}
