
package com.github.domain;

public interface IDatabase {
    void executeQuery(String query);
    Object fetchRecord(int id);
    boolean deleteRecord(int id);
}
