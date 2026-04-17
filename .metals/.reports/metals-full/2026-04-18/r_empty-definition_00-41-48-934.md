error id: file://<WORKSPACE>/app/repositories/UserRepository.scala:ExecutionContext.
file://<WORKSPACE>/app/repositories/UserRepository.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -javax/inject/ExecutionContext.
	 -javax/inject/ExecutionContext#
	 -javax/inject/ExecutionContext().
	 -scala/concurrent/ExecutionContext.
	 -scala/concurrent/ExecutionContext#
	 -scala/concurrent/ExecutionContext().
	 -slick/jdbc/PostgresProfile.api.ExecutionContext.
	 -slick/jdbc/PostgresProfile.api.ExecutionContext#
	 -slick/jdbc/PostgresProfile.api.ExecutionContext().
	 -ExecutionContext.
	 -ExecutionContext#
	 -ExecutionContext().
	 -scala/Predef.ExecutionContext.
	 -scala/Predef.ExecutionContext#
	 -scala/Predef.ExecutionContext().
offset: 90
uri: file://<WORKSPACE>/app/repositories/UserRepository.scala
text:
```scala
package repositories
import javax.inject._
import scala.concurrent.{Future, ExecutionConte@@xt}
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