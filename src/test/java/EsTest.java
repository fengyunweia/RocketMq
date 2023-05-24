import com.alibaba.fastjson.JSON;
import main.Application;
import main.domain.JD;
import main.domain.Student;
import main.utils.JtmlUtil;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(classes = Application.class)
public class EsTest {
    @Autowired
    private RestHighLevelClient client;


    @Test
    void contextLoads() throws IOException {
        CreateIndexRequest ahahaIndexndex = new CreateIndexRequest("ahaha_index");
        CreateIndexResponse createIndexResponse = client.indices().create(ahahaIndexndex, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    @Test
    void testAdd() throws IOException {
        Student student = new Student();
        student.setName("张三");
        student.setSix("25");
        student.setDoThing("学习");

        IndexRequest indexRequest = new IndexRequest("ahaha_index");
        indexRequest.id("1");
        indexRequest.type("testType");
        indexRequest.source(JSON.toJSONString(student), XContentType.JSON);
        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index.toString());

    }

    @Test
    void testAddBath() throws IOException {
        Student student = new Student();
        student.setName("张三");
        student.setSix("25");
        student.setDoThing("学习");

        IndexRequest indexRequest = new IndexRequest("ahaha_index");
        indexRequest.id("1");
        indexRequest.type("testType");
        indexRequest.source(JSON.toJSONString(student), XContentType.JSON);
        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index.toString());

    }

    @Test
    void testSearch() throws IOException {
        //构建request
        SearchRequest request = new SearchRequest("jd");
        //构建搜索参数 -----------重点 如果是String 会映射成 text keyword 类型 需要加上.keyword 不让分词器分开才能精确查询
        TermQueryBuilder name = QueryBuilders.termQuery("name", "crocs");
        //将搜索参数仿佛source
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(name);
        request.source(searchSourceBuilder);
        SearchResponse search = client.search(request,RequestOptions.DEFAULT);
        System.out.println(search);

    }

    /**
     * es 京东实战
     */
    @Test
    void testJingDong() throws IOException {
        List<JD> crocs = new JtmlUtil().JD("java");

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");
        AtomicInteger n = new AtomicInteger();
        crocs.forEach(i->{
            int i1 = n.getAndIncrement();
            bulkRequest.add(new IndexRequest("jd").type("_doc").id(String.valueOf(i1)).source(JSON.toJSONString(i), XContentType.JSON));
            System.out.println("=====================================");
            System.out.println(i);
        });
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());
    }

    /**
     * es 京东实战
     */
    @Test
    void testGet() throws IOException {
        //构建request
        SearchRequest request = new SearchRequest("jd");
        //构建搜索参数 -----------重点 如果是String 会映射成 text keyword 类型 需要加上.keyword 不让分词器分开才能精确查询 精确查询
        TermQueryBuilder name = QueryBuilders.termQuery("name.keyword", "crocs");

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "crocs");
        //将搜索参数仿佛source
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQueryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(1000);

        request.source(searchSourceBuilder);
        SearchResponse search = client.search(request,RequestOptions.DEFAULT);
        System.out.println(search);
    }
}
