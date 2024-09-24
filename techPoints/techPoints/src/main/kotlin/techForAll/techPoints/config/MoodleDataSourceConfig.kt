import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class MoodleDbConfig {

    @Bean(name = ["moodleDataSourceProperties"])
    @ConfigurationProperties("spring.datasource.moodle")
    fun moodleDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean(name = ["moodleDataSource"])
    fun moodleDataSource(@Qualifier("moodleDataSourceProperties") properties: DataSourceProperties): DataSource {
        return properties.initializeDataSourceBuilder().build()
    }
}
