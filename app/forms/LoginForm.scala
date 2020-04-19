package forms

import play.api.data.{ Form, Forms }
import play.api.data.Forms._

/**
  * Helper to manage HTML form description, submission and validation.
  * https://www.playframework.com/documentation/2.8.x/ScalaForms
  */
object LoginForm {
  val form = Form(
    mapping(
      "email"    -> email,
      "password" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

  case class Data(email: String, password: String)
}
