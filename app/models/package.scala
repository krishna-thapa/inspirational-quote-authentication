package object models {

  import slick.lifted.ProvenShape

  import slick.jdbc.MySQLProfile.api._
  import slick.lifted.Tag
  import com.mohiva.play.silhouette.api.Identity

  case class User(
      id: Option[Int],
      firstName: Option[String],
      lastName: Option[String],
      email: Option[String],
      providerId: String,
      providerKey: String
  ) extends Identity

  class Users(tag: Tag) extends Table[User](tag, "USER") {
    def id          = column[Option[Int]]("USER_ID", O.PrimaryKey, O.AutoInc)
    def firstName   = column[Option[String]]("FIRST_NAME")
    def lastName    = column[Option[String]]("LAST_NAME")
    def email       = column[Option[String]]("EMAIL")
    def providerId  = column[String]("PROVIDER_ID")
    def providerKey = column[String]("PROVIDER_KEY")

    def * : ProvenShape[User] =
      (id, firstName, lastName, email, providerId, providerKey) <> (User.tupled, User.unapply)
  }

  case class Password(key: String, hasher: String, hash: String, salt: Option[String])

  class Passwords(tag: Tag) extends Table[Password](tag, "PASSWORD") {
    def key    = column[String]("PROVIDER_KEY", O.PrimaryKey)
    def hasher = column[String]("HASHER")
    def hash   = column[String]("HASH")
    def salt   = column[Option[String]]("SALT")

    def * = (key, hasher, hash, salt) <> (Password.tupled, Password.unapply)
  }

  val userTable     = TableQuery[Users]
  val passwordTable = TableQuery[Passwords]
}
