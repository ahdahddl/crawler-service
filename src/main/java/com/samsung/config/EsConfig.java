package com.samsung.config;


import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;

import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


import java.net.InetAddress;

//client.transport.diff : 디폴트는 false이다. true로 설정하면 클라이언트는 연결 직후 다른 노드의 주소를 얻어올 수 있다.
//client.transport.ignore_cluster_name : 디폴트는 false이다. true로 설정하면 클러스터 이름이 올바른지에 대한 체크(validation)을 하지 않는다. 클라이언트가 지정한 클러스터의 이름과 서버의 클러스터 이름이 다른 경우에 경고가 나온다.
//client.transport.ping_timeout : 5s가 디폴트이다.
//client.transport.nodes_sampler_interval : 디폴트는 5s이다. ping/sample에 대한 주기를 의미한다.


@Configuration
@EnableElasticsearchRepositories(basePackages = "com.samsung.repository")
public class EsConfig {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.cluster_name}")
    private String clusterName;

    @Bean
    public Client client() {

        try {
            Settings esSettings = Settings.builder()
                    .put("cluster.name", clusterName)
                    .put("client.transport.sniff", false)//Add sniffing mechanism to find ES cluster
                    .put("thread_pool.search.size", 1)// Increase the number of thread pools, temporarily set to 5
                    .build();

            //https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html

            return new PreBuiltTransportClient(esSettings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
        } catch (Exception e) {
            System.out.println("Initialize Elasticsearch failed!");
            System.out.println(e.getStackTrace());
            return null;
        }
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplateCustom() throws Exception {
        Client client = client();
        if (client != null) {
            return new ElasticsearchTemplate(client);
        } else {
//            Throw new ApiException("Initialize Elasticsearch failed!", 100011);
        }
        return null;
    }

    //Embedded Elasticsearch Server
    /*@Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
    }*/
}
