package com.github.domain;

/**
 * The IDatabase interface defines the basic operations for interacting with a database.
 * This abstraction allows the domain layer to remain agnostic of the specific database implementation.
 */
public interface IDatabase {
    /**
     * Executes a given SQL query.
     *
     * @param query the SQL query to be executed
     */
    void executeQuery(String query);

    /**
     * Fetches a record from the database based on the provided ID.
     *
     * @param id the ID of the record to fetch
     * @return the fetched record as an Object, or null if not found
     */
    Object fetchRecord(int id);

    /**
     * Deletes a record from the database based on the provided ID.
     *
     * @param id the ID of the record to delete
     * @return true if the record was deleted successfully, false otherwise
     */
    boolean deleteRecord(int id);
}
