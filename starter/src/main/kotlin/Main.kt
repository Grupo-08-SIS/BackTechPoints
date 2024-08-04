
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["tech4all.techpoints"])
class TechPointsApplication

fun main(args: Array<String>) {
    runApplication<TechPointsApplication>(*args)
}
