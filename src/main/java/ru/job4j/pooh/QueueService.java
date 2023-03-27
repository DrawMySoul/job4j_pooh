package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp result = new Resp(req.getSourceName(), HttpStatusCode.NO_CONTENT.getStatus());
        if (HttpRequest.POST.getRequest().equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).add(req.getParam());
            result = new Resp(req.getParam(), HttpStatusCode.OK.getStatus());
        } else if (HttpRequest.GET.getRequest().equals(req.httpRequestType())) {
            var text = queue.getOrDefault(req.getSourceName(), new ConcurrentLinkedQueue<>()).poll();
            result = new Resp(
                text == null ? req.getSourceName() : text,
                text == null ? HttpStatusCode.NO_CONTENT.getStatus() : HttpStatusCode.OK.getStatus()
            );
        }
        return result;
    }
}
