package api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import api.domain.Article;

public interface ArticleRepository extends MongoRepository<Article, String> {

}
