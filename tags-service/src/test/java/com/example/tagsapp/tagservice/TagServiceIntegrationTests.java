package com.example.tagsapp.tagservice;

import com.example.tagsapp.tagservice.domain.model.Tag;
import com.example.tagsapp.tagservice.rest.dto.TagDto;
import com.example.tagsapp.tagservice.rest.dto.mapper.TagMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TagServiceIntegrationTests {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagMapper tagMapper;

    @BeforeAll
    public static void setup() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testAddTag() throws Exception {

        TagDto tagDto = new TagDto();
        tagDto.setKey("key1");
        tagDto.setValue("value1");

        ResultActions resultActions = mockMvc.perform(post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tagDto))
        ).andExpect(status().isOk());

        // Verify response content matches
        Tag responseTag = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), Tag.class);

        Assertions.assertNotNull(responseTag.getId());
        Assertions.assertEquals(tagDto.getKey(), responseTag.getKey());
        Assertions.assertEquals(tagDto.getValue(), responseTag.getValue());

        // Verify Tag is Persisted
        mockMvc.perform(get("/{id}", responseTag.getId())).andExpect(status().isOk());

    }

    @Test
    public void testUpdateTag() throws Exception {
        TagDto tagDto = new TagDto();
        tagDto.setKey("key2");
        tagDto.setValue("value2");

        String result = mockMvc.perform(post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tagDto))
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        // Grab response content to change
        Tag responseTag = objectMapper.readValue(result, Tag.class);
        responseTag.setKey("key22");
        responseTag.setValue("value22");

        // Perform PUT action
        result = mockMvc.perform(put("/{id}", responseTag.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tagMapper.toDto(responseTag)))
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        // Grab the response
        responseTag = objectMapper.readValue(result, Tag.class);

        // Verify Tag is updated
        String updatedResponseString = mockMvc.perform(get("/{id}", responseTag.getId())).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Tag updatedResponse = objectMapper.readValue(updatedResponseString, Tag.class);


        // Assert
        Assertions.assertEquals(updatedResponse.getKey(), responseTag.getKey());
        Assertions.assertEquals(updatedResponse.getValue(), responseTag.getValue());
        Assertions.assertEquals(updatedResponse.getId(), responseTag.getId());

    }

    @Test
    public void testDeleteTag() throws Exception {
        TagDto tagDto = new TagDto();
        tagDto.setKey("key3");
        tagDto.setValue("value3");

        ResultActions resultActions = mockMvc.perform(post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tagDto))
        ).andExpect(status().isOk());

        // Verify response content matches
        Tag responseTag = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), Tag.class);

        // Delete and Verify HTTP Status
        mockMvc.perform(delete("/{id}", responseTag.getId())
        ).andExpect(status().isAccepted());

        // Verify data is deleted
        mockMvc.perform(get("/{id}", responseTag.getId())).andExpect(status().isNotFound());
    }

    @Test
    public void testListTags() throws Exception {

        // Find the initial size
        String listContent = mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        int initialSize = objectMapper.readValue(listContent, Tag[].class).length;

        // Add some data to it
        TagDto tagDto = new TagDto();
        tagDto.setKey("key41");
        tagDto.setValue("value41");

        mockMvc.perform(post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tagDto))
        ).andExpect(status().isOk());

        tagDto = new TagDto();
        tagDto.setKey("key42");
        tagDto.setValue("value42");

        mockMvc.perform(post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tagDto))
        ).andExpect(status().isOk());


        // Find the size again
        listContent = mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<Tag> tagList = Arrays.asList(objectMapper.readValue(listContent, Tag[].class));

        // Assert
        Assertions.assertNotEquals(initialSize, tagList.size());
        Assertions.assertEquals(initialSize + 2, tagList.size());

    }

}
