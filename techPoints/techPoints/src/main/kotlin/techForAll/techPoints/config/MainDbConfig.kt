import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["techForAll.techPoints.repository"], // Pacote onde estão os repositórios da mainDB
    entityManagerFactoryRef = "mainEntityManagerFactory",
    transactionManagerRef = "mainTransactionManager"
)
class MainDbConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.main")
    fun mainDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    fun mainDataSource(): DataSource {
        return mainDataSourceProperties().initializeDataSourceBuilder().build()
    }

    @Bean
    fun mainEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val factory = LocalContainerEntityManagerFactoryBean()
        factory.dataSource = mainDataSource()
        factory.setPackagesToScan("techForAll.techPoints.domain") // Pacote das entidades do mainDb
        factory.jpaVendorAdapter = HibernateJpaVendorAdapter()
        return factory
    }

    @Bean
    fun mainTransactionManager(): JpaTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = mainEntityManagerFactory().`object`
        return transactionManager
    }
}
