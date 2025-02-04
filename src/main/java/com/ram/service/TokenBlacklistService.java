package com.ram.service;

public interface TokenBlacklistService {
    public void addtoBlacklist(String jwt);
    public boolean isBlacklisted(String jwt);
}
