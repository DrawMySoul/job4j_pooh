package ru.job4j.pooh;


public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] parse = content.split(System.lineSeparator());
        String[] lineParse = parse[0].split("\\s");
        String httpRequestType = lineParse[0];
        String poohMode = lineParse[1].split("/")[1];
        String sourceName = lineParse[1].split("/")[2];
        String param = "";
        if (HttpRequest.POST.getRequest().equals(httpRequestType)) {
            param = parse[7];
        } else if (HttpRequest.GET.getRequest().equals(httpRequestType) && "topic".equals(poohMode)) {
            param = lineParse[1].split("/")[3];
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
