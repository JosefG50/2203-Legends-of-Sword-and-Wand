package com.github.domain;

public interface IDatabase extends AutoCloseable {
    boolean executeUpdate(String query, Object... params);
    Object fetchRecord(int id);
    boolean deleteRecord(int id);
    @Override
    void close() throws Exception;
}