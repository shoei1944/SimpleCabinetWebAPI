package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.NewsComment;

public interface NewsCommentRepository extends JpaRepository<NewsComment, Long> {
}
