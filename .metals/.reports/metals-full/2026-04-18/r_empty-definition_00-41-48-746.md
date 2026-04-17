error id: file://<WORKSPACE>/app/repositories/UserRepository.scala:User.
file://<WORKSPACE>/app/repositories/UserRepository.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -javax/inject/User.
	 -javax/inject/User#
	 -javax/inject/User().
	 -slick/jdbc/PostgresProfile.api.User.
	 -slick/jdbc/PostgresProfile.api.User#
	 -slick/jdbc/PostgresProfile.api.User().
	 -models/User.
	 -models/User#
	 -models/User().
	 -User.
	 -User#
	 -User().
	 -scala/Predef.User.
	 -scala/Predef.User#
	 -scala/Predef.User().
offset: 149
uri: file://<WORKSPACE>/app/repositories/UserRepository.scala
text:
```scala
package repositories
import javax.inject._
import scala.concurrent.{Future, ExecutionContext}
import slick.jdbc.PostgresProfile.api._
import models.U@@ser
@singleton
class UserRepository @Inject() (db: Database)(implicit ec: ExecutionContext) {
  class UserTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", o.PrimaryKey, o.AutoInc)

  }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 