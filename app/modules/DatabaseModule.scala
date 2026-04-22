package modules

import javax.inject._
import play.api.inject._
import org.flywaydb.core.Flyway
import com.typesafe.config.Config

// Module to register FlywayRunner
class DatabaseModule extends SimpleModule(
  bind[FlywayRunner].toSelf.eagerly() // run on startup
)

// Runner class
@Singleton
class FlywayRunner @Inject()(config: Config) {

  // Build Flyway instance using DB config
  private val flyway: Flyway = Flyway.configure()
    .dataSource(
      config.getString("slick.dbs.default.db.url"),
      config.getString("slick.dbs.default.db.user"),
      config.getString("slick.dbs.default.db.password")
    )
    .load()

  // Run migrations when class is initialized
  flyway.migrate()
}