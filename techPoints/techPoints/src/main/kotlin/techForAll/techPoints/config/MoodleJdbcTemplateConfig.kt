package techForAll.techPoints.config

import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class MoodleJdbcTemplateConfig(
    val moodleDataSource: DataSource
) {

    @Bean(name = ["moodleJdbcTemplate"])
    fun moodleJdbcTemplate(): JdbcTemplate {
        return JdbcTemplate(moodleDataSource)
    }
}
