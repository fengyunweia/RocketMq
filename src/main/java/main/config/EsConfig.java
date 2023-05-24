package main.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author fengyunwei
 */
@ContextConfiguration
public class EsConfig {
    @Bean
    public RestHighLevelClient client(){
        RestHighLevelClient highLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
        return highLevelClient;
    }
}
