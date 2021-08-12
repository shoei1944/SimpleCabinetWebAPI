package pro.gravit.simplecabinet.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.News;
import pro.gravit.simplecabinet.web.model.NewsComment;
import pro.gravit.simplecabinet.web.repository.NewsCommentRepository;
import pro.gravit.simplecabinet.web.repository.NewsRepository;

import java.util.Optional;

@Service
public class NewsService {
    private NewsRepository newsRepository;
    private NewsCommentRepository commentRepository;

    public Optional<News> findByIdFetchComments(Long id) {
        return newsRepository.findByIdFetchComments(id);
    }

    public Page<News> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    public <S extends News> S save(S entity) {
        return newsRepository.save(entity);
    }

    public Optional<News> findById(Long aLong) {
        return newsRepository.findById(aLong);
    }

    public <S extends NewsComment> S save(S entity) {
        return commentRepository.save(entity);
    }

    public void delete(News entity) {
        newsRepository.delete(entity);
    }

    public void delete(NewsComment entity) {
        commentRepository.delete(entity);
    }
}
