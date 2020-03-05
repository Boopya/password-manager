public interface SQLStatements extends DatabaseConstants {
    public static final String QUERY_ENTRIES = "SELECT * FROM " + TABLE_NAME;
    public static final String ADD_ENTRY = "INSERT INTO ACCOUNTS VALUES ";
    public static final String DELETE_ENTRY = "DELETE FROM ACCOUNTS WHERE ";
    public static final String UPDATE_ENTRY = "UPDATE ACCOUNTS SET ";
}
