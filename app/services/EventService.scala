package services

import javax.inject.{Inject, Singleton}
import org.redisson.Redisson
import org.redisson.config.Config
import play.api.libs.json.{Json, JsValue}
import play.api.Configuration

import scala.util.Try

@Singleton
class EventService @Inject()(config: Configuration) {

  private val redisConf = config.underlying.getConfig("redis")
  private val redisHost = redisConf.getString("host")
  private val redisPort = redisConf.getInt("port")
  private val redissonConfig = new Config()
  redissonConfig.useSingleServer().setAddress(s"redis://$redisHost:$redisPort").setConnectionMinimumIdleSize(1)
  private val redisson = Redisson.create(redissonConfig)
  private val events = redisson.getDeque[String]("backend-exercise")

  def push(event: JsValue) = events.push(Json.stringify(event))

  def pop() = Try(Json.parse(events.pop)).toOption

}
