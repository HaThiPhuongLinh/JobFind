package com.jobfind.repositories;

import com.jobfind.models.Conversation;
import com.jobfind.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    List<Conversation> findByJobSeeker(User jobSeeker);
    List<Conversation> findByCompany(User company);

    @Query("SELECT COUNT(c) FROM Conversation c WHERE " +
            "(c.jobSeeker.userId = :userId AND c.unreadCountJobSeeker > 0) OR " +
            "(c.company.userId = :userId AND c.unreadCountCompany > 0)")
    Long countUnreadConversations(Integer userId);

    @Query("SELECT c.id FROM Conversation c WHERE c.jobSeeker.userId = :jobSeekerUserId AND c.company.userId = :companyUserId")
    Long findConversationIdByJobSeekerUserIdAndCompanyUserId(@Param("jobSeekerUserId") Integer jobSeekerUserId,
                                                             @Param("companyUserId") Integer companyUserId);

}