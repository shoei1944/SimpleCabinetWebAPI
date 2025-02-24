package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.dto.NewsCommentDto;
import pro.gravit.simplecabinet.web.dto.NewsDto;
import pro.gravit.simplecabinet.web.model.News;
import pro.gravit.simplecabinet.web.model.NewsComment;
import pro.gravit.simplecabinet.web.repository.NewsCommentRepository;
import pro.gravit.simplecabinet.web.repository.NewsRepository;
import pro.gravit.simplecabinet.web.service.storage.StorageService;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private NewsRepository newsRepository;
    private NewsCommentRepository commentRepository;
    @Autowired
    private StorageService storageService;

    @Autowired
    public NewsService(NewsRepository newsRepository, NewsCommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    public NewsDto toNewsWithPictureUrl(News entity) {
        return new NewsDto(entity.getId(), entity.getHeader(), entity.getPicture() != null ? storageService.getUrl(entity.getPicture()).toString() : null, null, entity.getText(), entity.getComments().stream().map(NewsCommentDto::new).collect(Collectors.toList()));
    }

    public NewsDto toMiniNewsWithPictureUrl(News entity) {
        return new NewsDto(entity.getId(), entity.getHeader(), entity.getPicture() != null ? storageService.getUrl(entity.getPicture()).toString() : null, entity.getMiniText(), null,null );
    }

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

    public Optional<NewsComment> findCommentById(Long aLong) {
        return commentRepository.findById(aLong);
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
