error id: file://<WORKSPACE>/app/repositories/UserRepository.scala:scala.
file://<WORKSPACE>/app/repositories/UserRepository.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -javax/inject/scala.
	 -scala/scala.
	 -slick/jdbc/PostgresProfile.api.scala.
	 -scala.
	 -scala/Predef.scala.
offset: 51
uri: file://<WORKSPACE>/app/repositories/UserRepository.scala
text:
```scala
package repositories
import javax.inject._
import s@@cala.* 
import scala.concurrent.{Future, ExecutionContext}
import slick.jdbc.PostgresProfile.api._
import models.User
@singleton
class UserRepository @Inject() (db: Database)(implicit ec: ExecutionContext) {
  class UserTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", o.PrimaryKey, o.AutoInc)

  }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 