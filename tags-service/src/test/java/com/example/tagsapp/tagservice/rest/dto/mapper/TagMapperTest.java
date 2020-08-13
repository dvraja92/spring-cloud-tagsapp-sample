package com.example.tagsapp.tagservice.rest.dto.mapper;

import com.example.tagsapp.tagservice.domain.model.Tag;
import com.example.tagsapp.tagservice.rest.dto.TagDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TagMapperTest {

    private final TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    @Test
    public void givenTagToTagDTO_whenMaps_thenCorrect() {
        // Arrange
        Tag tag = new Tag();
        tag.setKey("key");
        tag.setValue("value");

        // Act
        TagDto dto = tagMapper.toDto(tag);

        // Assert
        Assertions.assertEquals(tag.getKey(), dto.getKey());
        Assertions.assertEquals(tag.getValue(), dto.getValue());
    }

    @Test
    public void givenTagDTOToTag_whenMaps_thenCorrect() {
        // Arrange
        TagDto tagDto = new TagDto();
        tagDto.setKey("key");
        tagDto.setValue("value");

        // Act
        Tag tag = tagMapper.fromDto(tagDto);

        // Assert
        Assertions.assertEquals(tag.getKey(), tagDto.getKey());
        Assertions.assertEquals(tag.getValue(), tagDto.getValue());
    }

}
