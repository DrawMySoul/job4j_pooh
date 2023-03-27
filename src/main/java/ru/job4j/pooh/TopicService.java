package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final Map<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topic = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp result = new Resp("", HttpStatusCode.NO_CONTENT.getStatus());
        if (HttpRequest.GET.getRequest().equals(req.httpRequestType())) {
            topic.putIfAbsent(req.getParam(), new ConcurrentHashMap<>());
            topic.get(req.getParam()).putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            var text = topic.get(req.getParam()).get(req.getSourceName()).poll();
            result = new Resp(
                text == null ? "" : text,
                HttpStatusCode.OK.getStatus()
            );
        } else if (HttpRequest.POST.getRequest().equals(req.httpRequestType())) {
            topic.values().forEach(m -> m.computeIfPresent(req.getSourceName(), (key, value) -> {
                value.add(req.getParam());
                return value;
            }));
            result = new Resp("", HttpStatusCode.OK.getStatus());
        }
        return result;
    }
}
