package com.example.elastictest.article

import com.example.elastictest.article.repository.ArticleRepository
import com.example.elastictest.article.service.ArticleService
import com.example.elastictest.security.AuthenticationFacadeInterface
import org.junit.Before
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@DisplayName("ArticleServiceTest")
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleServiceMock
    @Mock
    private ArticleRepository articleRepositoryMock
    @Mock
    private AuthenticationFacadeInterface authenticationFacadeMock


    @Before
    void beforeEach(){
        MockitoAnnotations.initMocks(this)

    }
}
