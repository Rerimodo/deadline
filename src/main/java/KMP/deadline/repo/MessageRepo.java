package KMP.deadline.repo;

import KMP.deadline.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findAllByUserId(Long userId);
}
