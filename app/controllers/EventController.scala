package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, JsValue, Reads}
import play.api.mvc.{AbstractController, ControllerComponents}
import services.EventService

import scala.concurrent.ExecutionContext

@Singleton
class EventController @Inject()(eventService: EventService, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def validateJson[A: Reads] = parse.json.validate {
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  }

  def post = Action(validateJson[JsValue]) { request =>
    eventService.push(request.body)
    Ok("Event queued!")
  }

  def get = Action {
    eventService.pop.map(Ok(_)).getOrElse(NotFound("No events!"))
  }

}
