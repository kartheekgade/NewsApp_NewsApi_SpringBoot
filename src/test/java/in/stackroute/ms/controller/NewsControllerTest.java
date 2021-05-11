package in.stackroute.ms.controller;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import in.stackroute.ms.model.Articles;
import in.stackroute.ms.model.Example;
import in.stackroute.ms.service.NewsService;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {

	@Autowired
	private MockMvc mvc;
	
	private List<Example> example;
	@Before
	public void setUp(){
		Example example1 = new Example();
		example1.setTotalResults(20);
		
		Articles firstArticle = new Articles();
		
		example1.setArticles(firstArticle);
	}
	@Test
	public void accessTopHeadlines() throws Exception {
		Mockito.when(NewsService.sendRefinedUpdate()).thenReturn(example);
		MockHttpServletResponse resp = mvc.perform(
				MockMvcRequestBuilders.get("https://newsapi.org/v2/everything?q=tesla&from=2021-04-08&sortBy=publishedAt&apiKey=4f7aa805d4554730bdd04eae199d9519").accept(MediaType.APPLICATION_JSON).header("Authorization", "Basic dXNlcjpwYXNzd29yZA=="))
				.andReturn().getResponse();
		
		String headResp =  resp.getContentAsString();
		
		assert(headResp.contains("ok"));
	}
}
