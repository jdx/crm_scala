package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.Future

import reactivemongo.api._
import play.modules.reactivemongo._
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.json._

import play.api.Play.current

object Customers extends Controller with MongoController {
  
  def collection: JSONCollection = db.collection[JSONCollection]("customers")

  def index = Action {
    Async {
      val cursor: Cursor[JsObject] = collection.find(Json.obj()).cursor[JsObject]
      val futureCustomersList: Future[List[JsObject]] = cursor.toList
      val futureCustomersJsonArray: Future[JsArray] =
        futureCustomersList.map { customers => Json.arr(customers) }
      futureCustomersJsonArray.map { customers => Ok(customers) }
    }
  }
  
}