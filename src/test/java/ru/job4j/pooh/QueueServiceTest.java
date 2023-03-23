package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class QueueServiceTest {

    @Test
    void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
            new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
            new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text()).isEqualTo("temperature=18");
    }

    @Test
    void whenPostLessThenGet() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        String paramForPostMethod1 = "temperature=19";
        queueService.process(
            new Req("POST", "queue", "weather", paramForPostMethod)
        );
        queueService.process(
            new Req("POST", "queue", "weather", paramForPostMethod1)
        );
        Resp result = queueService.process(
            new Req("GET", "queue", "weather", null)
        );
        Resp result1 = queueService.process(
            new Req("GET", "queue", "weather", null)
        );
        Resp result2 = queueService.process(
            new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text()).isEqualTo("temperature=18");
        assertThat(result.status()).isEqualTo("200");
        assertThat(result1.text()).isEqualTo("temperature=19");
        assertThat(result1.status()).isEqualTo("200");
        assertThat(result2.text()).isEqualTo("weather");
        assertThat(result2.status()).isEqualTo("204");
    }

    @Test
    void whenQueueDoesNotExist() {
        QueueService queueService = new QueueService();
        Resp result = queueService.process(
            new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text()).isEqualTo("weather");
        assertThat(result.status()).isEqualTo("204");
    }

    @Test
    void whenWrongQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
            new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
            new Req("GET", "queue", "buses", null)
        );
        assertThat(result.text()).isEqualTo("buses");
        assertThat(result.status()).isEqualTo("204");
    }
}