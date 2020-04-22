package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.api.LoginInfo

import scala.reflect.ClassTag

/**
  * PasswordInfo DAO for storing passwords
  */
class PasswordDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ec: ExecutionContext
) extends DelegableAuthInfoDAO[PasswordInfo]
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = {
    val result = db
      .run(
        passwordTable
          .filter(password => password.key === loginInfo.providerKey)
          .result
          .headOption
      )
      .map(
        _.map(password => PasswordInfo(password.hasher, password.hash, password.salt))
      )
    result
  }

  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    db.run(
        passwordTable += Password(
          loginInfo.providerKey,
          authInfo.hasher,
          authInfo.password,
          authInfo.salt
        )
      )
      .map((res: Int) => {
        authInfo
      })
  }

  override def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    val q = for {
      password <- passwordTable if password.key === loginInfo.providerKey
    } yield (password.hasher, password.hash, password.salt)

    db.run(q.update(authInfo.hasher, authInfo.password, authInfo.salt)).map(_ => authInfo)
  }

  override def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    find(loginInfo).flatMap {
      case Some(_) => update(loginInfo, authInfo)
      case None    => add(loginInfo, authInfo)
    }
  }

  override def remove(loginInfo: LoginInfo): Future[Unit] = {
    db.run(
        passwordTable.filter(password => password.key === loginInfo.providerKey).delete
      )
      .map(i => ())
  }

  override val classTag: ClassTag[PasswordInfo] = scala.reflect.classTag[PasswordInfo]
}
