package com.example.tagsapp.tagservice.rest.dto.mapper;

import com.example.tagsapp.tagservice.domain.model.Tag;
import com.example.tagsapp.tagservice.rest.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

@Mapper(componentModel = "spring", nullValueMappingStrategy = RETURN_DEFAULT)
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper( TagMapper.class );

    TagDto toDto(Tag tag);

    Tag fromDto(TagDto tagDto);
}
