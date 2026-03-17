package com.epam.springCoreTask.security;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {

    private final Map<String, Date> blacklistedTokens = new ConcurrentHashMap<>();

    public void blacklist(String token, Date expiration) {
        if (token == null || expiration == null) {
            return;
        }

        blacklistedTokens.put(token, expiration);
        purgeExpiredTokens();
    }

    public boolean isBlacklisted(String token) {
        purgeExpiredTokens();
        return blacklistedTokens.containsKey(token);
    }

    private void purgeExpiredTokens() {
        Date now = new Date();
        blacklistedTokens.entrySet().removeIf(entry -> entry.getValue().before(now));
    }
}