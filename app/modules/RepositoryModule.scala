package modules

import com.google.inject.AbstractModule
import repositories.{JdbcUserRepository, UserRepository}

final class RepositoryModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserRepository]).to(classOf[JdbcUserRepository])
  }
}
