package com.github.nikyotensai.test;

import com.github.nikyotensai.ConsumerPlusApplication;
import com.github.nikyotensai.clients.ProviderPlusClient;
import com.github.nikyotensai.entity.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConsumerPlusApplication.class)
@AutoConfigureMockMvc
public class ProviderPLusClientTest {

    @Mock
    private ProviderPlusClient providerPlusClient;

    @Autowired
    private MockMvc mvc;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testFeignGet() throws Exception {
        when(providerPlusClient.getSth()).thenReturn(Result.create("0", "hello world"));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/test/getSth")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(providerPlusClient.getSth());
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
