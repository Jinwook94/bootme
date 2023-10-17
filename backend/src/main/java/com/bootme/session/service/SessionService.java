package com.bootme.session.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;



import java.util.HashSet;
import java.util.Set;

import static org.springframework.web.context.request.RequestContextHolder.*;

@Service
public class SessionService {

    public HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    @SuppressWarnings("unchecked")
    public Set<Long> getViewedPosts() {
        HttpSession session = getSession();
        Set<Long> viewedPosts = (Set<Long>) session.getAttribute(SessionAttribute.VIEWED_POSTS.toString());
        if (viewedPosts == null) {
            viewedPosts = new HashSet<>();
        }
        return viewedPosts;
    }

    public void addViewedPost(Long postId) {
        HttpSession session = getSession();
        Set<Long> viewedPosts = getViewedPosts();
        viewedPosts.add(postId);
        session.setAttribute(SessionAttribute.VIEWED_POSTS.toString(), viewedPosts);
    }

}
