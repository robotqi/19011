/**
 * Chelsea
 */
public interface DBTemplate
{
    public boolean addRecord(String strTable,
                             String strKeyName,
                             String strKeyContents);
    public boolean deleteAll(String strTable);

    public void close();
    public  String getField(String strFieldName);
    public Boolean moreRecords();
    public void openConnection(String strDataSourceName);
    public void query(String strSQL);
    public Boolean setField(String strTable,
                            String strKeyName,
                            String strKeyContents,
                            String strFieldName,
                            String strFieldContents);
    public void status(String strVar);

}
