package com.example.tagsapp.tagservice.service.impl;

import com.example.tagsapp.tagservice.domain.model.Tag;
import com.example.tagsapp.tagservice.domain.repo.TagRepository;
import com.example.tagsapp.tagservice.exception.EntityNotFoundException;
import com.example.tagsapp.tagservice.service.TagService;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    private TagService tagService;

    @BeforeEach
    public void init() {
        tagService = new TagServiceImpl(tagRepository);
    }

    @Test
    public void whenList_thenReturn() {
        // Arrange
        List<Tag> list = new ArrayList<>();
        when(tagRepository.findAll()).thenReturn(list);

        // Act
        List<Tag> tags = tagService.list();

        // Assert
        Assertions.assertEquals(Lists.newArrayList(list).size(), tags.size());
    }

    @Test
    public void whenValidGetTag_thenReturn() {
        // Arrange
        final Long id = 1L;
        Tag mockedTag = new Tag();
        mockedTag.setId(id);
        mockedTag.setKey("key");
        mockedTag.setValue("value");
        when(tagRepository.findById(id)).thenReturn(Optional.of(mockedTag));

        // Act
        Tag tag = tagService.get(id);

        // Assert
        Assertions.assertEquals(mockedTag.getId(), tag.getId());
        Assertions.assertEquals(mockedTag.getKey(), tag.getKey());
        Assertions.assertEquals(mockedTag.getValue(), tag.getValue());
    }

    @Test
    public void whenInvalidGetTag_thenThrow() {
        // Arrange
        when(tagRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> tagService.get(1L));
    }

    @Test
    public void whenSaveValidTag_thenReturn() {
        // Arrange
        final Tag mockTag = new Tag();
        when(tagRepository.save(any(Tag.class))).thenAnswer(invocationOnMock -> {
            mockTag.setId(1L);
            return mockTag;
        });

        // Act
        Tag tag = tagService.save(mockTag);

        // Assert
        Assertions.assertEquals(mockTag.getId(), tag.getId());
        Assertions.assertNotNull(tag.getCreatedAt());
        Assertions.assertNotNull(tag.getUpdatedAt());

    }

    @Test
    public void whenUpdateValidTag_theReturn() {
        // Arrange
        final Long id = 1L;
        final Tag mockTag = new Tag();
        mockTag.setId(id);
        mockTag.setKey("key");
        mockTag.setValue("value");
        when(tagRepository.findById(any())).thenReturn(Optional.of(mockTag));
        when(tagRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // Act
        Tag tag = tagService.update(id, mockTag);

        // Assert
        Assertions.assertEquals(mockTag.getId(), tag.getId());
        Assertions.assertEquals(mockTag.getKey(), tag.getKey());
        Assertions.assertEquals(mockTag.getValue(), tag.getValue());
        Assertions.assertNotNull(tag.getUpdatedAt());
    }

    @Test
    public void whenUpdateInvalidTag_thenThrow() {
        // Arrange
        final Long id = 1L;
        final Tag mockTag = new Tag();
        when(tagRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> tagService.update(id, mockTag));

    }

}
