package com.tensai.feign.logger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Payload {

    private String classMethod;
    private String requestUrl;
    private String headers;
    private String requestBody;
    private String reponseBody;
    private Integer success;
    private Long elapsedTime;

}
