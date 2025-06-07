package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.DiscussionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscussionServiceTest {

    @Mock DiscussionRepository repo;
    @InjectMocks DiscussionService service;

    @Test
    void getDiscussionsCourse_returnsList() {
        Course c = new Course();
        Discussion d = new Discussion();
        when(repo.findByCourse(c)).thenReturn(List.of(d));

        assertThat(service.getDiscussionsCourse(c)).hasSize(1);
    }

    @Test
    void createDiscussion_savesEntity() {
        Discussion d = new Discussion();
        when(repo.save(d)).thenReturn(d);

        assertThat(service.createDiscussion(d)).isSameAs(d);
    }
}
