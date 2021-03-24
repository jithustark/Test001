package CATS.RocktheJVM.DataManipulation

object Reader extends App {

  /*
  Configuration File: intial Data Structure
  DB Layer
  an Http Layer
  a Business Layer
   */

  case class Config(DBUserName: String, DBPassword: String, host: String, port: Int, nThreads: Int)
  case class DBConection(username: String, password: String) {
    def getOrderStatus(orderID: Long): String = "Dispatched"
  }

  case class HttpService(host: String, port: Int){
    def start():Unit = println("server started")
  }

//bootstrap
  val config = Config("Hello", "rock", "star", 54, 12)


  // Reader is a data processing Type used in cats to make a connection

    // cats Reader
  import cats.data.Reader
  val dbReader: Reader[Config, DBConection] = Reader(conf => DBConection(conf.DBUserName,conf.DBPassword))
  val dbconn = dbReader.run(config)

  // Reader[I, O]




}
