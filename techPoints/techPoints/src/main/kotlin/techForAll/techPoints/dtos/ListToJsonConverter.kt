package techForAll.techPoints.dtos

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter


@Converter(autoApply = true)
class ListToJsonConverter : AttributeConverter<List<Long>, String> {

    private val objectMapper = ObjectMapper()

    override fun convertToDatabaseColumn(attribute: List<Long>?): String {
        return objectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): List<Long> {
        return dbData?.let {
            objectMapper.readValue(it, Array<Long>::class.java).toList()
        } ?: emptyList()
    }
}
