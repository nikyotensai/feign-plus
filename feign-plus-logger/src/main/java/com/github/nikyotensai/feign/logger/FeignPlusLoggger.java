/**
 * Copyright (c) 2020-2050 NikyoTensai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.nikyotensai.feign.logger;

import feign.Request;
import feign.Response;
import feign.Util;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Objects;

@Slf4j
public class FeignPlusLoggger extends Slf4jLogger {

    private static final ThreadLocal<Payload> threadLocal = ThreadLocal.withInitial(() -> {
        return new Payload();
    });

    public FeignPlusLoggger(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        Payload payload = new Payload();
        payload.setClassMethod(configKey);
        payload.setRequestUrl(request.url());
        payload.setHeaders(request.headers() != null ? request.headers().toString() : "-");
        if (request.requestBody() != null) {
            payload.setRequestBody(request.requestBody().asString());
        }
        threadLocal.set(payload);
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        Payload payload = threadLocal.get();
        if (Objects.nonNull(payload)) {
            try {
                payload.setElapsedTime(elapsedTime);
                payload.setSuccess(Constants.SUCCESS_STATUS);
                if (Objects.nonNull(response)) {
                    int status = response.status();
                    if (response.body() != null && status != HttpStatus.NO_CONTENT.value() && status != HttpStatus.RESET_CONTENT.value()) {
                        byte[] bodyData = Util.toByteArray(response.body().asInputStream());
                        String responseBody = this.decodeOrDefault(bodyData, Util.UTF_8, "-");
                        payload.setReponseBody(responseBody);
                        response = response.toBuilder().body(bodyData).build();
                    }
                }
                log.info("feign log:{}", payload);
            } catch (Exception ex) {
                log.error("feign execute logAndRebufferResponse method error:", ex);
            } finally {
                threadLocal.remove();
            }
        }
        return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
    }

    @Override
    protected IOException logIOException(String configKey, Level logLevel, IOException ioe, long elapsedTime) {
        Payload payload = threadLocal.get();
        if (Objects.nonNull(payload)) {
            try {
                payload.setElapsedTime(elapsedTime);
                payload.setSuccess(Constants.FAIL_STATUS);
                StringWriter sw = new StringWriter();
                ioe.printStackTrace(new PrintWriter(sw));
                payload.setReponseBody(sw.toString());
                log.error("feign IOException log:", payload);
            } finally {
                threadLocal.remove();
            }
        }

        return ioe;
    }

    public String decodeOrDefault(byte[] data, Charset charset, String defaultValue) {
        if (data == null) {
            return defaultValue;
        } else {
            try {
                return charset.newDecoder().decode(ByteBuffer.wrap(data)).toString();
            } catch (CharacterCodingException var5) {
                return defaultValue;
            }
        }
    }
}
