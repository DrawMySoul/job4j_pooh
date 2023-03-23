package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class TopicServiceTest {

    @Test
    void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
            new Req("GET", "topic", "weather", paramForSubscriber1)
        );

        topicService.process(
            new Req("POST", "topic", "weather", paramForPublisher)
        );

        Resp result1 = topicService.process(
            new Req("GET", "topic", "weather", paramForSubscriber1)
        );

        Resp result2 = topicService.process(
            new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result2.text()).isEmpty();
    }

    @Test
    void whenTwoSubscribeAndTwoPostThenGet() {
        TopicService topicService = new TopicService();
        String paramForPublisher1 = "temperature=18";
        String paramForPublisher2 = "temperature=19";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
            new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
            new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        topicService.process(
            new Req("POST", "topic", "weather", paramForPublisher1)
        );
        topicService.process(
            new Req("POST", "topic", "weather", paramForPublisher2)
        );
        Resp result1 = topicService.process(
            new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
            new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        Resp result3 = topicService.process(
            new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result4 = topicService.process(
            new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result1.status()).isEqualTo("200");
        assertThat(result2.text()).isEqualTo("temperature=18");
        assertThat(result2.status()).isEqualTo("200");
        assertThat(result3.text()).isEqualTo("temperature=19");
        assertThat(result3.status()).isEqualTo("200");
        assertThat(result4.text()).isEqualTo("temperature=19");
        assertThat(result4.status()).isEqualTo("200");
    }
}