/**
 * Licensed to JumpMind Inc under one or more contributor
 * license agreements.  See the NOTICE file distributed
 * with this work for additional information regarding
 * copyright ownership.  JumpMind Inc licenses this file
 * to you under the GNU General Public License, version 3.0 (GPLv3)
 * (the "License"); you may not use this file except in compliance
 * with the License.
 *
 * You should have received a copy of the GNU General Public License,
 * version 3.0 (GPLv3) along with this library; if not, see
 * <http://www.gnu.org/licenses/>.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jumpmind.symmetric.io;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;

public class DbCompareConfig {
    
    public final static String WHERE_CLAUSE = "where_clause";
    public final static String EXCLUDED_COLUMN = "exclude_columns";
    
    private String sqlDiffFileName;
    private List<String> includedTableNames;
    private List<String> targetTableNames;
    private List<String> excludedTableNames;
    private boolean useSymmetricConfig = true;
    private int numericScale = 3;
    private Map<String, String> whereClauses = new LinkedHashMap<String, String>();
    private Map<String, List<String>> tablesToExcludedColumns = new LinkedHashMap<String, List<String>>();
    
    public String getSourceWhereClause(String tableName) {
        return getWhereClause(tableName, "source");        
    }
    
    public String getTargetWhereClause(String tableName) {
        return getWhereClause(tableName, "target");
    }
    
    protected String getWhereClause(String tableName, String sourceOrTarget) {
        String tableNameLower = tableName.toLowerCase();
        String[] keys = {
                tableNameLower + "." + sourceOrTarget + "." + WHERE_CLAUSE,
                tableNameLower + "." + WHERE_CLAUSE,
                WHERE_CLAUSE
        };
        
        for (String key : keys) {
            if (whereClauses.containsKey(key)) {
                return whereClauses.get(key);
            }            
        }
        
        return "1=1";
    }
    
    protected boolean shouldIncludeColumn(String tableName, String columnName) {
        String tableNameLower = tableName.toLowerCase();
        String columnNameLower = columnName.toLowerCase();
        String[] keys = {
                tableNameLower + "." + EXCLUDED_COLUMN,
                EXCLUDED_COLUMN
        };
        
        for (String key : keys) {
            if (tablesToExcludedColumns.containsKey(key)) {
                List<String> exludedColumnNames = tablesToExcludedColumns.get(key);
                return !exludedColumnNames.contains(columnNameLower);
            }            
        }
        
        return true;
    }
    
    public String getSqlDiffFileName() {
        return sqlDiffFileName;
    }
    public void setSqlDiffFileName(String sqlDiffFileName) {
        this.sqlDiffFileName = sqlDiffFileName;
    }
    public List<String> getIncludedTableNames() {
        return includedTableNames;
    }
    public void setIncludedTableNames(List<String> includedTableNames) {
        this.includedTableNames = includedTableNames;
    }
    public List<String> getExcludedTableNames() {
        return excludedTableNames;
    }
    public void setExcludedTableNames(List<String> excludedTableNames) {
        this.excludedTableNames = excludedTableNames;
    }
    public boolean isUseSymmetricConfig() {
        return useSymmetricConfig;
    }
    public void setUseSymmetricConfig(boolean useSymmetricConfig) {
        this.useSymmetricConfig = useSymmetricConfig;
    }
    public int getNumericScale() {
        return numericScale;
    }
    public void setNumericScale(int numericScale) {
        this.numericScale = numericScale;
    }
    public Map<String, String> getWhereClauses() {
        return whereClauses;
    }
    @SuppressWarnings("unchecked")
    public void setWhereClauses(Map<String, String> whereClauses) {
        this.whereClauses = new CaseInsensitiveMap(whereClauses);
    }

    public List<String> getTargetTableNames() {
        return targetTableNames;
    }

    public void setTargetTableNames(List<String> targetTableNames) {
        this.targetTableNames = targetTableNames;
    }

    public Map<String, List<String>> getTablesToExcludedColumns() {
        return tablesToExcludedColumns;
    }

    public void setTablesToExcludedColumns(Map<String, List<String>> tablesToExcludedColumns) {
        this.tablesToExcludedColumns = tablesToExcludedColumns;
    }
}
