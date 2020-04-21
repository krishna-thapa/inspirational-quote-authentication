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

/**
  * PasswordInfo DAO for storing passwords
  *
  */
class PasswordDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ec: ExecutionContext
) extends DelegableAuthInfoDAO[PasswordInfo]
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = {}

  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {}

  override def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {}

  override def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = { ??? }

  override def remove(loginInfo: LoginInfo): Future[Unit] = ???
}
