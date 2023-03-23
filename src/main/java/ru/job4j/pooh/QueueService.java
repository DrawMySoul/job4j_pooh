package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp result;
        if (HttpRequest.POST.getRequest().equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).add(req.getParam());
            result = new Resp(req.getParam(), HttpStatusCode.OK.getStatus());
        } else if (HttpRequest.GET.getRequest().equals(req.httpRequestType())
            && queue.get(req.getSourceName()) != null
            && !queue.get(req.getSourceName()).isEmpty()
        ) {
            result = new Resp(queue.get(req.getSourceName()).poll(), HttpStatusCode.OK.getStatus());
        } else {
            result = new Resp(req.getSourceName(), HttpStatusCode.NO_CONTENT.getStatus());
        }
        return result;
    }
}
