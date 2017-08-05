package controllers

import javax.inject._
//import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController extends Controller {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request: Request[AnyContent] =>
    Ok("<h1>Hello People</h1><h3>Enter your name with URL<h3>").as(HTML)
  }

  def startSession(name: String) = Action{
    implicit request =>
      Redirect(routes.HomeController.getSession).withSession("user" -> name)
  }

  def flashAction = Action{
    request => Ok(request.flash.get("success").getOrElse("<h1>No user found</h1>")).as(HTML)
  }

  def getSession = Action{
    request =>
      request.session.get("user").map { user =>
       Redirect(routes.HomeController.flashAction).flashing("success" -> s"<h2>Hello $user</h2>")
      }.getOrElse {
        Redirect(routes.HomeController.flashAction).flashing("success" -> "<h2>Session is not yet started</h2>")
      }.as(HTML)
  }


  /*def action2(user: String) = Action{
    Redirect(routes.HomeController.getSession).flashing(
      "success" -> s"<h1>Hii user $user</h1>")
  }

  def action3(id: Int) = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.getSession(s"with id: $id"))
  }*/


}
