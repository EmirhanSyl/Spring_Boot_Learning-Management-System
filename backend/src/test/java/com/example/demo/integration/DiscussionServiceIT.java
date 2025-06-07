package com.example.demo.integration;

import com.example.demo.entity.Course;
import com.example.demo.entity.Discussion;
import com.example.demo.service.CourseService;
import com.example.demo.service.DiscussionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DiscussionServiceIT {

    @Autowired
    DiscussionService discussionService;
    @Autowired
    CourseService courseService;

    @Test
    void createDiscussion() {
        Course c = courseService.createCourse(new Course());

        Discussion d = new Discussion();
        d.setCourse(c); d.setuName("Ali"); d.setContent("Merhaba");
        discussionService.createDiscussion(d);

        assertThat(discussionService.getDiscussionsCourse(c))
                .singleElement()
                .extracting(Discussion::getContent).isEqualTo("Merhaba");
    }
}
